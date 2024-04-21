import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client {
    // CHANGE THESE BASED ON WHO IS RUNNING THE SERVER
    private String HOSTIP = "localhost"; //hostname/IP to connect to
    private int PORT = 1234; // Port number to connect to on hostname

    private ObjectOutputStream output;
	private ObjectInputStream input;
    private Socket socket;  // flag to determine if login has been authenticated

    // LoginGUI attributes
    private LoginUI loginGUI;

    // MainGUI attributes
    private User currentUser;
    private ClientGUI mainGUI;
    private Map<String, String> userlist; // Username, Display name?
    private boolean loggedIn;    // flag to show user has successfully logged in

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
    public void start() {
        try {
            socket = new Socket(HOSTIP, PORT);

            if (!socket.isConnected()) {
                // Error on Connection
                // Display error Message
                //JOptionPane errorPane = new JOptionPane();
                JOptionPane.showMessageDialog(null, 
                "Server connection issue. Check IP.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
                // Close Program
                System.exit(0);
            }
            
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());

            //Spawn our listener thread that will wait for incoming responses from the server
            new Thread(() -> listenForMessages()).start();

            // Start Login UI
            loggedIn = false; //
            invokeLoginUI();

            while(!loggedIn) { // Wait until we get a valid login
                // waiting for login message to return
                Thread.sleep(1000);
                System.out.println("waiting to be verified");
            }
            
            // After Successful login 
            System.out.println("CONNECTED");
            // New thread on Event dispatch thread
            invokeMainGUI();
            
            while (loggedIn) {
                // wait for logout
            }

            //socket.close();
        } catch (Exception e) {
            e.toString();
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void listenForMessages() {
        while (true){
        try {
            // Accept a Message
            List<ServerMessage> fromServer = (List<ServerMessage>) input.readObject();
            for (ServerMessage m : fromServer) { // Route each message that came in from the server
                // Once you know what type of message, cast it to that Message
                MessageTypes type = m.getType();
                
                // Identify what to do with the message and spawn thread to handle
                switch (type) {
                    case LOGIN:
                        new Thread(() -> handleLoginMessage((LoginMessage) m)).start(); // Cast to Login Message
                        break;
                    case LOGOUT:
                        // LOGOUT()
                        break;
                    case CHAT_MESSAGE:
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
    // Message Handling Methods -----------------------

    public void handleLoginMessage(LoginMessage msg) {
        // The user has sent a request for login
        // we need to know if it was successful
        loggedIn = false;

        if (msg.isSuccessful() && socket.isConnected()){
           // init client attributes given by server in the message
           loggedIn = true; // set loggedIn flag - breaks loop and initializes main client 
           currentUser = msg.getUser();
           //userlist = msg.getUserlist(); // A list of all users in the system
           // This should be a Map<String, String> username, displayName
           
           // Dispose of the LoginGUI because we're authenticated now
            killLoginGUI();
        } else { /*  Stay in login loop */
            loginGUI.updateWaitingStatus(loggedIn);
        }
    }


    // Message Sending Methods -------------------

    // Anytime you need to send a method to the server
    // call this method and pass in anytype of MessageTypes message
    // see LoginUI's login() method for an example. 
    public void sendMessageToServer(ServerMessage m) {
        // Testing without authentication
        // testLoginFromUI(m);

        List<ServerMessage> toServer = new ArrayList<>();
        toServer.add(m);
        
        try {
            output.writeObject(toServer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pretty sure that's all we need to do
    }

    // Other Methods
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

    // Testing Methods
    private void testLoginFromUI(ServerMessage m){
                //test waiting
                LoginMessage msg = (LoginMessage) m;
                // try {
                //     System.out.println("User: " + msg.getUsername()
                //     + "\nPassword: " + msg.getPassword()
                //     + "\nType: " + msg.getType()
                //     + "\nStatus: " + msg.getStatus());
                //     Thread.sleep(5000);
                //     loginGUI.updateWaitingStatus(loggedIn);
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
        
                // test successful
                loginGUI.updateWaitingStatus(true);
                loggedIn = true; // 
                currentUser = msg.getUser();
        
                // A list of all users in the system
                // This should be a Map<String, String> username, displayName
                //userlist = msg.getUserlist();
                 killLoginGUI();
    } 
}
