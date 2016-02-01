package com.gutti.auction.core;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionWinner {
    private User user;
    private Item item;
    private Double price;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
