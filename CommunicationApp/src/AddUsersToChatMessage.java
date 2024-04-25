import java.util.List;

public class AddUsersToChatMessage extends ServerMessage {
    private String chatId; 
    private List<String> newParticipantIds;

    public AddUsersToChatMessage(String chatId, List<String> newParticipantIds) {
        super(MessageTypes.ADD_USERS_TO_CHAT, null);
        this.chatId = chatId;
        this.newParticipantIds = newParticipantIds;
    }

    public String getChatId() {
        return chatId;
    }

    public List<String> getNewParticipantIds() {
        return newParticipantIds;
    }
}
