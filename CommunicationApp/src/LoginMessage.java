// Do we need anything else when sending a login request to the server?
// This message should get processed by the server and success should be set
// to `true` if the login in successful
// else it stays false

// BIG question: What does the server need to send to the client
// when a login is successful? What data does the client need immediately
// Their chats, maybe a list of active users?
// The server can create a separate message to send this data
// or we can add all of that data to this class

public class LoginMessage {
    private String password;
    private String username;
    private boolean success;

    private MessageTypes type;

    public LoginMessage(String pass, String user, MessageTypes type){
        this.password = pass;
        this.username = user;
        this.type = type;
        this.success = false;
    }

    public String getPassword(){
        return password;
    }

    public String getUsername(){
        return username;
    }

    public MessageTypes getType(){
        return type;
    }

    public boolean isSuccessful(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }
}