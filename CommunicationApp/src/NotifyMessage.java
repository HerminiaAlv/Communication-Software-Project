public class NotifyMessage extends ServerMessage {
    private String notificationText; 

    public NotifyMessage(String notificationText) {
        super(MessageTypes.NOTIFY_USER, null);
        this.notificationText = notificationText;
    }

    public String getNotificationText() {
        return notificationText;
    }
}
