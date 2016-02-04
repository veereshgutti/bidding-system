import com.gutti.auction.core.AuctionSystem;
import com.gutti.auction.core.UserBase;
import com.gutti.auction.entity.Item;
import com.gutti.auction.entity.User;
import com.gutti.auction.exception.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionSystemTest {

    @Test (expected = UserNotExistException.class)
    public void userTest() throws UserNotExistException, MinPriceException, ItemNotExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();;
        auctionSystem.bid(1, 0, 13);
    }

    @Test (expected = ItemNotExistException.class)
    public void itemTest() throws ItemNotExistException, UserNotExistException, MinPriceException, UserAlreadyExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();;
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(2,"user2","address"));
        auctionSystem.bid(1, 2, 13);
    }

    @Test (expected = MinPriceException.class)
    public void minPriceTest() throws UserNotExistException, MinPriceException, ItemNotExistException, UserAlreadyExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(3,"user3","address"));
        auctionSystem.addItemToAuction(new Item(1,"name",10,"desc"),10);
        auctionSystem.bid(1, 3, 9);
    }

    @Test(expected = NoBiddersExistException.class)
    public void addItemToAuctionTest() throws NoBiddersExistException {
        Item item = new Item(2, "phone", 12.12, "Smart Phone");
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.addItemToAuction(item, 5);
        assertNotNull(auctionSystem.getItems());
        assertNotNull(auctionSystem.getAuctionedItemDetail(2));
    }

    @Test
    public void MaxItemTest() {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        for (int i = 0; i < 100000; i++) {
            auctionSystem.addItemToAuction(new Item(123 + i, "watch" + i, 1, "Apple Watch"), 5);
        }
        assertNotNull(auctionSystem.getItems().get(99999));
        assertTrue(auctionSystem.getItems().get(5000).getTime() >= 0);
    }

    @Test
    public void bidPassedTest() throws UserNotExistException, MinPriceException, ItemNotExistException, InterruptedException, UserAlreadyExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(4,"user4","user address"));
        auctionSystem.addItemToAuction(new Item(3,"item3",10,"item description"),1);
        auctionSystem.bid(3,4,11);
        Thread.sleep(60 * 1000);
        assertNotNull(auctionSystem.getItemWinnerDetails(3));
    }

    @Test
    public void noUserBidTest() throws UserNotExistException, MinPriceException, ItemNotExistException, InterruptedException, UserAlreadyExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(8,"user8","user address"));
        auctionSystem.addItemToAuction(new Item(4,"item4",10,"item description"),1);
        Thread.sleep(60 * 1000);
        assertNull(auctionSystem.getItemWinnerDetails(4));
    }

    @Test
    public void bidMultiUserTest() throws UserNotExistException, MinPriceException, ItemNotExistException, InterruptedException, UserAlreadyExistException {
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(5,"user5","user address"));
        userBase.registerUser(new User(6,"user6","user address"));
        auctionSystem.addItemToAuction(new Item(5,"item5",10,"item description"),1);
        auctionSystem.bid(5,5,11);
        auctionSystem.bid(5,6,15);
        Thread.sleep(60 * 1000);
        assertNotNull(auctionSystem.getItemWinnerDetails(5));
        assertEquals(6,auctionSystem.getItemWinnerDetails(5).getUser().getUserId());
    }

    @Test
    public void testItemTimeout() throws InterruptedException, UserAlreadyExistException, UserNotExistException, MinPriceException, ItemNotExistException {
        Item item = new Item(6, "phone", 12.12, "Smart Phone");
        UserBase userBase = UserBase.getInstance();
        userBase.registerUser(new User(7, "user7", "bangalore"));
        AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.addItemToAuction(item, 1);
        Thread.sleep(70*1000);
        assertEquals(0,auctionSystem.getItems().size());
        auctionSystem.addItemToAuction(new Item(10, "phone", 12.12, "Smart Phone"),1);
        auctionSystem.bid(10,7,13);
        Thread.sleep(70*1000);
        assertEquals(0,auctionSystem.getItems().size());
    }
}