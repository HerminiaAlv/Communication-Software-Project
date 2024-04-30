import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {
    private ChatMessage chatMessage;
    private Message testMessage;

    @BeforeEach
    void setUp() {
        testMessage = new Message("test message", "user1", "chat1");
        chatMessage = new ChatMessage(testMessage);
    }

    @Test
    void getMessageTest() {
        assertEquals("test message", chatMessage.getMessage().getMessage(), "Should return test message.");
    }

    @Test
    void getSenderShouldTest() {
        assertEquals("user1", chatMessage.getMessage().getSender(), "Should return the sender from the setUp().");
    }
}


