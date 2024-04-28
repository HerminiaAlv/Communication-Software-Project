import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Server server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        try {
            this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("Error setting up stream: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {

            while (true) {
                Object messageObj = inputStream.readObject();
                if (messageObj instanceof ServerMessage) {
                    ServerMessage message = (ServerMessage) messageObj;
                    switch (message.getType()) {
                        case LOGIN:
                            Server.handleLogin((LoginMessage) message);
                            break;
                        case LOGOUT:
                        	Server.handleLogout((LogoutMessage) message);
                            break;
                        case CHAT_MESSAGE:
                        	Server.handleChatMessage((ChatMessage) message);
                            break;
                        case UPDATE_USER:
                        	Server.handleUpdateUser((UpdateUserMessage) message);
                            break;
                        case CREATE_CHAT:
                        	Server.handleCreateChat((CreateChatMessage) message);
                            break;
                        case ADD_USERS_TO_CHAT:
                        	Server.handleAddUsersToChat((AddUsersToChatMessage) message);
                            break;
                        case NOTIFY_USER:
                        	Server.handleNotifyUser((NotifyMessage) message);
                            break;
                        case PIN_CHAT:
                        	Server.handlePinChat((PinChatMessage) message);
                            break;
                        default:
                            System.out.println("Received undefined message type.");
                            break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading from client: " + e.getMessage());
        } finally {
            closeConnection();
            
         //   outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
         //   inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                List<ServerMessage> messages = (List<ServerMessage>) inputStream.readObject();
                for (ServerMessage message : messages) {
                    handleServerMessage(message);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleServerMessage(ServerMessage message) {
//        if (message.getType() == null) {
//            System.out.println("Received message with null type: " + message);
//            return;
//        }

        System.out.println("Received message type: " + message.getType());
        MessageTypes type = message.getType();

        switch (type) {
            case LOGIN:
                //System.out.println("Login message received: " + message.getMessage());
                handleLoginMessage((LoginMessage) message);
                break;
            case CHAT_MESSAGE:
            if (message instanceof ChatMessage) {
                ChatMessage chatMessage = (ChatMessage) message;
                System.out.println("Chat message received " + chatMessage.getMessage());
                server.broadcastMessage(chatMessage);
            } else {
                System.out.println("Message is not a ChatMessage instance.");
            }
            break;
            default:
                System.out.println("Unhandled message type: " + message.getType());
        }
        
    }

    private void closeConnection() {
    	try {
    		if (inputStream != null) inputStream.close();
    		if (outputStream != null) outputStream.close();
    		if (clientSocket != null) clientSocket.close();
    	} catch (IOException e) {
    		System.out.println("Error closing connection: " + e.getMessage());
    	}

    private void handleLoginMessage(LoginMessage msg) {
        // Verify the username and password
//        if ("amiller2".equals(msg.getUsername()) && "test".equals(msg.getPassword())) {
//            msg.setSuccess(true);
//            System.out.println("Login successful for: " + msg.getUsername());
//        } else {
//            msg.setSuccess(false);
//            System.out.println("Login failed for: " + msg.getUsername());
//        }
    	
    	if (msg.getStatus() == MessageStatus.PENDING) {
    		msg.setStatus(MessageStatus.SUCCESS);
    		msg.setSuccess(true);
            server.addClient(msg.getUsername(), outputStream);
    	}

        // Send the response back to the client
        sendMessageToClient(msg);
    }

    // For chat messages
    public void handleChatMessage(ChatMessage chatMessage) {

        // Send the message to all clients
        server.broadcastMessage((ServerMessage) chatMessage);
        sendMessageToClient(chatMessage);
    }

    private void sendMessageToClient(ServerMessage message) {
        try {
        	List<ServerMessage> toClient = new ArrayList<>();
        	toClient.add(message);
            outputStream.writeObject(toClient);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}