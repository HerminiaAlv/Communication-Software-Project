import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserMessageTest {
    private UpdateUserMessage message;
    private String userId;
    private Map<String, String> updates;

    @BeforeEach
    void setUp() {
        userId = "testUser";
        updates = new HashMap<>();       
        message = new UpdateUserMessage(userId, updates);
    }

    @Test
    void testUserIdIsCorrect() {
        assertEquals(userId, message.getUserId(), "The userId should match the one provided at construction.");
    }

}
