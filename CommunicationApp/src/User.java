import java.io.Serializable;

public class User implements Serializable{
	private String id;
	private String firstName;
	private String lastName;
	private String password;
	// attributes: status of user's status type? and the user's role
	private UserStatus status; // default offlien status
	private String username;
	private boolean is_IT; 

	//constructor
	public User(String id, String firstName, String lastName, 
				String username, String password, boolean is_IT){
					this.id = id;
					this.firstName = firstName;
					this.lastName = lastName;
					this.username = username;
					this.password = password;
					this.status = UserStatus.Offline; 
					this.is_IT = is_IT;
				}

	
	//getters
	public String getID() {
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getUsername(){
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public UserStatus getStatus() {
		return status;
	}

	public boolean isIT(){
			return is_IT;
	}

	public 

	//setters
	// username, status, firstname, lastname, isIT
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public void setUserName(String username){
		this.username = username;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	//other methods
	public boolean setIT(boolean is_IT){
		this.is_IT = is_IT;
	}

	public boolean isOnline(){
		return status == UserStatus.Online;
	}

	// addToChat, update user, addPinned

	
	
}
