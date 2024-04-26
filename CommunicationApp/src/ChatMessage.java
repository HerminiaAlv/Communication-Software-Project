import java.io.Serializable;

public class ChatMessage extends ServerMessage {
    private String message;
    private MessageStatus status;
    private MessageTypes type;

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
}