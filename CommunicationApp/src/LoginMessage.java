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

    public boolean isSuccessful(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }
}