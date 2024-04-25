import java.io.Serializable;

public class ChatMessage extends ServerMessage {
    private String message;
    private MessageStatus status;
    private MessageTypes type;
    private String senderID;
    private long timestamp;

    public ChatMessage(String message, MessageStatus status, MessageTypes type, String senderID, long timestamp) {
        this.message = message;
        this.status = status;
        this.type = type;
        this.senderID = senderID;
        this.timestamp = timestamp;
    }

    // Getters and setters for the class variables

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public MessageTypes getType() {
        return type;
    }

    public void setType(MessageTypes type) {
        this.type = type;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}