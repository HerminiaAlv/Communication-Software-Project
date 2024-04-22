
package Hello;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    private Map<String, ClientHandler> clientHandlers = new HashMap<>();
    private List<User> users = new ArrayList<>();

    public Server() {
        // Read usernames from credentialsData.txt
        try (BufferedReader reader = new BufferedReader(new FileReader("credentialsData.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4) {
                    String lastName = parts[0].trim();
                    String name = parts[1].trim();
                    String username = parts[2].trim();
                    String userType = parts[3].trim();
                    users.add(new User(lastName, name, username, userType));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidCredentials(Credentials credentials) {
        for (User user : users) {
            if (user.getUsername().equals(credentials.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public User getUserInfo(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clientHandlers.put(clientHandler.getUsername(), clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    public void sendMessage(Message message) {
        String recipientUsername = message.getRecipient();
        ClientHandler clientHandler = null;

        // Find the User using the username
        User recipientUser = null;
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(recipientUsername)) {
                recipientUser = user;
                break;
            }
        }

        if (recipientUser != null) {
            clientHandler = clientHandlers.get(recipientUser.getUsername());
        }

        if (clientHandler != null) {
            clientHandler.sendMessage(message);
            System.out.println("Recipient found: " + recipientUser.getName() + " " + recipientUser.getLastName());
        } else {
            System.out.println("Recipient not found: " + recipientUsername);
        }

        String chatRoomId = generateChatRoomId(message.getSender(), message.getRecipient());
        FileManager.writeMessageToFile(chatRoomId, message);
    }



    



    public String generateChatRoomId(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(12345);
    }
    
}
