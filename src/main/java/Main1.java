import com.gutti.auction.core.AuctionSystem;
import com.gutti.auction.core.Item;
import com.gutti.auction.core.User;
import com.gutti.auction.exception.ItemNotExistException;
import com.gutti.auction.exception.MinPriceException;
import com.gutti.auction.exception.NoBiddersExistException;
import com.gutti.auction.exception.UserNotExistException;

import java.util.Random;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class Main1 {
    public static void main(String[] args) {
        //final AuctionSystem auctionSystem = AuctionSystem.getInstance();
        //add Item Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                AuctionSystem auctionSystem = AuctionSystem.getInstance();
                for (int i = 1; i <= 100; i++) {
                    auctionSystem.addItemToAuction(new Item(i, "watch" + i, 10, "Apple Watch"), 1);
                        //System.out.println("item added: " + auctionSystem.getItems());
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }).start();

        //add User Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                AuctionSystem auctionSystem = AuctionSystem.getInstance();
                for (int i = 1; i <= 10; i++) {
                    auctionSystem.registerUser(new User(i,"name" +i,"address"+i));
                    //System.out.println("userAdded: " + auctionSystem.getRegisterUser());
                    /*try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }).start();
        //bidding thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                AuctionSystem auctionSystem = AuctionSystem.getInstance();
                for(int j=1; j<= 10;j++) {
                    for (int i = 1; i <= 100; i++) {
                        try {
                            auctionSystem.getRegisterUser();
                            auctionSystem.bid(i * j, j, Math.abs(new Random().nextInt(90) + 10));
                            System.out.println(i*j+" : "+auctionSystem.getAuctionedItemDetail(i*j));
                        } catch (UserNotExistException e) {
                            e.printStackTrace();
                        } catch (ItemNotExistException e) {
                            e.printStackTrace();
                        } catch (MinPriceException e) {
                            e.printStackTrace();
                        } catch (NoBiddersExistException e) {
                            e.printStackTrace();
                        }
                        /*try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            }
        }).start();
    }
}
