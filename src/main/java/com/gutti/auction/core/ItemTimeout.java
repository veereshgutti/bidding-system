package com.gutti.auction.core;

import java.util.TimerTask;

/**
 * Sets Expiry time here for items putting for auction, time out delay is in minutes
 * on expiry item is removed for auction items, and winner is declared, if any user bid for that item exists
 *
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

