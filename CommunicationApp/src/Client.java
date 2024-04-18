package Hello;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password (any string): ");
            String password = scanner.nextLine();

            Credentials credentials = new Credentials(username, password);

            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(credentials);
            out.flush();  // Flush the output stream

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            String loginStatus = (String) in.readObject();

            if ("SUCCESS".equals(loginStatus)) {
                System.out.println("Login successful!");

                String fullName = (String) in.readObject();
                System.out.println(fullName);

                while (true) {
                    System.out.print("Enter recipient name: ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter message: ");
                    String content = scanner.nextLine();

                    Message message = new Message(username, recipient, content);
                    out.writeObject(message);
                    out.flush();  // Flush the output stream

                    if ("exit".equalsIgnoreCase(content)) {
                        break;
                    }
                }
            } else {
                System.out.println("Login failed. Invalid username or password.");
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
