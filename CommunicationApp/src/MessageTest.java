import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageTest {
    private Message message;
    private String testMessage;
    private String testSender;
    private String testChatID;
    private LocalDateTime testTimestamp =LocalDateTime.now();

    @BeforeEach
    void setUp() {
        testMessage = "test message";
        testSender = "testSender";
        testChatID = "testChatID";
        message = new Message(testMessage, testSender, testChatID, testTimestamp);
    }

    @Test
    void testGetMessageShouldMatchMessage() {
    	assertEquals(testMessage, message.getMessage());
    }

    @Test
    void testSetMessageShouldEqualNewMessage() {
        String newMessage = "New test message";
        message.setMessage(newMessage);
        assertEquals(newMessage, message.getMessage());
    }

    @Test
    void testGetSender() {
        assertEquals(testSender, message.getSender());
    }

    @Test
    void testSetSender() {
        String newSender = "NewSender";
        message.setSender(newSender);
        assertEquals(newSender, message.getSender());
    }

    @Test
    void testGetChatID() {
        assertEquals(testChatID, message.getChatID());
    }

    @Test
    void testSetChatID() {
        String newChatID = "NewChatID";
        message.setChatID(newChatID);
        assertEquals(newChatID, message.getChatID());
    }

    @Test
    void testGetTimestampShouldReturnTime() {
        assertNotNull(message.getTimestamp());
    }

    @Test
    void testSetTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.now();
        message.setTimestamp(newTimestamp);
        long secondsDifference = ChronoUnit.SECONDS.between(newTimestamp, message.getTimestamp());
        assertTrue(secondsDifference <= 1, "Timestamp should be within a second of the set time.");
    }

    @Test
    void testToStringForFile() {
        String expectedOutput = testMessage + "," + testSender + "," + testChatID + "," + testTimestamp;
        assertEquals(expectedOutput, message.toStringForFile());
    }

    @Test
    void testToString() {
        message.setTimestamp(testTimestamp);
        String expectedOutput = "[" + testTimestamp.format(DateTimeFormatter.ofPattern("MM/dd HH:mm")) + "] " + testSender + ":\n " + testMessage;
        assertEquals(expectedOutput, message.toString());
    }
}
