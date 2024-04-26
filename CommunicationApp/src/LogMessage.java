public class LogMessage extends ServerMessage {
    private String userId; 			
    private MessageStatus status; 	
    private String requestDetails; 	

    // Constructor for log message
    public LogMessage(String userId, String requestDetails) {
        this.setType(MessageTypes.GET_LOGS);
        this.setStatus(MessageStatus.PENDING);
        this.userId = userId;
        this.requestDetails = requestDetails;
    }

    // Getter and setter for the status of the log request
    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    // Getter for the user ID
    public String getUserId() {
        return userId;
    }

    // Getter for the request details
    public String getRequestDetails() {
        return requestDetails;
    }
}
