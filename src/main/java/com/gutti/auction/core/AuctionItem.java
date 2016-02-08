package com.gutti.auction.core;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionItem {
    private Item item;
    private long time;

    public AuctionItem(Item item, long time) {
        this.item = item;
        this.time = (time * 60 * 1000) + System.currentTimeMillis();
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
                ", time left=" + (time-System.currentTimeMillis())/(60*1000) + "mins" ;
    }
}
