import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Formatter;

public class ChatMessage extends ServerMessage {
    private String message;
    private MessageStatus status;
    private MessageTypes type;
    private LocalDateTime timestamp;
    private String formattedTimestamp;
    private User sender;

    public ChatMessage(String message, MessageStatus status, MessageTypes type) {
        this.message = message;
        this.status = status;
        setType(MessageTypes.CHAT_MESSAGE);
    }

    // Getters and setters 

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.formattedTimestamp = timestamp.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"));
    } 

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String toString() {
        User sender = new User();
        sender.setUserName("Dummy Sender");
        return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + message;
    }
}