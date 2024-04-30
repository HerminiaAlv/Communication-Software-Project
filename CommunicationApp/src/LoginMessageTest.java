import static org.junit.Assert.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LoginMessageTest {
    private LoginMessage message;
    private User testUser;
    private Map<String, User> userMap;

    @Before
    public void setUp() {
        message = new LoginMessage("password", "username");
        testUser = new User("1", "First", "Last", "fakeUsername", "fakePassword", false, null);
        userMap = new HashMap<>();
        userMap.put("fakeUsername", testUser);
    }

    @Test
    public void testMessageCreation() {
        assertEquals("username", message.getUsername());
        assertEquals("password", message.getPassword());
        assertFalse("Initial state should be unsuccessful", message.isSuccessful());
    }

    @Test
    public void testSuccess() {
        message.setSuccess(true);
        assertTrue("Login should be successful", message.isSuccessful());
    }
    
    @Test
    public void testSetGetCurrentUser() {
        message.setCurrentUser(testUser);
        assertEquals("The current user should be set correctly.", testUser, message.getUser());
    }

    @Test
    public void testSetGetAllUsers() {
        message.setAllUsers(userMap);
        assertEquals("All users map should be retrieved correctly.", userMap, message.getAllUsers());
        assertEquals("Correct user should be retrieved from map.", testUser, message.getAllUsers().get("fakeUsername"));
    }
}
