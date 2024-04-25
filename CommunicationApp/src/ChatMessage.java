public class ChatMessage extends ServerMessage {
    private String sender;
    private String messageContent;

    public ChatMessage(String sender, String messageContent) {
        super(MessageTypes.CHAT_MESSAGE, MessageStatus.PENDING);   // COMEBACK TO CHECK WHAT STATUS
        this.sender = sender;
        this.messageContent = messageContent;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageContent() {
        return messageContent;
    }
}
