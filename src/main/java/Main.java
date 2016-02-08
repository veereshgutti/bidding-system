import com.gutti.auction.core.*;
import com.gutti.auction.entity.AuctionItem;
import com.gutti.auction.entity.Item;
import com.gutti.auction.entity.User;
import com.gutti.auction.exception.*;

import java.util.Map;
import java.util.Scanner;

/**
 * To Check all basic functionality of bidding system, by typical switch case statements.
 * Created by Veeresh Gutti on 31/1/16.
 */
public class Main {

    public static void main(String[] args) {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        UserBase userBase = UserBase.getInstance();

        auctionSystem.addItemToAuction(new Item(123, "watch", 10, "Apple Watch"), 1);
        auctionSystem.addItemToAuction(new Item(124, "watch", 15, "Apple Watch 16GB"), 2);
        try {
            userBase.registerUser(new User(1,"veeresh","Bangalore"));
            userBase.registerUser(new User(2,"gutti","Bangalore"));
        } catch (UserAlreadyExistException e) {
            e.printStackTrace();
        }

        System.out.println("WelCome to Auction System");
        while(true){
            System.out.println("1: Add Item to Auction\n2: List auctioned Items");
            System.out.println("3: Register User\n4: List Registered User");
            System.out.println("5: Bid for Item\n6: List the biddings");
            Scanner scanner = new Scanner(System.in);
            int option = Integer.parseInt(scanner.next());
            switch (option){
                case 1:
                    Item item1 = new Item();
                    System.out.print("Item  Description: ");
                    item1.setDescription(scanner.next());
                    System.out.print("Item  Timeout in Minutes: ");
                    auctionSystem.addItemToAuction(item1, scanner.nextLong());
                    System.out.println("Item Added to Auction");
                    break;
                case 2:
                    System.out.println("Items available For auction");
                    for (AuctionItem item : auctionSystem.getItems()) {
                        System.out.println(item);
                    }
                    break;
                case 3:
                    User user = new User();
                    System.out.print("User ID:");
                    user.setUserId(scanner.nextInt());
                    System.out.print("User Name:");
                    user.setUserName(scanner.next());
                    System.out.print("User Address:");
                    user.setAddress(scanner.next());
                    try {
                        userBase.registerUser(user);
                    } catch (UserAlreadyExistException e) {
                        e.printStackTrace();
                    }
                    System.out.println("User successfuly registerd");
                    break;
                case 4:
                    System.out.println("List of Registered User");
                    for (User user1 : userBase.getRegisteredUsers()) {
                        System.out.println(user1);
                    }
                    break;
                case 5:
                    System.out.print("Your user id:");
                    int uid = scanner.nextInt();
                    System.out.print("item Id:");
                    int itemId = scanner.nextInt();
                    System.out.print("Enter bid price:");
                    double price = scanner.nextDouble();
                    try {
                        auctionSystem.bid(itemId,uid,price);
                    } catch (UserNotExistException e) {
                        e.printStackTrace();
                    } catch (ItemNotExistException e) {
                        e.printStackTrace();
                    } catch (MinPriceException e) {
                        e.printStackTrace();
                    }
                    break;
                case 6:
                    System.out.print("Item ID:");
                    Map<Integer, Double> itemDetail = null;
                    try {
                        itemDetail = auctionSystem.getAuctionedItemDetail(scanner.nextInt());
                    } catch (NoBiddersExistException e) {
                        e.printStackTrace();
                    }
                    if(itemDetail != null) {
                        for (Map.Entry<Integer, Double> integerDoubleEntry : itemDetail.entrySet()) {
                            System.out.println(String.format("User: %s, Bidding Price: %f", integerDoubleEntry.getKey(), integerDoubleEntry.getValue()));
                        }
                    } else
                        System.out.println("No Bidders for this Item");
                default:
                    System.out.println("Enter Option: ");
            }
        }
    }
}
