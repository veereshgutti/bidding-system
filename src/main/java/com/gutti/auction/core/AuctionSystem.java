package com.gutti.auction.core;

import com.gutti.auction.exception.ItemNotExistException;
import com.gutti.auction.exception.MinPriceException;
import com.gutti.auction.exception.NoBiddersExistException;
import com.gutti.auction.exception.UserNotExistException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionSystem {
    private static AuctionSystem auctionSystem;
    private static Timer timer;
    private Map<Integer,AuctionItem> items = new ConcurrentHashMap<>();
    private Map<Integer,User> users =new HashMap<>();
    private Map<Integer,Map<Integer,Double>> auctionedItems = new ConcurrentHashMap<>();

    private AuctionSystem(){
        timer = new Timer();
    }

    public static synchronized AuctionSystem getInstance(){
        if(auctionSystem == null)
            auctionSystem = new AuctionSystem();
        return auctionSystem;
    }

    public void registerUser(User user){
        users.put(user.getUserId(), user);
    }

    public List<User> getRegisterUser(){
        List userList = new ArrayList();
        for (Map.Entry<Integer, User> integerUserEntry : users.entrySet()) {
            userList.add(integerUserEntry.getValue());
        }
        return userList;
    }

    public void addItemToAuction(Item item, long time){
        items.put(item.getItemId(), new AuctionItem(item,time));
        timer.schedule(new ItemTimeout(item.getItemId()),time * 60 *1000);
    }

    public List<AuctionItem> getItems(){
        List<AuctionItem> list = new ArrayList();
        for (Map.Entry<Integer, AuctionItem> integerItemEntry : items.entrySet()) {
            list.add(integerItemEntry.getValue());
        }
        return list;
    }

    public synchronized void removeItemFromAuction(int itemId) {
        AuctionWinner lowestBiddingUser = getLowestBiddingUser(itemId);
        if(lowestBiddingUser == null){
            System.out.println("No User bid for this item");
            items.remove(itemId);
            auctionedItems.remove(itemId);
            return;
        }
        System.out.println("Winner");
        System.out.println(String.format("User %s won %s item for %f Price", lowestBiddingUser.getUser().getUserName(),
                lowestBiddingUser.getItem().getItemName(),lowestBiddingUser.getPrice()));
        items.remove(itemId);
        auctionedItems.remove(itemId);
    }

    private AuctionWinner getLowestBiddingUser(int itemId) {
        Map<Integer, Double> itemAuctionMap = auctionedItems.get(itemId);
        if(itemAuctionMap != null ){
            Map<Integer, Double> userItemMap = sortByValues(itemAuctionMap);
            Integer userID = (Integer) userItemMap.keySet().toArray()[0];

            AuctionWinner auctionWinner = new AuctionWinner();
            auctionWinner.setUser(users.get(userID));
            auctionWinner.setItem(items.get(itemId).getItem());
            auctionWinner.setPrice(userItemMap.get(userID));
            return auctionWinner;
        }
        return null;
    }

    public synchronized boolean bid(int itemID, int userID,double biddingPrice) throws UserNotExistException, ItemNotExistException, MinPriceException {
        if(!isUserExist(userID)){
            System.out.println("Cannot Bid, User does not Exist");
            throw new UserNotExistException("Cannot Bid, User does not Exist" + userID);
        }
        if(!isItemExist(itemID)){
            System.out.println("Cannot Bid, Item does not exist");
            throw new ItemNotExistException("Cannot Bid, Item does not Exist" + itemID);
        }
        if(!checkItemMinPrice(itemID, biddingPrice)){
            System.out.println("Cannot Bid, Price lesser than minimum item Price" );
            throw new MinPriceException("Cannot Bid, Price lesser than minimum item Price" );
        }
        if(auctionedItems.get(itemID) == null){
            Map map = new HashMap<>();
            map.put(userID,biddingPrice);
            auctionedItems.put(itemID,map);
        }
        else {
            auctionedItems.get(itemID).put(userID,biddingPrice);
        }
        return true;
    }

    public synchronized Map<Integer,Double> getAuctionedItemDetail(int itemID) throws NoBiddersExistException {
        Map<Integer, Double> auctionedItemsUserMap = auctionedItems.get(itemID);
        if(auctionedItemsUserMap == null){
            throw new NoBiddersExistException("No bidders for item " + itemID);
        }
        return sortByValues(auctionedItemsUserMap);
    }

    private boolean checkItemMinPrice(int itemID, double biddingPrice) {
        return biddingPrice > items.get(itemID).getItem().getItemPrice() ? true : false;
    }

    private boolean isItemExist(int itemID) {
        return items.containsKey(itemID);
    }

    private boolean isUserExist(int userId){
        return users.containsKey(userId);
    }

    private Map<Integer, Double> sortByValues(Map<Integer, Double> unsortMap) {
        List<Map.Entry<Integer, Double>> list =
                new ArrayList<>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Map<Integer, Double> sortedMap = new LinkedHashMap();
        for (Iterator<Map.Entry<Integer, Double>> it = list.iterator(); it.hasNext();) {
            Map.Entry<Integer, Double> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}