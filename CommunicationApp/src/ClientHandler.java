import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

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
        }
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

    private void closeConnection() {
    	try {
    		if (inputStream != null) inputStream.close();
    		if (outputStream != null) outputStream.close();
    		if (clientSocket != null) clientSocket.close();
    	} catch (IOException e) {
    		System.out.println("Error closing connection: " + e.getMessage());
    	}
    }
}