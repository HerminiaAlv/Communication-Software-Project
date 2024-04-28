import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private  Map<String, ObjectOutputStream> clients = new HashMap<>();

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                new Thread(new ClientHandler(clientSocket, this)).start();
            }
        }
        catch (IOException e) { 
            e.printStackTrace();
        }
    }

    public synchronized void addClient(String username, ObjectOutputStream out) {
        clients.put(username, out);
    }
    
    public synchronized void removeClient(String username) {
        clients.remove(username);
    }
    public synchronized void sendMessageToClient(String username, ServerMessage message) {
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

    // chat messages?
    public synchronized void broadcastMessage(ServerMessage message) {

        for (ObjectOutputStream out : clients.values()) {
            try  {
                List<ServerMessage> messages = new ArrayList<>(); 
                messages.add(message);
                out.writeObject(messages);
                System.out.println("broadcasted message to client.") ;
            } catch (IOException e) {
                System.out.println("Error broadcasting message to client: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public void handleLogin(LoginMessage msg, ObjectOutputStream outputStream) {	
    	if (msg.getStatus() == MessageStatus.PENDING) {
    		msg.setStatus(MessageStatus.SUCCESS);
    		msg.setSuccess(true);
            addClient(msg.getUsername(), outputStream);
    	}//

        // Send the response back to the client
        // sendMessageToClient(msg);
    }

    public void handleLogout(LogoutMessage message) {
        
    }

    public void handleChatMessage(ChatMessage message) {
        broadcastMessage(message);
    }

    public void handleUpdateUser(UpdateUserMessage message) {
        
    }

    public void handleCreateChat(CreateChatMessage message) {
        
    }
    
    public void handlePinChat(PinChatMessage message) {

    }

    public void handleNotifyUser(NotifyMessage message) {
        
    }
    
    public void handleAddUsersToChat(AddUsersToChatMessage message) {
    
    }
    
        

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
//test