package input;

import common.Constants;
import dataset.AnnualChange;
import dataset.Child;
import dataset.ChildUpdate;
import dataset.Database;
import dataset.Entity;
import dataset.Factory;
import dataset.Gift;
import enums.Category;
import enums.Cities;
import enums.CityStrategyEnum;
import enums.ElvesType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The class reads and parses the data from the tests
 * <p>
 * DO NOT MODIFY
 */
public final class InputLoader {
    /**
     * The path to the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    public String getInputPath() {
        return inputPath;
    }

    /**
     * The method reads the database
     *
     * @return an Input object
     */
    public void readData() {
        Map<String, Category> stringCategoryMap = initCategory();
        Map<String, Cities> stringCitiesMap = initCities();
        Database database = Database.getDatabase();
        JSONParser jsonParser = new JSONParser();
        List<Child> children = new ArrayList<>();
        List<Gift> gifts = new ArrayList<>();
        List<AnnualChange> annualChanges = new ArrayList<>();
        int numberOfYears;
        double santaBudget;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.inputPath))) {
            // get main file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(bufferedReader);
            // retrieve main data
            numberOfYears = Integer.parseInt(jsonObject.get(Constants.NUMBER_OF_YEARS).toString());
            santaBudget = Double.parseDouble(jsonObject.get(Constants.SANTA_BUDGET).toString());
            JSONObject initialData = (JSONObject) jsonObject.get(Constants.INITIAL_DATA);
            JSONArray jsonChildren = (JSONArray)
                    initialData.get(Constants.CHILDREN);
            JSONArray jsonSantaGiftList = (JSONArray)
                    initialData.get(Constants.SANTA_GIFT_LIST);
            JSONArray jsonAnnualChanges = (JSONArray) jsonObject.get(Constants.ANNUAL_CHANGES);
            // check if children exist
            if (jsonChildren != null) {
                for (Object jsonChild : jsonChildren) {
                    // set niceScoreHistory as an array so we can print it later in output
                    List<Double> niceScoreHistory = new ArrayList<>();
                    List<Category> giftPreferences = new ArrayList<>();

                    for (Object gift : (JSONArray) ((JSONObject) jsonChild)
                            .get(Constants.GIFTS_PREFERENCES)) {
                        giftPreferences.add(stringToCategory(gift.toString()));
                    }
//                    giftPreferences.stream().distinct().collect(Collectors.toList());
                    niceScoreHistory.add(getDouble(Constants.NICE_SCORE, jsonChild));
                    Child.ChildBuilder childBuilder = new Child.ChildBuilder(
                            getInteger(Constants.ID, jsonChild),
                            getString(Constants.LASTNAME, jsonChild),
                            getString(Constants.FIRSTNAME, jsonChild),
                            stringToCities(((JSONObject) jsonChild)
                                    .get(Constants.CITY).toString()),
                            getInteger(Constants.AGE, jsonChild),
                            giftPreferences,
                            niceScoreHistory,
                            stringToElf(((JSONObject) jsonChild)
                                    .get(Constants.ELF).toString())
                    );
                    if (((JSONObject) jsonChild).get(Constants.NICE_SCORE_BONUS) != null
                            && getDouble(Constants.NICE_SCORE_BONUS, jsonChild) != 0.0) {
                        childBuilder.
                                bonusNiceScore(getDouble(Constants.NICE_SCORE_BONUS, jsonChild));
                    }
                    children.add(childBuilder.build());
                }
            }
            // check if gifts exist
            if (jsonSantaGiftList != null) {
                for (Object jsonGift : jsonSantaGiftList) {
                    Entity entity = Factory.getEntity(Constants.GIFT);
                    assert entity != null;
                    gifts.add(entity.populateEntity(
                            getString(Constants.PRODUCT_NAME, jsonGift),
                            getDouble(Constants.PRICE, jsonGift),
                            stringToCategory(((JSONObject) jsonGift)
                                    .get(Constants.CATEGORY).toString()),
                            getInteger(Constants.QUANTITY, jsonGift)
                    ));
                }
            }
            // check if changes exist
            if (jsonAnnualChanges != null) {
                for (Object jsonAnnualChange : jsonAnnualChanges) {
                    Double newSantaBudget =
                            getDouble(Constants.NEW_SANTA_BUDGET, jsonAnnualChange);
                    List<Gift> newGifts = new ArrayList<>();
                    List<Child> newChildren = new ArrayList<>();
                    List<ChildUpdate> childUpdates = new ArrayList<>();
                    CityStrategyEnum strategy
                            = stringToCityStrategyEnum(((JSONObject) jsonAnnualChange)
                            .get(Constants.STRATEGY).toString());
                    JSONArray jsonNewGifts = (JSONArray) ((JSONObject) jsonAnnualChange)
                            .get(Constants.NEW_GIFTS);
                    JSONArray jsonNewChildren = (JSONArray) ((JSONObject) jsonAnnualChange)
                            .get(Constants.NEW_CHILDREN);
                    JSONArray jsonChildrenUpdates = (JSONArray) ((JSONObject) jsonAnnualChange)
                            .get(Constants.CHILDREN_UPDATES);
                    // check if gifts exist
                    if (jsonNewGifts != null) {
                        for (Object jsonNewGift : jsonNewGifts) {
                            Entity entity = Factory.getEntity(Constants.GIFT);
                            newGifts.add(entity.populateEntity(
                                    getString(Constants.PRODUCT_NAME, jsonNewGift),
                                    getDouble(Constants.PRICE, jsonNewGift),
                                    stringToCategory(((JSONObject) jsonNewGift)
                                            .get(Constants.CATEGORY).toString()),
                                    getInteger(Constants.QUANTITY, jsonNewGift)
                            ));
                        }
                    }
                    // check if children exist
                    if (jsonNewChildren != null) {
                        for (Object jsonNewChild : jsonNewChildren) {
                            List<Double> niceScoreHistory = new ArrayList<>();
                            List<Category> giftPreferences = new ArrayList<>();
                            niceScoreHistory.add(getDouble(Constants.NICE_SCORE, jsonNewChild));

                            for (Object gift : (JSONArray) ((JSONObject) jsonNewChild)
                                    .get(Constants.GIFTS_PREFERENCES)) {
                                giftPreferences.add(stringToCategory(gift.toString()));
                            }
                            Child.ChildBuilder childBuilder = new Child.ChildBuilder(
                                    getInteger(Constants.ID, jsonNewChild),
                                    getString(Constants.LASTNAME, jsonNewChild),
                                    getString(Constants.FIRSTNAME, jsonNewChild),
                                    stringToCities(((JSONObject) jsonNewChild)
                                            .get(Constants.CITY).toString()),
                                    getInteger(Constants.AGE, jsonNewChild),
                                    giftPreferences,
                                    niceScoreHistory,
                                    stringToElf(((JSONObject) jsonNewChild)
                                            .get(Constants.ELF).toString())
                            );
                            if (((JSONObject) jsonNewChild).get(Constants.NICE_SCORE_BONUS) != null
                                    && getDouble(Constants.NICE_SCORE_BONUS, jsonNewChild) != 0.0) {
                                childBuilder.
                                        bonusNiceScore(getDouble(
                                                Constants.NICE_SCORE_BONUS, jsonNewChild));
                            }
                            newChildren.add(childBuilder.build());

                        }
                    }
                    // check if updates exist
                    if (jsonChildrenUpdates != null) {
                        for (Object jsonChildUpdate : jsonChildrenUpdates) {
                            Double niceScoreValue = null;
                            List<Category> giftPreferences = new ArrayList<>();
                            for (Object gift : (JSONArray) ((JSONObject) jsonChildUpdate)
                                    .get(Constants.GIFTS_PREFERENCES)) {
                                giftPreferences.add(stringToCategory(gift.toString()));
                            }

                            if (((JSONObject) jsonChildUpdate).get(Constants.NICE_SCORE) != null) {
                                niceScoreValue =  getDouble(Constants.NICE_SCORE,
                                        jsonChildUpdate);
                            }
                            Entity entity = Factory.getEntity(Constants.CHILD_UPDATE);
                            childUpdates.add(entity.populateEntity(
                                    getInteger(Constants.ID, jsonChildUpdate),
                                    niceScoreValue,
                                    giftPreferences,
                                    stringToElf(((JSONObject) jsonChildUpdate)
                                            .get(Constants.ELF).toString())
                            ));
                        }
                    }
                    Entity entity = Factory.getEntity(Constants.ANNUAL_CHANGES);
                    annualChanges.add(entity.populateEntity(newSantaBudget, newGifts,
                            newChildren, childUpdates, strategy));
                }

            }
            // set database with the input retrieved
            database.setChildren(children);
            database.setGifts(gifts);
            database.setAnnualChanges(annualChanges);
            database.setNumberOfYears(numberOfYears);
            database.setSantaBudget(santaBudget);

        } catch (Exception e) {
        }
    }

    private CityStrategyEnum stringToCityStrategyEnum(final String strategy) {
        return switch (strategy) {
            case "id" -> CityStrategyEnum.ID;
            case "niceScore" -> CityStrategyEnum.NICE_SCORE;
            case "niceScoreCity" -> CityStrategyEnum.NICE_SCORE_CITY;
            default -> null;
        };
    }

    private ElvesType stringToElf(final String elf) {
        return switch (elf) {
            case "yellow" -> ElvesType.YELLOW;
            case "black" -> ElvesType.BLACK;
            case "pink" -> ElvesType.PINK;
            case "white" -> ElvesType.WHITE;
            default -> null;
        };
    }

    /**
     * Return a hashmap with enum values (string, enum) for Category
     * @return
     */
    private Map<String, Category> initCategory() {
        Map<String, Category> stringCategoryMap = new HashMap<>();
        for (Category category : Category.values()) {
            stringCategoryMap.put(category.name(), category);
        }
        return stringCategoryMap;
    }

    /**
     * Return a hashmap with enum values (string, enum) for Citites
     * @return
     */
    private Map<String, Cities> initCities() {
        Map<String, Cities> stringCitiesMap = new HashMap<>();
        for (Cities cities : Cities.values()) {
            stringCitiesMap.put(cities.toString(), cities);
        }
        return stringCitiesMap;
    }

    /**
     * Retrieve Integer
     */
    private static Integer getInteger(final String field, final Object jsonChild) {
        return Integer.parseInt(((JSONObject) jsonChild).get(field).toString());
    }
    /**
     * Retrieve Double
     */
    private static Double getDouble(final String field, final Object jsonChild) {
        return Double.parseDouble(((JSONObject) jsonChild).get(field).toString());
    }
    /**
     * Retrieve String
     */
    private static String getString(final String field, final Object jsonChild) {
        return (String) ((JSONObject) jsonChild).get(field);
    }
    /**
     * Convert String to Category Enum
     */
    private static Category stringToCategory(final String categoryObject) {
        return switch (categoryObject) {
            case "Board Games" -> Category.BOARD_GAMES;
            case "Books" -> Category.BOOKS;
            case "Clothes" -> Category.CLOTHES;
            case "Sweets" -> Category.SWEETS;
            case "Technology" -> Category.TECHNOLOGY;
            case "Toys" -> Category.TOYS;
            default -> null;
        };
    }

    /**
     * Convert String to Cities Enum
     */
    private static Cities stringToCities(final String city) {
        return switch (city) {
            case "Bucuresti" -> Cities.BUCURESTI;
            case "Constanta" -> Cities.CONSTANTA;
            case "Buzau" -> Cities.BUZAU;
            case "Timisoara" -> Cities.TIMISOARA;
            case "Cluj-Napoca" -> Cities.CLUJ;
            case "Iasi" -> Cities.IASI;
            case "Craiova" -> Cities.CRAIOVA;
            case "Brasov" -> Cities.BRASOV;
            case "Braila" -> Cities.BRAILA;
            case "Oradea" -> Cities.ORADEA;
            default -> null;
        };
    }
}
