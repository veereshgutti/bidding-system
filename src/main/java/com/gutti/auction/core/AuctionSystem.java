package com.gutti.auction.core;

import com.gutti.auction.entity.AuctionItem;
import com.gutti.auction.entity.AuctionWinner;
import com.gutti.auction.entity.Item;
import com.gutti.auction.exception.ItemNotExistException;
import com.gutti.auction.exception.MinPriceException;
import com.gutti.auction.exception.NoBiddersExistException;
import com.gutti.auction.exception.UserNotExistException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Bigging-system for selling item by putting then on auction for specified time period.
 * Registered users can bid for items register using this class. before user bids for items, it validates, item,user and price.
 * On Expiry items will removed from auction system, and winner is declared.
 *
 * Created by Veeresh Gutti on 31/1/16.
 *
 */
public class AuctionSystem extends Observable{
    private static AuctionSystem auctionSystem;
    private static Timer timer;
    private Map<Integer,AuctionItem> items = new ConcurrentHashMap<>();
    private Map<Integer,Map<Integer,Double>> auctionedItems = new ConcurrentHashMap<>();
    private UserBase userBase;
    private Map<Integer,AuctionWinner> auctionWinnerMap = new HashMap<>();

    private AuctionSystem(){
        timer= new Timer();
        userBase = UserBase.getInstance();
    }

    public static synchronized AuctionSystem getInstance(){
        if(auctionSystem == null)
            auctionSystem = new AuctionSystem();
        return auctionSystem;
    }

    /**
     * This Method add items to Aution system, if item is already there it will override.
     * @param item item to be added to Auction system
     * @param minutes item expiry delay in minutes.
     */
    public void addItemToAuction(Item item, long minutes){
        items.put(item.getItemId(), new AuctionItem(item,minutes));
        //ItemTimeout.setItemTimeout(item.getItemId(), minutes * 60 * 1000);
        timer.schedule(new ItemTimeout(item.getItemId()), minutes * 60 * 1000);
    }

    /**
     * Checks whether item exists in bidding system, before user start biddig on this item
     * @param itemID item to be checked whether exist in system
     * @return true if exist else false
     */
    private boolean isItemExist(int itemID) {
        return items.containsKey(itemID);
    }

    /**
     * All item currently present in bidding system for user to check before start bidding
     * @return
     */
    public List<AuctionItem> getItems(){
        List<AuctionItem> list = new ArrayList();
        for (Map.Entry<Integer, AuctionItem> integerItemEntry : items.entrySet()) {
            list.add(integerItemEntry.getValue());
        }
        return list;
    }

    /**
     * Winner of item with highest bidding price for itemID
     * @param itemId itemId for bidding is over
     * @return AuctionWinner - item details, user details and winning price.
     */
    public synchronized AuctionWinner getItemWinnerDetails(Integer itemId){
        return auctionWinnerMap.get(itemId);
    }

    /**
     * execute on expiry of bidding time for an item, adds item to auctionWinnerMap.
     * Removes item from bidding system.
     * @param itemId
     */
    public synchronized void removeItemFromAuction(int itemId) {
        AuctionWinner lowestBiddingUser = getLowestBiddingUser(itemId);
        auctionWinnerMap.put(itemId,lowestBiddingUser);
        if(lowestBiddingUser == null){
            System.out.println("No User bid for this item");
            items.remove(itemId);
            auctionedItems.remove(itemId);
            return ;
        }
        System.out.println("Winner");
        System.out.println(String.format("User %s won %s item for %f Price", lowestBiddingUser.getUser().getUserName(),
                lowestBiddingUser.getItem().getItemName(),lowestBiddingUser.getPrice()));
        items.remove(itemId);
        auctionedItems.remove(itemId);
    }

    /**
     * Returns lowest bidder for item
     * @param itemId
     * @return AuctionWinner
     */
    private AuctionWinner getLowestBiddingUser(int itemId) {
        Map<Integer, Double> itemAuctionMap = auctionedItems.get(itemId);
        if(itemAuctionMap != null ){
            Map<Integer, Double> userItemMap = sortByValues(itemAuctionMap);
            Integer userID = (Integer) userItemMap.keySet().toArray()[0];

            AuctionWinner auctionWinner = new AuctionWinner();
            auctionWinner.setUser(userBase.getUserDetails(userID));
            auctionWinner.setItem(items.get(itemId).getItem());
            auctionWinner.setPrice(userItemMap.get(userID));
            return auctionWinner;
        }
        return null;
    }

    /**
     * Method to bid for an item with existing User, item should be present in bidding system,
     * @param itemID already existing item in bidding system.
     * @param userID Only Register user
     * @param biddingPrice biddingPrice should be greater than item initial price
     * @return
     * @throws UserNotExistException - If user Not Registered
     * @throws ItemNotExistException - If item Not present in bidding system.
     * @throws MinPriceException - If min price check failes
     */
    public synchronized boolean bid(int itemID, int userID,double biddingPrice) throws UserNotExistException, ItemNotExistException, MinPriceException {
        if(!userBase.isUserExist(userID)){
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
            auctionedItems.get(itemID).put(userID, biddingPrice);
        }
        return true;
    }

    /**
     * Returns all auctioned items with which all user bidding for that item sorted by highest to lowest price.
     * @param itemID
     * @return
     * @throws NoBiddersExistException
     */
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

    /**
     * Sorting bidding items based on there price in descending order.
     * @param unsortMap
     * @return
     */
    private Map<Integer, Double> sortByValues(Map<Integer, Double> unsortMap) {
        List<Map.Entry<Integer, Double>> list =
                new ArrayList<>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>() {
            public int compare(Map.Entry<Integer, Double> o1,
                               Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
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