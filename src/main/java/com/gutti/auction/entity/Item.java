package com.gutti.auction.entity;

/**
 * Item Represents real world Item entity. give all information present during and after bidding process
 *
 * Created by Veeresh Gutti on 31/1/16.
 */
public class Item {
    private int itemId;
    private String itemName;
    private double itemPrice;
    private String description;

    public Item(int itemId, String itemName, double itemPrice, String description) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.description = description;
    }

    public Item() {
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  "itemId=" + itemId +
                ", itemName=" + itemName +
                ", itemPrice=" + itemPrice  +
                ", description=" + description ;
    }
}
