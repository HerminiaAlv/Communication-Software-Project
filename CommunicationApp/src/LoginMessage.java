import java.util.HashMap;
import java.util.Map;

public class LoginMessage extends ServerMessage{
    private String password;
    private String username;
    private boolean success;

    private User currentUser; // Server passes a user over when login is successful
    private Map<String, User> allUsers; // Server should pass a list or map of ALL users 
    // a map of userIDs and names is probabably sufficient becauses we don't need all the data

    //private MessageTypes type;

    public LoginMessage(String password, String username) {
        this.password = password;
        this.username = username;
        setType(MessageTypes.LOGIN);
        setStatus(MessageStatus.PENDING);
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public User getUser() {
        return currentUser;
    }

    public Map<String, User> getAllUsers() {
        return allUsers;
    }

    public boolean isSuccessful(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    
    public void setAllUsers(Map<String, User> allUsers){
        this.allUsers = allUsers; 
    }  
}
