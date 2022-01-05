package dataset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Category;
import enums.Cities;
import enums.ElvesType;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Set up instance from Entity.
     */
    public Child populateEntity(final int idEntity, final String lastNameEntity,
                                final String firstNameEntity, final Cities cityEntity,
                                final int ageEntity,
                                final List<Category> giftsPreferenceEntity,
                                final List<Double> niceScoreHistoryEntity,
                                final Double bonusNiceScoreEntity, final ElvesType elfEntity) {
        this.id = idEntity;
        this.lastName = lastNameEntity;
        this.firstName = firstNameEntity;
        this.city = cityEntity;
        this.age = ageEntity;
        this.giftsPreferences = giftsPreferenceEntity;
        this.niceScoreHistory = niceScoreHistoryEntity;
        this.bonusNiceScore = bonusNiceScoreEntity;
        this.elf = elfEntity;
        return this;
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

    public void setBonusNiceScore(Double bonusNiceScore) {
        this.bonusNiceScore = bonusNiceScore;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(ElvesType elf) {
        this.elf = elf;
    }
}
