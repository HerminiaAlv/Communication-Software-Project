import java.util.Map;

// For telling the clients when users have been added to the system
// they need to update their client.userlist
public class UpdateUserListMessage extends ServerMessage {
    private  Map<String, User> updatedUserList;

    UpdateUserListMessage(Map<String, User> updatedUserList){
        setType(MessageTypes.UPDATE_USER_LIST);
        setStatus(MessageStatus.PENDING);
        this.updatedUserList = updatedUserList;
    }

    public Map<String, User> getUpdatedUserList() {
        return updatedUserList;
    }

    public void setUpdatedUserList(Map<String, User> updatedUserList) {
        this.updatedUserList = updatedUserList;
    }
}
