package com.gutti.auction.core;

import java.util.TimerTask;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class ItemTimeout extends TimerTask{
    int itemId;
    public ItemTimeout(int itemId) {
        this.itemId = itemId;
    }
    @Override
    public void run() {
        AuctionSystem instance = AuctionSystem.getInstance();
        instance.removeItemFromAuction(itemId);
        System.out.println(String.format("item %d removed from list",itemId) );
    }
}

