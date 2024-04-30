import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class AddUsersToChatMessageTest {
    
    private AddUsersToChatMessage addUsersToChatMessage;
    private String chatId;
    private List<String> newParticipantIds;
    private String username;
    private ChatRoom chatRoom;

    @BeforeEach
    void setUp() {
        chatId = "chat";
        username = "user";
        newParticipantIds = Arrays.asList("user1", "user2");
        chatRoom = new ChatRoom(); 
        addUsersToChatMessage = new AddUsersToChatMessage(username, chatId, newParticipantIds);
    }

    @Test
    void testMessageCreation() {
        assertEquals(chatId, addUsersToChatMessage.getChatId(), "Chat ID should match the one set in constructor");
        assertEquals(newParticipantIds, addUsersToChatMessage.getNewParticipantIds(), "Participant IDs should match the ones set in constructor");
        assertEquals(username, addUsersToChatMessage.getUsername(), "Username should match the one set in constructor");
    }

    @Test
    void testSetAndGetAddedChat() {
        addUsersToChatMessage.setAddedChat(chatRoom);
        assertSame(chatRoom, addUsersToChatMessage.getAddedChat(), "Added chat room should be the same as the one set");
    }

    @Test
    void testMessageParticipants() {
        assertFalse(addUsersToChatMessage.getNewParticipantIds().isEmpty(), "New participant IDs should not be empty");
    }

    @Test
    void testMessageParticipantsSize() {
        assertEquals(2, addUsersToChatMessage.getNewParticipantIds().size(), "There should be two new participants");
    }

    @Test
    void testNullParticipants() {
        AddUsersToChatMessage nullParticipantsMessage = new AddUsersToChatMessage(username, chatId, null);
        assertNull(nullParticipantsMessage.getNewParticipantIds(), "Participant IDs should be null if set to null in constructor");
    }
}
