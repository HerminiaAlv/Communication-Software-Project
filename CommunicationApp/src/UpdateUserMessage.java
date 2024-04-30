import java.util.Map;

public class UpdateUserMessage extends ServerMessage {
    private String userId;
    private String password;
    private String firstname;
    private String lastname;
    private boolean is_IT;
    private Map<String, String> updates;

    public UpdateUserMessage(String userId, String password, String firstname, String lastname, boolean is_IT) {
        this.setType(MessageTypes.UPDATE_USER);
        this.setStatus(MessageStatus.PENDING);
        this.userId = userId;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.is_IT = is_IT;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }

    public boolean getIs_IT() {
    	return is_IT;
    }
    
    public void setUserId(String userId) {
    	this.userId = userId;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public void setFirstname(String firstname) {
    	this.firstname = firstname;
    }
    
    public void setLastname(String lastname) {
    	this.lastname = lastname;
    }
    
    public void setIs_IT(boolean Is_IT) {
    	this.is_IT = Is_IT;
    }
}
