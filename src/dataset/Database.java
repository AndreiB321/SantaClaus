package dataset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.Constants;
import enums.Category;
import enums.CityStrategyEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import strategy.StrategySort;
import strategy.StrategySortByID;
import strategy.StrategySortByNicescore;
import strategy.StrategySortByNicescoreCity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Database {
    private List<Child> children;
    private List<Gift> gifts;
    private List<AnnualChange> annualChanges;
    private int numberOfYears;
    private double santaBudget;
    private static Database instance = null;
    private StrategySort strategySort;
    // Created a strategy for each method
    private StrategySortByID strategySortByID = new StrategySortByID();
    private strategy.StrategySortByNicescore StrategySortByNicescore
            = new StrategySortByNicescore();
    private strategy.StrategySortByNicescoreCity StrategySortByNicescoreCity
            = new StrategySortByNicescoreCity();

    private Database() {
    }

    /**
     * Constructor Singleton
     * @return
     */
    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Call the method that start the solution
     * @return
     */
    public JSONArray solution() {
        JSONArray objects = new JSONArray();
        // remove children older than 18
        getChildren().removeIf(child -> child.getAge() > Constants.EIGHTEEN);
        // add first round children to final json
        addChildren(objects, -1);
        // simulate every round
        for (int year = 0; year < getNumberOfYears(); year++) {
            updateData(objects, year);
        }
        return objects;
    }

    /**
     * Update data for the current year
     */
    public void updateData(final JSONArray objects, final int year) {
        AnnualChange annualChange = getAnnualChanges().get(year);

        setSantaBudget(annualChange.getNewSantaBudget());
        // add presents
        annualChange.getNewGifts().forEach(gift -> getGifts().add(gift));
        // update age
        getChildren().forEach(child -> child.setAge(child.getAge() + 1));

        getChildren().removeIf(child -> child.getAge() > Constants.EIGHTEEN);
        // update children preferences
        for (ChildUpdate childUpdate : annualChange.getChildUpdates()) {
            // check if a child is new or updated
            Child child = getChildren().stream()
                    .filter(x -> childUpdate.getId() == x.getId())
                    .findFirst().orElse(null);
            if (child != null) {
                // child is updated
                if (childUpdate.getNewNiceScore() != null && childUpdate.getNewNiceScore() >= 0) {
                    child.getNiceScoreHistory().add(childUpdate.getNewNiceScore());
                }
                // add all preferences
                childUpdate.getNewGiftPreferences().addAll(child.getGiftsPreferences());
                child.setGiftsPreferences(childUpdate.getNewGiftPreferences());
                // remove duplicates
                child.setGiftsPreferences(child.getGiftsPreferences()
                        .stream().distinct().collect(Collectors.toList()));
            }
        }
        // add new children
        annualChange.getNewChildren().stream().filter(child -> child.getAge() < Constants.EIGHTEEN)
                .forEach(child -> getChildren().add(child));
        // add children to final json
        addChildren(objects, year);
    }

    /**
     * Set best gifts to each child
     * @return
     */
    public JSONArray round(int year) {
        JSONArray roundList = new JSONArray();
        List<Child> childList = new ArrayList<>();
        final double[] average = {0.0};
        Gift bestGift = null;
        double bestPrice = Double.MAX_VALUE;
        // calculate average
        getChildren().forEach(child -> average[0] += getAverage(child));

        getChildren().forEach(child -> child.setAverageScore(getAverage(child)));
        // set budgetUnit
        double budgetUnit = getSantaBudget() / average[0];
        if (year >= 0) {
            // sort children by strategy
            CityStrategyEnum cityStrategyEnum = getAnnualChanges().get(year).getStrategy();

            if (cityStrategyEnum.equals(CityStrategyEnum.ID)) {
                setChildren(strategySortByID.sortBy(getChildren()));
            }

            if (cityStrategyEnum.equals(CityStrategyEnum.NICE_SCORE)) {
                setChildren(StrategySortByNicescore.sortBy(getChildren()));
            }

            if (cityStrategyEnum.equals(CityStrategyEnum.NICE_SCORE_CITY)) {
                setChildren(StrategySortByNicescoreCity.sortBy(getChildren()));
            }
        }

        for (Child child : getChildren()) {
            List<Gift> childGifts = new ArrayList<>();

            double averageScore = getAverage(child);
            double sumPrices = budgetUnit * averageScore;
//            if (child.getElf().equals(ElvesType.BLACK))
//                sumPrices = sumPrices * 0.7;
//
//            if (child.getElf().equals(ElvesType.PINK))
//                sumPrices = sumPrices * 1.3;

            child.setAssignedBudget(sumPrices);
            // Find cheapest gift from preferences
            for (Category categoryPreference : child.getGiftsPreferences()) {
                bestPrice = Double.MAX_VALUE;
                for (Gift gift : getGifts()) {
                    if (gift.getCategory().equals(categoryPreference)
                            && gift.getPrice() <= bestPrice
                            && sumPrices - gift.getPrice() >= 0 && gift.getQuantity() > 0) {
                            bestGift = gift;
                            bestPrice = gift.getPrice();
                    }
                }
                // check if a gift was found
                if (bestPrice != Double.MAX_VALUE) {
                    childGifts.add(bestGift);
                    sumPrices -= bestGift.getPrice();
                    bestGift.setQuantity(bestGift.getQuantity() - 1);
                }
            }
            // update child data
            child.setAverageScore(averageScore);
            child.setReceivedGifts(childGifts);
            // add object to json array
            childList.add(child);
        }

        childList = strategySortByID.sortBy(childList);

        for (Child child : childList) {

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStringObject = null;
            try {
                jsonStringObject = objectMapper.writerWithDefaultPrettyPrinter()
                        .writeValueAsString(child);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(jsonStringObject);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // add JSONobject created to final JSONArray
            roundList.add(jsonObject);

        }
//
//        for (Child child : getChildren()) {
//            if (child.getReceivedGifts().size() == 0 && child.getElf().equals(ElvesType.YELLOW)) {
//                System.out.println(child.getGiftsPreferences());
//                System.out.println(child.getFirstName());
//                System.out.println(child.getLastName());
//                List<Gift> childGifts = new ArrayList<>();
//
//                double averageScore = getAverage(child);
//                double sumPrices = 10000000.0;
//
//                child.setAssignedBudget(sumPrices);
//
//                for (Category categoryPreference : child.getGiftsPreferences()) {
//                    bestPrice = Double.MAX_VALUE;
//                    for (Gift gift : getGifts()) {
//                        if (gift.getCategory().equals(categoryPreference)
//                                && gift.getPrice() <= bestPrice
//                                && sumPrices - gift.getPrice() >= 0 && gift.getQuantity() > 0) {
//                            bestGift = gift;
//                            bestPrice = gift.getPrice();
//                        }
//                    }
//                    if (bestPrice != Double.MAX_VALUE) {
//                        childGifts.add(bestGift);
//                        sumPrices -= bestGift.getPrice();
//                        bestGift.setQuantity(bestGift.getQuantity() - 1);
//                    }
//                }
//
//                child.setAverageScore(averageScore);
//                child.setReceivedGifts(childGifts);
//                // add object to json array
//                ObjectMapper objectMapper = new ObjectMapper();
//                String jsonStringObject = null;
//                try {
//                    jsonStringObject = objectMapper.writerWithDefaultPrettyPrinter()
//                            .writeValueAsString(child);
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//
//                JSONParser jsonParser = new JSONParser();
//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = (JSONObject) jsonParser.parse(jsonStringObject);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                roundList.add(jsonObject);
//            }
//        }
        return roundList;
    }


    /**
     * Calculate the average score
     */
    public double getAverage(final Child child) {
        double sum = 0.0;
        double cnt;
        final int twelve = 12;
        final int five = 5;
        final int ten = 10;
        // child is younger than 5
        if (child.getAge() < five) {
            return ten;
        } else if (child.getAge() >= twelve) {
            // child is over 12
            cnt = (1 + child.getNiceScoreHistory().size()) * child.getNiceScoreHistory().size() / 2;
            for (int i = 1; i <= child.getNiceScoreHistory().size(); i++) {
                sum += i * child.getNiceScoreHistory().get(i - 1);
            }
            return Math.min((sum / cnt) * (1 + child.getBonusNiceScore() / 100), 10);
        }
        // child is between 5 and 12
        for (Double score : child.getNiceScoreHistory()) {
            sum += score;
        }

        return Math.min((sum / child.getNiceScoreHistory().size()) * (1 + child.getBonusNiceScore() / 100), 10);
    }


    private void addChildren(final JSONArray objects, int year) {
        JSONObject jsonObject = new JSONObject();
        // add current round children to final array
        jsonObject.put(Constants.CHILDREN, round(year));
        objects.add(jsonObject);
    }


    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(final List<Gift> gifts) {
        this.gifts = gifts;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final double santaBudget) {
        this.santaBudget = santaBudget;
    }

}
