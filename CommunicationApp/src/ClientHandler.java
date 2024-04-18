
package Hello;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;
    private String fullName;  // Added field to store the full name

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
        try {
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            this.in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Credentials credentials = (Credentials) in.readObject();

            if (server.isValidCredentials(credentials)) {
                out.writeObject("SUCCESS");

                User userInfo = server.getUserInfo(credentials.getUsername());
                this.fullName = userInfo.getName() + " " + userInfo.getLastName();  // Store the full name
                
                out.writeObject(fullName);  // Send user's full name to client

                this.username = credentials.getUsername();

                while (true) {
                    Message message = (Message) in.readObject();
                    if ("exit".equalsIgnoreCase(message.getContent())) {
                        break;
                    }
                    server.sendMessage(message);
                }
            } else {
                out.writeObject("FAIL");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
