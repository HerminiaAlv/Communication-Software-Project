
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

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

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
                System.out.println("Login message received: " + message.getMessage());
                handleLoginMessage((LoginMessage) message);
                break;
            case CHAT_MESSAGE:
            if (message instanceof ChatMessage) {
                ChatMessage chatMessage = (ChatMessage) message;
                System.out.println("Chat message received from user " + chatMessage.getSenderID() + ": " + chatMessage.getMessage());
                server.broadcastMessage(chatMessage);
            } else {
                System.out.println("Message is not a ChatMessage instance.");
            }
            break;
            default:
                System.out.println("Unhandled message type: " + message.getType());
        }
        
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