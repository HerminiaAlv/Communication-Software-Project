import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private static Map<String, ObjectOutputStream> clients = new HashMap<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new Thread(new ClientHandler(clientSocket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void addClient(String username, ObjectOutputStream out) {
        clients.put(username, out);
    }

    public synchronized static void removeClient(String username) {
        clients.remove(username);
    }

    public synchronized static void sendMessageToClient(String username, ServerMessage message) {
    	ObjectOutputStream out = clients.get(username);
        if (out != null) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized static void broadcastMessage(ServerMessage message) {
        for (ObjectOutputStream out : clients.values()) {
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handleLogin(LoginMessage message) {
        if (validateCredentials(message.getUsername(), message.getPassword())) {
            message.setSuccess(true);
            message.setCurrentUser(new User()); 
            addClient(message.getUsername(), clients.get(message.getUsername()));
        } else {
            message.setSuccess(false);
        }
        sendMessageToClient(message.getUsername(), message);
    }

    public static void handleLogout(LogoutMessage message) {
        if (message.getStatus() == MessageStatus.PENDING) {
            removeClient(message.getUsername());
            message.setStatus(MessageStatus.SUCCESS);
            sendMessageToClient(message.getUsername(), message);
            System.out.println("User logged out and connection closed: " + message.getUsername());
        }
    }


    public static void handleChatMessage(ChatMessage message) {
        broadcastMessage(message);  
    }

    public static void handleUpdateUser(UpdateUserMessage message) {
        message.setStatus(MessageStatus.SUCCESS);
        sendMessageToClient(message.getUserId(), message);
    }

    public static void handleCreateChat(CreateChatMessage message) {
        // Get participant IDs from the message
        List<String> participantIds = message.getParticipantIds();
        
        // Validate participant list
        if (participantIds == null || participantIds.isEmpty()) {
            message.setStatus(MessageStatus.FAILED);
//            sendMessageToClient(message); 		// need to fix sendMessageToClient
            return;
        }

        ChatRoom newChat = new ChatRoom(participantIds);
        
        message.setCreatedChat(newChat);
        message.setStatus(MessageStatus.SUCCESS);

    }

    public static void handleAddUsersToChat(AddUsersToChatMessage message) {
        message.setStatus(MessageStatus.SUCCESS);
        sendMessageToClient(message.getUsername(), message);
    }

    public static void handleNotifyUser(NotifyMessage message) {
        sendMessageToClient(message.getUsername(), message);
    }

    public static void handlePinChat(PinChatMessage message) {
        message.setStatus(MessageStatus.SUCCESS);
        sendMessageToClient(message.getUsername(), message);
    }

    private static boolean validateCredentials(String username, String password) {
        return "expectedUsername".equals(username) && "expectedPassword".equals(password);
    }

    public static void main(String[] args) {
        new Server().start();
    }
}