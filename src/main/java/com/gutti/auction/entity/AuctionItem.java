package com.gutti.auction.entity;

import com.gutti.auction.entity.Item;

/**
 * Contains all the item put on auction, with expiry time set for that particular item,
 * User can query bidding system at any to know all items available to bid with time left to bid.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionItem {
    private static final long ONEMINUTE = 60 * 1000;
    private Item item;
    private long time;

    public AuctionItem(Item item, long time) {
        this.item = item;
        this.time = (time * ONEMINUTE) + System.currentTimeMillis();
    }

    public AuctionItem() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return  "item=" + item +
                ", time left=" + (time-System.currentTimeMillis())/(ONEMINUTE) + "mins" ;
    }
}
