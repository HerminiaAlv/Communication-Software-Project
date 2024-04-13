import java.io.Serializable;
import java.util.*;

public class Message implements Serializable{
	protected MessageTypes type; 
	private String message;
	private String sender;
	private String chatID; //what about multiple recipients?
	private MessageStatus status;
	private String id;
	private boolean delivered;
	private Date date;
	
	protected User user;
	protected UserStatus userStatus;


	
	//constructor
	public Message(String message) {
		this.type = MessageTypes.UNDEFINED;
		this.message = message;
		this.status = MessageStatus.UNDEFINED;
	}
	
	//parametized constructor	
	public Message(String message, String sender, String recipient, MessageStatus status) {
		this.message = message;
		this.sender = sender;
		this.chatID = recipient;
		this.status = status;
	}
	
	
	//getters
	public String getMessage() {
		return message;
	}

	public String getUser(){
		return user;
	}

	public UserStatus getUserStatus(){
		return userStatus;
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
	
	public String getID(){
		return id;
	}

	public Date getDate(){
		return date;
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

	public void setUser(String user){
		
	}

	public void setUserStatus(UserStatus userStatus){

	}

	public MessageTypes getType() {
		return type;
	}

	public void setType(MessageTypes type) {
		this.type = type;
	}

	public void setChatID(String chatID) {
		this.chatID = chatID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MessageTypes getType() {
		return type;
	}

	public void setType(MessageTypes type) {
		this.type = type;
	}

	public void setChatID(String chatID) {
		this.chatID = chatID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setUser(User user) {
		this.user = user;
	}

	//other methods

}
