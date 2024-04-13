import java.io.Serializable;

public class User implements Serializable{
	private String id;
	private String password;
	// attributes: status of user's status type? and the user's role
	private UserStatus status;
	
	//getters
	public String getID() {
		return id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public UserStatus getStatus() {
		return status;
	}
	
	//setters
	// username, status, firstname, lastname, isIT
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
}
