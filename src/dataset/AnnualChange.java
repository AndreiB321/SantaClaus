package dataset;

import enums.CityStrategyEnum;

import java.util.List;

public class AnnualChange extends Entity {
    private Double newSantaBudget;
    private List<Gift> newGifts;
    private List<Child> newChildren;
    private List<ChildUpdate> childUpdates;
    private CityStrategyEnum strategy;

    public AnnualChange() {
    }

    /**
     * Set up instance from Entity.
     */

    public final AnnualChange populateEntity(final Double newSantaBudgetEntity,
                                             final List<Gift> newGiftsEntity,
                                             final List<Child> newChildrenEntity,
                                             final List<ChildUpdate> childUpdatesEntity,
                                             final CityStrategyEnum strategyEntity) {
        this.newSantaBudget = newSantaBudgetEntity;
        this.newGifts = newGiftsEntity;
        this.newChildren = newChildrenEntity;
        this.childUpdates = childUpdatesEntity;
        this.strategy = strategyEntity;
        return this;
    }

    /**
     * Retrieve Santa's budget
     * @return
     */
    public Double getNewSantaBudget() {
        return newSantaBudget;
    }
    /**
     * Set Santa's budget
     * @return
     */
    public void setNewSantaBudget(final Double newSantaBudget) {
        this.newSantaBudget = newSantaBudget;
    }

    /**
     * Retrieve new gifts
     * @return
     */
    public List<Gift> getNewGifts() {
        return newGifts;
    }

    /**
     * Set new gifts
     * @return
     */
    public void setNewGifts(final List<Gift> newGifts) {
        this.newGifts = newGifts;
    }

    /**
     * Retrieve new children
     * @return
     */
    public List<Child> getNewChildren() {
        return newChildren;
    }
    /**
     * Set new children
     * @return
     */
    public void setNewChildren(final List<Child> newChildren) {
        this.newChildren = newChildren;
    }

    /**
     * Retrieve child update
     * @return
     */
    public List<ChildUpdate> getChildUpdates() {
        return childUpdates;
    }

    /**
     * return strategy
     * @return
     */
    public CityStrategyEnum getStrategy() {
        return strategy;
    }

    /**
     * set strategy
     * @param strategy
     */
    public void setStrategy(final CityStrategyEnum strategy) {
        this.strategy = strategy;
    }
}
