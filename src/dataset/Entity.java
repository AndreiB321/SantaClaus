package dataset;

import enums.Category;
import enums.CityStrategyEnum;
import enums.ElvesType;

import java.util.List;

public class Entity {
    /**
     * Populate Entity of Gift
     */
    public Gift populateEntity(final String productName,
                               final Double price,
                               final Category category,
                               final int quantityEntity) {
        return (Gift) this;
    }
    /**
     * Populate Entity of ChildUpdate
     */
    public ChildUpdate populateEntity(final int id, final Double newNiceScore,
                                      final List<Category> newGiftPreferences,
                                      final ElvesType elfEntity) {
        return (ChildUpdate) this;
    }
    /**
     * Populate Entity of AnnualChange
     */
    public AnnualChange populateEntity(final Double newSantaBudget, final List<Gift> newGifts,
                                       final List<Child> newChildren,
                                       final List<ChildUpdate> childUpdates,
                                       final CityStrategyEnum strategyEntity) {
        return (AnnualChange) this;
    }
}
