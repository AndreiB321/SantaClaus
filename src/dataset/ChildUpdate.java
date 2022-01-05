package dataset;

import enums.Category;
import enums.ElvesType;

import java.util.List;

public class ChildUpdate extends Entity {
    private int id;
    private Double newNiceScore;
    private List<Category> newGiftPreferences;
    private ElvesType elf;

    public ChildUpdate() {
    }
    /**
     * Set up instance from Entity.
     */
    public ChildUpdate populateEntity(final int idEntity, final Double newNiceScoreEntity,
                                      final List<Category> newGiftPreferencesEntity,
                                      final ElvesType elfEntity) {
        this.id = idEntity;
        this.newNiceScore = newNiceScoreEntity;
        this.newGiftPreferences = newGiftPreferencesEntity;
        this.elf = elfEntity;
        return this;
    }

    /**
     * Retrieve id
     * @return
     */
    public int getId() {
        return id;
    }
    /**
     * Set id
     * @return
     */
    public void setId(final int id) {
        this.id = id;
    }
    /**
     * Retrieve score
     * @return
     */
    public Double getNewNiceScore() {
        return newNiceScore;
    }
    /**
     * Retrieve gift preferences
     * @return
     */
    public List<Category> getNewGiftPreferences() {
        return newGiftPreferences;
    }

    public void setNewNiceScore(Double newNiceScore) {
        this.newNiceScore = newNiceScore;
    }

    public void setNewGiftPreferences(List<Category> newGiftPreferences) {
        this.newGiftPreferences = newGiftPreferences;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(ElvesType elf) {
        this.elf = elf;
    }
}
