package dataset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Category;
import enums.Cities;
import enums.ElvesType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Child extends Entity {
    private int id;
    private String lastName;
    private String firstName;
    private Cities city;
    private int age;
    private List<Category> giftsPreferences;
    private double averageScore;
    private List<Double> niceScoreHistory;
    private double assignedBudget;
    private List<Gift> receivedGifts = new ArrayList<>();
    @JsonIgnore
    private Double bonusNiceScore;
    @JsonIgnore
    private ElvesType elf;


    public Child() {
    }


    private Child(final ChildBuilder builder) {
        this.id = builder.id;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.city = builder.city;
        this.age = builder.age;
        this.giftsPreferences = builder.giftsPreferences;
        this.niceScoreHistory = builder.niceScoreHistory;
        this.bonusNiceScore = builder.bonusNiceScore;
        this.elf = builder.elf;
    }

    public static class ChildBuilder {
        private int id;
        private String lastName;
        private String firstName;
        private Cities city;
        private int age;
        private List<Category> giftsPreferences;
        private List<Double> niceScoreHistory;
        private Double bonusNiceScore = 0.0;
        private ElvesType elf;


        public ChildBuilder(final int id, final String lastName,
                            final String firstName, final Cities city,
                            final int age, final List<Category> giftsPreferences,
                            final List<Double> niceScoreHistory, final ElvesType elf) {
            this.id = id;
            this.lastName = lastName;
            this.firstName = firstName;
            this.city = city;
            this.age = age;
            this.giftsPreferences =  giftsPreferences.stream().distinct()
                    .collect(Collectors.toList());
            this.niceScoreHistory = niceScoreHistory;
            this.elf = elf;
        }

        /**
         * set niceScoreBonus
         * @param bonusNiceScoreid
         * @return
         */
        public ChildBuilder bonusNiceScore(final Double bonusNiceScoreid) {
            this.bonusNiceScore = bonusNiceScoreid;
            return this;
        }

        /**
         * builder
         * @return
         */
        public Child build() {

            return new Child(this);
        }
    }


    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(final Cities city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final List<Category> giftsPreference) {
        this.giftsPreferences = giftsPreference;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(final double averageScore) {
        this.averageScore = averageScore;
    }

    public List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public void setNiceScoreHistory(final List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(final double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public void setReceivedGifts(final List<Gift> gifts) {
        this.receivedGifts = gifts;
    }

    public Double getBonusNiceScore() {
        return bonusNiceScore;
    }

    public void setBonusNiceScore(final Double bonusNiceScore) {
        this.bonusNiceScore = bonusNiceScore;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }

}
