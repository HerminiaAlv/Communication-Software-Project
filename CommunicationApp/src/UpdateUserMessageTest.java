import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserMessageTest {
    private UpdateUserMessage message;
    private String userId;
    private String password;
    private String firstname;
    private String lastname;

    @BeforeEach
    void setUp() {
        userId = "testUser";
        password = "testPassword";
        firstname = "Jose";
        lastname = "Galeana";
        message = new UpdateUserMessage(userId, password, firstname, lastname);
    }

    @Test
    void testUserId() {
        assertEquals(userId, message.getUserId(), "The userId should be testUser.");
    }

    @Test
    void testPassword() {
        assertEquals(password, message.getPassword(), "The password should be testPassword.");
    }

    @Test
    void testFirstname() {
        assertEquals(firstname, message.getFirstname(), "The firstname should be Jose.");
    }

    @Test
    void testLastname() {
        assertEquals(lastname, message.getLastname(), "The lastname should be Galeana.");
    }
}
