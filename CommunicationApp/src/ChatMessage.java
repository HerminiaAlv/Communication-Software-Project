// This is the Message you send to the server when then user hits send on their client
// It contains a single Message
// It's status is automatically set to PENDING
// It's type is automatically set to CHAT_MESSAGE

import java.util.ArrayList;
import java.util.List;

public class ChatMessage extends ServerMessage {
    private Message message;
    private List <String> Participants = new ArrayList<String>();

    public ChatMessage(Message message) {
        this.message = message;
        this.setType(MessageTypes.CHAT_MESSAGE);
        this.setStatus(MessageStatus.PENDING);
    }

    // Getters and setters 

/*     public ChatMessage(String mssg, MessageStatus sent, MessageTypes chatMessage) {
        //TODO Auto-generated constructor stub
        this.message = new Message;
        this.setType(MessageTypes.CHAT_MESSAGE);
        this.setStatus(MessageStatus.PENDING);
    } */

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public List<String> getParticipants() {
        return Participants;
    }

    public void setParticipants(List<String> participants) {
        Participants = participants;
    }

}