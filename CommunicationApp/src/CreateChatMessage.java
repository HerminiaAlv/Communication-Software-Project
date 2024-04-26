import java.util.List;

public class CreateChatMessage extends ServerMessage {
    private List<String> participantIds; 
    private String chatName; 

    public CreateChatMessage(List<String> participantIds, String chatName) {
       this.setType(MessageTypes.CREATE_CHAT);
       this.setStatus(MessageStatus.PENDING);
        this.participantIds = participantIds;
        this.chatName = chatName;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    public String getChatName() {
        return chatName;
    }
}
