public class PinChatMessage extends ServerMessage {
    private String chatId; 
    private String username;

    public PinChatMessage(String username, String chatId) {
    	this.username = username;
        this.setType(MessageTypes.PIN_CHAT);
        this.setStatus(MessageStatus.PENDING);
        this.chatId = chatId;
       
    }

    public String getChatId() {
        return chatId;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}