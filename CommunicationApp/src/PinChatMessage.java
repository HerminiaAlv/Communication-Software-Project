public class PinChatMessage extends ServerMessage {
    private String chatId; 

    public PinChatMessage(String chatId) {
        this.setType(MessageTypes.PIN_CHAT);
        this.setStatus(MessageStatus.PENDING);
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }
}
