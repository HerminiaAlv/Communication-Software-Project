import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class ChatRoomTest {

	private ChatRoom chatRoom;
	private Message message;
	private User user;
	
	@BeforeEach
	void setUp() {
		chatRoom = new ChatRoom(new ArrayList(Arrays.asList("user1", "user2")));
		message = new Message("Test", "user1", "chat1");
		user = new User();
        user.setUserName("user3");
	} 
	@Test
	void testGetChatID() {
        assertNotNull(chatRoom.getChatID(), "Chat ID should be automatically generated.");
    }
	
	@Test
    void testSetAndGetFilename() {
//        chatRoom.setFilename(".txt");
//        assertEquals(".txt", chatRoom.getFilename(), "Filename should be .txt.");
	}

	@Test
    void testSetandGetParticipants() {
        chatRoom.setParticipants(new ArrayList<>(Arrays.asList("user3", "user4")));
        assertTrue(chatRoom.getParticipants().contains("user3") && chatRoom.getParticipants().contains("user4"), "Participants should include user3 and user4.");
    }
	
	@Test
    void testAddMessage() {
        chatRoom.addMessage(message);
        assertEquals(1, chatRoom.getMessages().size(), "There should be one message in the chat room.");
        assertEquals(message, chatRoom.getMessages().get(0), "The message should be the one added.");
    }
	
	@Test
    void testAddMember() {
        assertTrue(chatRoom.addMember(user), "Should return true when a new member that wasn't in the chat is added.");
        assertFalse(chatRoom.addMember(user), "Should return false when a member who is already in the chat is added.");
    }

    @Test
    void testMessageExists() {
        chatRoom.addMessage(message);
        assertTrue(chatRoom.messageExists(message.getID()), "Should be true when the message ID exists in the chat room.");
        assertFalse(chatRoom.messageExists("not found"), "Should return false for a message ID that doesn't exist.");
    }

    @Test
    void testIsParticipant() {
        assertTrue(chatRoom.isParticipant("user1"), "user1 should be a participant.");
        assertFalse(chatRoom.isParticipant("user5"), "user5 should not be a participant.");
    }

    @Test
    void testToString() {
        assertEquals("user1, user2, ", chatRoom.toString(), "Should correctly list participants.");
    }
}
   