package dataset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.Category;
import enums.ElvesType;

public final class Gift extends Entity {
    private String productName;
    private double price;
    private Category category;
    @JsonIgnore
    private int quantity;

    public Gift() {
    }

    /**
     * Set up instance from Entity.
     */
    public Gift populateEntity(final String productNameEntity, final Double priceEntity,
                               final Category categoryEntity, final int quantityEntity) {
        this.productName = productNameEntity;
        this.price = priceEntity;
        this.category = categoryEntity;
        this.quantity = quantityEntity;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
