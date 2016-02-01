import com.gutti.auction.core.AuctionSystem;
import com.gutti.auction.core.Item;
import com.gutti.auction.core.User;
import com.gutti.auction.exception.ItemNotExistException;
import com.gutti.auction.exception.MinPriceException;
import com.gutti.auction.exception.NoBiddersExistException;
import com.gutti.auction.exception.UserNotExistException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Veeresh Gutti on 31/1/16.
 */
public class AuctionSystemTest {
    AuctionSystem auctionSystem = AuctionSystem.getInstance();

    @Test (expected = UserNotExistException.class)
    public void userTest() throws UserNotExistException, MinPriceException, ItemNotExistException {
        //AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.bid(1, 0, 13);
    }

    @Test (expected = ItemNotExistException.class)
    public void itemTest() throws ItemNotExistException, UserNotExistException, MinPriceException {
        //AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.registerUser(new User(1,"user1","address"));
        auctionSystem.bid(1, 1, 13);
    }

    @Test (expected = MinPriceException.class)
    public void minPriceTest() throws UserNotExistException, MinPriceException, ItemNotExistException {
        //AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.registerUser(new User(1,"user1","address"));
        auctionSystem.addItemToAuction(new Item(1,"name",10,"desc"),10);
        auctionSystem.bid(1, 1, 9);
    }

    @Test
    public void addUserTest() {
        auctionSystem = AuctionSystem.getInstance();
        assertTrue(auctionSystem.getRegisterUser().size() == 0);
        auctionSystem.registerUser(new User(1, "user1", "address"));
        assertEquals(1, auctionSystem.getRegisterUser().get(0).getUserId());
        assertNotNull(auctionSystem.getRegisterUser().get(0));
    }

    @Test
    public void testGetInstance() {
        assertNotNull(AuctionSystem.getInstance());
    }

    @Test(expected = NoBiddersExistException.class)
    public void addItemToAuctionTest() throws NoBiddersExistException {
        Item item = new Item(1, "phone", 12.12, "Smart Phone");
        //AuctionSystem auctionSystem = AuctionSystem.getInstance();
        auctionSystem.addItemToAuction(item, 5);
        assertNotNull(auctionSystem.getItems());
        assertNotNull(auctionSystem.getAuctionedItemDetail(1));
    }

    @Test
    public void MaxItemTest() {
        //AuctionSystem auctionSystem = AuctionSystem.getInstance();
        for (int i = 0; i < 100000; i++) {
            auctionSystem.addItemToAuction(new Item(123 + i, "watch" + i, 10 + i, "Apple Watch"), 5);
        }
        assertEquals(100000, auctionSystem.getItems().size());
        assertNotNull(auctionSystem.getItems().get(99999));
        assertTrue(auctionSystem.getItems().get(5000).getTime() >= 0);
    }
}