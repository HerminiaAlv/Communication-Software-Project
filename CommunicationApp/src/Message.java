import java.util.*;

public class Message extends ServerMessage{
	protected MessageTypes type; 
	private String message;
	private String sender;
	private String chatID; //will handle multiple receivers, will contain chatroom ID
	private MessageStatus status;
	private String id;
	private boolean delivered;
	private Date date;
	
	protected User user;
	protected UserStatus userStatus;

	//constructor
	public Message(MessageTypes type, MessageStatus status, String Message) {
		this.type = MessageTypes.UNDEFINED;
		//this.message = message;
		this.status = MessageStatus.UNDEFINED;
		this.date = new Date();
	}
	
	//parametized constructor	
	public Message(String message, String sender, String chatid, Date date) {
		this.message = message;
		this.sender = sender;
		this.chatID = chatid;
		this.date = date;
	}

    //getters
	public String getMessage() {
		return message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public MessageStatus getStatus() {
		return status;
	}
	
	public String getChatID() {
		return chatID;
	}
	
	public String getID() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}
	public Date getDate() {
		return date;
	}

	private boolean isDelivered() {
		return delivered;
	}

	//setters
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	public void setChatID(String chatID){
		this.chatID = chatID;
	}

	public void setUserStatus(UserStatus userStatus){
  		this.userStatus = userStatus;
	}
	public void setUser(User user){
		this.user = user;
	}

	public void setDelivered(boolean delivered){
		this.delivered = delivered;
	}

	//other methods
}
