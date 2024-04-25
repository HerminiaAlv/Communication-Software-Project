public class PinChatMessage extends ServerMessage {
    private String chatId; 

    public PinChatMessage(String chatId) {
        super(MessageTypes.PIN_CHAT, null);
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }
}
