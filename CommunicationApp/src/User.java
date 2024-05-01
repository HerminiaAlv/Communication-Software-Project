import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable{
	private static int count = 0;
	// private int id; // username is the ID
	private String firstName;
	private String lastName;
	private String password; // do we need this?
	// attributes: status of user's status type? and the user's role
	private List<ChatRoom> chats;

	private UserStatus status; // default offline status
	private String username;
	private boolean is_IT; 

	//constructors
	public User() { // default constructor for testing purposes
		this.chats = new ArrayList<>();
		this.firstName = "Firstname";
		this.lastName = "Lastname";
		this.password = "Password";
		this.username = "Username";
	}

	public User(String firstname, String lastname, String username) {
		this.firstName = firstname;
		this.lastName = lastname;
		this.username = username;
		this.chats = new ArrayList<>();
	}
	
	public User(String id, String firstName, String lastName, 
				String username, String password, boolean is_IT, List<ChatRoom> chats){
					//this.id = ++count;
					this.firstName = firstName;
					this.lastName = lastName;
					this.username = username;
					this.password = password;
					this.status = UserStatus.Offline; 
					this.is_IT = is_IT;
					this.chats = chats;
	}

	
	//getters
	// public int getID() {
	// 	return id;
	// }

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

	public List<ChatRoom> getChats() {
		return chats;
	}
	
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
    public void setPassword(String password) {
        this.password = password;
    }
    
	public void setIT(boolean is_IT){
		this.is_IT = is_IT;
	}

	public boolean isOnline(){
		return status == UserStatus.Online;
	}

	public boolean addChat(ChatRoom chatToAdd) {
		// if (!chats.contains(chatToAdd.getChatID())) // Chat doesn't exist in User's list of chats
		// add it and return true 
		// else 
		if(!chats.contains(chatToAdd.getChatID())) {
			chats.add(chatToAdd);
			return true;
		}
		
		return false;
	}

    public String getActiveChat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getActiveChat'");
    }

	public String toString () {
		return firstName + " " + lastName;
	}

	
	// addToChat, update user, addPinned

	
	
}