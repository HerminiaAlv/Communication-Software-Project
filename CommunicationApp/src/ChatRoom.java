import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatRoom implements Serializable {
    private String chatID; // what is this going to be?
    private String filename; // filename on server
    private List<User> participants;
    private List<Message> messages;

    // Constructor for a new chatroom from a user
    public ChatRoom(List<User> participants) {
        this.participants = participants;
        this.messages = new ArrayList<>();
        //this.filename = "";
        this.chatID = UUID.randomUUID().toString();
    }

    // Constructor for a complete object - loading from server
    public ChatRoom(List<User> participants, List<Message> messages, String chatID, String filename) {
        this.chatID = chatID;
        this.filename = filename;
        this.participants = participants;
        this.messages = messages;
    }

    // Public Methods
    // Getters
    public String getChatID() {
        return chatID;
    }

    public String getFilename() {
        return filename;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMesssages() {
        return messages;
    }

    // Other public methods
    public boolean addMessage(Message msg) {
        if (!messageExists(msg.getID())) { 
            messages.add(msg);
            return true;
        }
        return false; // not added
    }

    public boolean addMember(User user) {
        if (!isParticipant(user.getUsername())){
            participants.add(user);
            return true;
        }
        return false;
    }

    public boolean messageExists(String messageID) {
        for (Message m : messages) {
            if (messageID == m.getID())
                return true;
        }
        // not found
        return false;
    }

    public boolean isParticipant(String userID) {
        for (User user : participants) {
            if (userID == user.getUsername())
                return true;
        }
        // not found
        return false;
    }

    // private methods
}