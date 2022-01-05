package dataset;

import common.Constants;

public final class Factory {
    private Factory() {
    }

    /**
     * Factory entity for classes
     */
    public static Entity getEntity(final String type) {
        if (type == null) {
            return null;
        }

        return switch (type) {
            case Constants.CHILD -> new Child();
            case Constants.CHILD_UPDATE -> new ChildUpdate();
            case Constants.ANNUAL_CHANGES -> new AnnualChange();
            case Constants.GIFT -> new Gift();
            default -> null;
        };
    }
}
