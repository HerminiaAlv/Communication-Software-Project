import java.util.List;

public class AddUsersToChatMessage extends ServerMessage {
    private String chatId; 
    private List<String> newParticipantIds;
    private String username;
    private ChatRoom addedChat;

    public AddUsersToChatMessage(String username, String chatId, List<String> newParticipantIds) {
        this.username = username;
    	this.chatId = chatId;
        this.newParticipantIds = newParticipantIds;
    }
    public ChatRoom getAddedChat() {
        return addedChat;
    }

    public String getChatId() {
        return chatId;
    }

    public List<String> getNewParticipantIds() {
        return newParticipantIds;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

    public void setAddedChat(ChatRoom toAdd) {
        this.addedChat = toAdd;
    }
}