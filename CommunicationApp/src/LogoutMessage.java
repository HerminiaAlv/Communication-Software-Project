public class LogoutMessage extends ServerMessage {
    private MessageTypes type;
    private String username;

    public LogoutMessage(String username){
    	this.type = MessageTypes.LOGOUT;
    	this.setUsername(username);
    }
	
    public MessageTypes getType(){
        return type;
    }
    
    public String getUsername() {
	return username;
    }
	
    public void setUsername(String username) {
	this.username = username;
    }
}
