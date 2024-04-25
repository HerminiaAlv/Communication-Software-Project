import java.util.Map;

public class UpdateUserMessage extends ServerMessage {
    private String userId;
    private Map<String, String> updates;

    public UpdateUserMessage(String userId, Map<String, String> updates) {
        super(MessageTypes.UPDATE_USER, null);
        this.userId = userId;
        this.updates = updates;
    }

    public String getUserId() {
        return userId;
    }

    public Map<String, String> getUpdates() {
        return updates;
    }
}
