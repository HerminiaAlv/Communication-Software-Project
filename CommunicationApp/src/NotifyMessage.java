public class NotifyMessage extends ServerMessage {
    private String notificationText; 

    public NotifyMessage(String notificationText) {
        this.setType(MessageTypes.NOTIFY_USER);
        this.setStatus(MessageStatus.PENDING);
        this.notificationText = notificationText;
    }

    public String getNotificationText() {
        return notificationText;
    }
}
