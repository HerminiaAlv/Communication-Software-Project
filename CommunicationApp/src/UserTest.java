import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("1", "Jose", "Galeana", "username", "password", false, Collections.emptyList());
    }

    @Test
    void testSetFirstName() {
        user.setFirstName("First");
        assertEquals("First", user.getFirstName(), "The first name should be First.");
    }

    @Test
    void testSetLastName() {
        user.setLastName("Last");
        assertEquals("Last", user.getLastName(), "The last name should be Last.");
    }

    @Test
    void testSetUsername() {
        user.setUserName("username1");
        assertEquals("username1", user.getUsername(), "The username should be username1.");
    }

    @Test
    void testSetPassword() {
    	user.setPassword("newPassword");
    	assertEquals("newPassword", user.getPassword(), "The password should match the new password.");
    }
    
    @Test
    void testGetFirstName() {
    	assertEquals("Jose", user.getFirstName(), "The first name should be Jose.");
    }
    
    @Test
    void testGetLasttName() {
    	assertEquals("Galeana", user.getLastName(), "The first name should be Last.");
    }
    
    @Test
    void testGetUsername() {
        assertEquals("username", user.getUsername(), "The username should be username.");
    }
    
    @Test
    void testGetPassword() {
        user.getPassword();
        assertEquals("password", user.getPassword(), "The password should be password.");
    }

    @Test
    void testStatus() {
        user.setStatus(UserStatus.Online);
        assertEquals(UserStatus.Online, user.getStatus(), "The status should be Online.");
    }

    @Test
    void testIsIT() {
        user.setIT(true);
        assertTrue(user.isIT(), "isIT should be true.");
    }

    @Test
    void testAddChat() {
        
    }
}
