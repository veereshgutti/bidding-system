import com.gutti.auction.entity.User;
import com.gutti.auction.core.UserBase;
import com.gutti.auction.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Veeresh Gutti on 4/2/16.
 */
public class UserBaseTest {
    UserBase userBase;
    @Before
    public void setup(){
        userBase = UserBase.getInstance();
    }
    @Test
    public void addUserTest() throws UserAlreadyExistException {
        UserBase.getInstance();
        assertTrue(userBase.getRegisteredUsers().size() == 0);
        userBase.registerUser(new User(1, "user1", "address"));
        assertTrue(userBase.getRegisteredUsers().size() == 1);
        assertEquals(1, userBase.getRegisteredUsers().get(0).getUserId());
        assertEquals("user1", userBase.getRegisteredUsers().get(0).getUserName());
        assertEquals("address", userBase.getRegisteredUsers().get(0).getAddress());
        assertNotNull(userBase.getRegisteredUsers().get(0));
    }

    @Test
    public void userExisttest(){
        assertTrue(userBase.isUserExist(1));
        assertFalse(userBase.isUserExist(2));
        assertNotNull(userBase.getUserDetails(1));
        assertNull(userBase.getUserDetails(2));
    }

}
