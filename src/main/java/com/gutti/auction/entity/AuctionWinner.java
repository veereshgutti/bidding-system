package com.gutti.auction.entity;

import com.gutti.auction.entity.Item;
import com.gutti.auction.entity.User;

/**
 * Represents Auction winner, to declare winner need all information like which user has won the auction,
 * what are the details of user,item details and what is the winning price.
 *
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

    @Override
    public String toString() {
        return "AuctionWinner{" +
                "user=" + user +
                ", item=" + item +
                ", price=" + price +
                '}';
    }
}
