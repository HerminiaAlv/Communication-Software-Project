
import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client {
    private String HOSTIP = "localhost"; // hostName/IP to connection
    private int PORT = 12345; // Port number to connect to on hostName

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private Socket socket;  // flag to determine if login has been authenticated

    // LoginGUI attributes
    private LoginUI loginGUI;
   
    // MainGUI attributes
    private User currentUser;
    private ClientGUI mainGUI;
    private Map<String, String> userlist; // Username, Display name?
    private boolean loggedIn;

    //Message
    private MessagePanel mssgPanel;
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {
        try {
            socket = new Socket(HOSTIP, PORT);
            if (!socket.isConnected()) {
                JOptionPane.showMessageDialog(null, "Server connection issue. Check IP.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
           // new Thread(new IncomingReader()).start();
            //Thread chatMessageListenerThread = new Thread(new IncomingReader());
            //chatMessageListenerThread.start();
            new Thread(() -> listenForMessages()).start();
            loggedIn = false;
            invokeLoginUI();

            while (!loggedIn) { // wait until we get a valid login
            	// waiting for login message to return
                Thread.sleep(1000);
                System.out.println("waiting to be verified");
            }
            System.out.println("CONNECTED");
            invokeMainGUI();
            //new Thread(() -> listenForMessages()).start();
            while (loggedIn) {
            	// wait for logout
            }
            
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void listenForMessages() {
        while (true) {
            try {
            	// Accept a Message
                List<ServerMessage> fromServer = (List<ServerMessage>) input.readObject();
                for (ServerMessage m : fromServer) { // Route each message that came in from the server
                    //debugging
                    System.out.println("Debug: Message received from server: " + m);
                	switch (m.getType()) {
                        case LOGIN:
                            System.out.println("Debug: LoginMessage received: " + m);
                            new Thread(() -> handleLoginMessage((LoginMessage) m)).start();// Cast to Login Message
                            break;

                        case LOGOUT:
                           // new Thread(() -> handleLogoutMessage((LogoutMessage) m)).start();
                            break;
                        case CHAT_MESSAGE:
                            System.out.println("Debug: CHAT_MESSAGE received: " + m.getMessage());
                            new Thread(()->handleChatMessage((ChatMessage) m)).start();
                            break;
                        case GET_LOGS:
                            break;
                        case UPDATE_USER:
                            break;
                        case CREATE_CHAT:
                            break;
                        case ADD_USERS_TO_CHAT:
                            break;
                        case NOTIFY_USER:
                            break;
                        case PIN_CHAT:
                            // save this for later if we have time
                            break; 
                        default:
                            System.out.println("Reached an undefined state");
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

 // Message Handling Methods ----------------------------------------------
    public void handleLoginMessage(LoginMessage msg) {
    	// The user has sent a request for login
        // we need to know if it was successful
        loggedIn = msg.isSuccessful() && socket.isConnected();

        if (loggedIn) {
            currentUser = msg.getUser();
            killLoginGUI();
        } else {  /*  Stay in login loop */
            loginGUI.updateWaitingStatus(loggedIn);
        }            
    }
    

    public void sendMessageToServer(ServerMessage m) { //This method works, it does recognize LoginMessage but nto ChatMessage
        try {
            System.out.println("Message Type to send: " + m.getType());
        	List<ServerMessage> toServer = new ArrayList<>();
        	toServer.add(m);
        	
            output.writeObject(toServer);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLogoutMessage(LogoutMessage msg) {

    }

    public void handleChatMessage(ChatMessage chatMessage) {
        // Log the received chat message for debugging
        System.out.println("Chat message received" + chatMessage.getMessage());
        // Broadcast the message to other clients (excluding the sender)
        SwingUtilities.invokeLater(() -> {
                mainGUI.updateMessagePanel(chatMessage.getMessage());
        });
    }
    
    // Other Methods ****************************************************************
    private Client getThisClient() {
        return this;
    }

    private void killLoginGUI() {
        EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					loginGUI.setVisible(false); // turn off login gui?
                    loginGUI = null;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
    }

    private void invokeLoginUI() {
        EventQueue.invokeLater(new Runnable() { 
            public void run() {
                try {
                    loginGUI = new LoginUI(getThisClient());
                    loginGUI.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            });
    }

    private void invokeMainGUI() {
        EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
					mainGUI = new ClientGUI(getThisClient(), currentUser);
					mainGUI.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
    }
    // listen for incoming mssgs
/*     public void setupConnection() {
		try {
			socket = new Socket(HOSTIP, PORT);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
			new Thread(new IncomingReader()).start(); 
	
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} */
/*     private void startListening() {
        new Thread(new IncomingReader()).start();
    } */

/*     private class IncomingReader implements Runnable {
        public void run() {
            try {
                while (true) {
                    Object obj = input.readObject();
                    if (obj instanceof List<?>) { // Assuming messages are sent as a List
                        List<?> messages = (List<?>) obj;
                        for (Object messageObj : messages) {
                            if (messageObj instanceof ChatMessage) {
                                ChatMessage message = (ChatMessage) messageObj;
                                System.out.println("Chat message received: " + message.getMessage());
                                SwingUtilities.invokeLater(() -> {
                                    mainGUI.updateMessagePanel(message.getMessage());
                                });
                            }
                            // Handle other types of messages
                            else if (messageObj instanceof LoginMessage) {
                                // Process login message
                            }
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Failed to read from server: " + e.getMessage());
                e.printStackTrace();
            }
        }
    } */
    
    // Testing Methods
    private void testLoginFromUI(ServerMessage m){
                //test waiting
                LoginMessage msg = (LoginMessage) m;
                // test successful
                loginGUI.updateWaitingStatus(true);
                loggedIn = true; // 
                currentUser = msg.getUser();

                 killLoginGUI();
    } 
}