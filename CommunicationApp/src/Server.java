import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Server {
    private static final int PORT = 4000;
    private Map<String, ObjectOutputStream> clients = new HashMap<>();
    private Map<String, String[]> credentials = new HashMap<>();
    private Map<String, User> allUsers = new HashMap<>();
    
    // Return the all UserMap for Login Message
    public Map<String, User> populateAllUsers() {
        Map<String, User> allUsersMap = new HashMap<>();
        // username, first + last
        for (String username : credentials.keySet()){
            String[] userValues = credentials.get(username);
            //build first+last // last name, first name, password, is_it
            User toAdd = new User(userValues[1], userValues[0], username);
            allUsersMap.put(username, toAdd);
        }
        return allUsersMap;
    }
    public void start() {

        populateCredentials();
        allUsers = populateAllUsers();
        // Open the server to accept connections

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

    public boolean writeChatRoomFile(ChatRoom toWrite) {
        //"\\401-TestBuildFromFile\\src\\chatrooms"
        String filename = "chatrooms\\" + toWrite.getChatID();
        boolean createdNewFile = false;
        File file = new File(filename+".txt");
        // Open up the file with the same username
    	if (!file.exists()) {
            System.out.println("File not found: " + filename + ".txt Creating a new file");
            createdNewFile = true;
        }

        try (PrintWriter writer = new PrintWriter(file)){
        // Writer the file header
            // CHATID
            writer.println(toWrite.getChatID());

            // Participant list
            String participants = "";
            for (String p : toWrite.getParticipants())
                participants = participants + p + ",";
            writer.println(participants);

            // Messages
            for (Message m : toWrite.getMessages()) {
                writer.println(m.toCSVString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return createdNewFile;
    }

    public boolean writeUserChatList(User user) {
        String filename = user.getUsername();
        boolean createdNewFile = false;

        File file = new File(filename+".txt");
        // Open up the file with the same username
    	if (!file.exists()) {
            System.out.println("File not found: " + user.getUsername() + ".txt Creating a new file");
            createdNewFile = true;
        }
            System.out.println("File found. Overwriting file");
        // create a loop that writes the all of the chatIDs to a file
        try (PrintWriter writer = new PrintWriter(file)) {
            for (ChatRoom chat : user.getChats()) {
                writer.println(chat.getChatID());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createdNewFile; // New file wasnt created
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
    // Assume that this method will receive a list of usernames.
    public synchronized void broadcastMessage(ChatMessage chatMessage) {
        // LOGGING -- Chris will do this
        // When a message comes in we need to append it to the chatID
        Message toAppend = chatMessage.getMessage();
        appendMessageToChat(chatMessage.getMessage().getChatID(), toAppend);

        chatMessage.setStatus(MessageStatus.SUCCESS);
        //System.out.println("Inside broadcastMsg");

        // for each user in the chat room, send the message to the user
        List<String> participants = chatMessage.getParticipants();

        for (String participant : participants) {
            ObjectOutputStream out = clients.get(participant);
            if (out != null) {
                try {
                    List<ServerMessage> messages = new ArrayList<>();
                    messages.add(chatMessage);
                    out.writeObject(messages);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("User: " + participant + " was not online. Saving for later");
            }
        }
    }
    
    
   private void appendMessageToChat(String chatID, Message toAppend) {
        String filename = "chatrooms\\" + chatID + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            //bw.newLine();
            bw.write(toAppend.toCSVString());
            bw.newLine();
            System.out.println(toAppend.toCSVString() + " was added to " + chatID);
        } catch (IOException e) {
            System.err.println("Error writing to chatfile file: " + e.getMessage());
        }
    }
 public void handleLogin(LoginMessage msg, ObjectOutputStream outputStream) {	  	
        
        if (msg.getStatus() == MessageStatus.PENDING) {
        	if (validateCredentials(msg.getUsername(), msg.getPassword()))
        	{
        		msg.setStatus(MessageStatus.SUCCESS);
        		msg.setSuccess(true);
                msg.setAllUsers(allUsers);
                msg.setCurrentUser(buildUser(msg.getUsername())); // build this user
                addClient(msg.getUsername(), outputStream);
        	} else 
        	{
        		msg.setStatus(MessageStatus.FAILED);
        	}
    		
    	}
       
        // Send the response back to the client
        // sendMessageToClient(msg);
    	
        /*
    	  if (validateCredentials(message.getUsername(), message.getPassword())) {
              message.setSuccess(true);
              message.setCurrentUser(new User());  // where I need build user
              addClient(message.getUsername(), clients.get(message.getUsername()));
          } else {
              message.setSuccess(false);
          }
          sendMessageToClient(message.getUsername(), message);

          */
    }

    public boolean validateCredentials(String username, String password) {
        if (credentials.containsKey(username)) {
            String[] userDetails = credentials.get(username);
            return userDetails[2].equals(password); // Password is at index 2
        }
        return false;
    }

    public void handleLogout(LogoutMessage message) {
    	if (message.getStatus() == MessageStatus.PENDING) {
            removeClient(message.getUsername());
            message.setStatus(MessageStatus.SUCCESS);
            sendMessageToClient(message.getUsername(), message);
            System.out.println("User logged out and connection closed: " + message.getUsername());
        }
        
    }

    public void handleChatMessage(ChatMessage message) {
        broadcastMessage(message);
    }

    public void handleGetLogs(LogMessage message) {
        // with the userID need to go to the file and build all the chats and return them in  a list
        if (message.getStatus() == MessageStatus.PENDING) {
            String username = message.getUserId();
            User user = buildUser(username);
            
            message.setUserChats(user.getChats());
            message.setStatus(MessageStatus.SUCCESS);
        }
        else message.setStatus(MessageStatus.FAILED);

        // fall through send back
    }

    public void handleUpdateUser(UpdateUserMessage message) {
    	if (message.getStatus() == MessageStatus.PENDING) {
            if (!credentials.containsKey(message.getUserId())){ // see if we already have this value in credentials
                // Add To Credentials Map
                // last name, first name, password, is_it
                String booleanVal = "0";
                if (message.getIs_IT())
                    booleanVal = "1";
                String[] vals = {message.getLastname(), message.getFirstname(), message.getPassword(), booleanVal};
                credentials.put(message.getUserId(), vals);
                // write to credentialsData.txt
                writeCredentials(message.getLastname(), message.getFirstname(), message.getUserId(), message.getPassword(), booleanVal.compareTo("1") == 0);
                // Create a new blank userfile
                createNewUserFile(message.getUserId());

                // Update users with the new request
                allUsers.put(message.getUserId(), new User(message.getFirstname(), message.getLastname(), message.getUserId()));

                // Broadcast new User Map
                new Thread(()->{updateUsers();}).start();

                message.setStatus(MessageStatus.SUCCESS);
                sendMessageToClient(message.getUserId(), message);
                System.out.println("User logged out and connection closed: " + message.getUserId());
            }
            else {
                message.setStatus(MessageStatus.FAILED);
            }
        }
        else {
            message.setStatus(MessageStatus.FAILED);
            //send back
        }
    }
    public void updateUsers() {
        UpdateUserListMessage msg = new UpdateUserListMessage(allUsers);
        for (ObjectOutputStream oos : clients.values()) {
            try {
                List<ServerMessage> toClient = new ArrayList<>();
                toClient.add(msg);
                oos.writeObject(toClient);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleCreateChat(CreateChatMessage message) {
    	// Get participant IDs from the message
        List<String> participantIds = message.getParticipantIds();
        
        // Validate participant list
        if (participantIds == null || participantIds.isEmpty()) { // not valid
            message.setStatus(MessageStatus.FAILED);
            //sendMessageToClient(message.getUsername(), message); 		// need to fix sendMessageToClient
            return;
        }

        ChatRoom newChat = new ChatRoom(participantIds);
        for (String username : participantIds)
            appendChatToUserFile(username, newChat.getChatID()); // Add the chat to each participant
        // create the file on the server as well
        writeChatRoomFile(newChat);

        message.setCreatedChat(newChat);
        message.setStatus(MessageStatus.SUCCESS);

        // let fall through and send it back in clienthandler
    }
    
    public void handlePinChat(PinChatMessage message) {
        // Low Prio
    }

    public void handleNotifyUser(NotifyMessage message) {
        // Low Prio
    }
    
    public void handleAddUsersToChat(AddUsersToChatMessage message) {
        // TODO add this
    }

    public void populateCredentials()
    {
        try (BufferedReader br = new BufferedReader(new FileReader("credentialsData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cred = line.split(",");
                // do wanna add is_IT to this file?
                // Assuming the file format is: username,firstname,lastname,password
                if (cred.length >= 4) {
                    String username = cred[2]; // username is the third item
                    String[] userDetails = {cred[0], cred[1], cred[3], cred[4]}; // last name, first name, password, is_it
                    credentials.put(username, userDetails);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading credentials file: " + e.getMessage());
        }
    }
    // assuming that new user data is added
    public void writeCredentials(String lastName, String firstName, String username, String password, boolean is_IT) {
        String isIT;
        if (is_IT) {
            isIT = "1"; //true
        } else {
            isIT = "0"; //false
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("credentialsData.txt", true))) {
            //bw.newLine();
            bw.write(lastName + "," + firstName + "," + username + "," + password + "," + isIT);
            bw.newLine();
            System.out.println("User added to credentials file: " + username);
        } catch (IOException e) {
            System.err.println("Error writing to credentials file: " + e.getMessage());
        }
    }
    public User buildUser(String username) {
        // Retrieve user details from credentials map
        String[] userDetails = credentials.get(username);
        if (userDetails == null) {
            return null; // If user does not exist in credentials
        }

        //  chat rooms associated with this user
        List<String> chatIDsToBuild = new ArrayList<>();
        File file = new File("users\\" + username+".txt"); // username is the file path for userchat room IDs
        
        // Open up the file with the same username
    	if (!file.exists()) {
            System.err.println("File not found: " + username + ".txt");
            return null;
        }
        Scanner reader;
        try {
            reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine(); // each line a chatID we need to go build
                chatIDsToBuild.add(line); 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // With the list we created from userfile - go find the chats
        List<ChatRoom> userChats = new ArrayList<>();
        for (String chatFilePath : chatIDsToBuild) {
            ChatRoom chatToAdd = buildChatRoom(chatFilePath);
            if (chatToAdd != null) // buildChatRoom success
                userChats.add(chatToAdd);
            else // buildChatRoom returned null
                System.out.println(chatFilePath + "was not found and did not build correctly.");
        }
        boolean is_it = false;
        if (userDetails[3].compareTo("1") == 0) {
            is_it = true;
        }
        // Creating a new User object with detailed constructor
        User user = new User(username, userDetails[1], userDetails[0], 
                            username, userDetails[2], is_it, userChats); 
        return user;
    }

    
    
    private List<ChatRoom> getUserChats(String username) {
        // Dummy data for chat rooms
        List<ChatRoom> chats = new ArrayList<>();
        List<String> participants = Arrays.asList("user1", "user2", username); // Dummy participants, including the user

        // Dummy messages, utilizing the Message constructor correctly
        Message message1 = new Message("Hello from user1!", "user1", "chat123");
        Message message2 = new Message("Hello from user2!", "user2", "chat456");

        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);

        // Creating chat rooms with these messages
        ChatRoom chat1 = new ChatRoom(participants, messages, UUID.randomUUID().toString(), "chat1file.txt");
        ChatRoom chat2 = new ChatRoom(participants, messages, UUID.randomUUID().toString(), "chat2file.txt");

        chats.add(chat1);
        chats.add(chat2);

        return chats; // Returns the list of chat rooms for the user
    }  
    
    public ChatRoom buildChatRoom(String filePath) // (String populate )this "populate" should be able to populate the file
    {
    	File file = new File("chatrooms\\"+filePath + ".txt");
    	if (!file.exists()) {
            System.err.println("File not found: " + filePath);
            return null;
        }

        String path = "chatrooms\\"+ filePath + ".txt";
        Scanner reader;
		try {
			reader = new Scanner(new File(path), StandardCharsets.UTF_8);
		
        String[] tokens;
        String line;

        // Line 1 is chatID
        // Line 2 is participants
        // Line 3 and bellow are messages
        
        String chatID;
        List<String> participants = new ArrayList<>();

        // Get ID
        line = reader.nextLine();
        tokens = line.split(",");
        // tokens[0] is the id
        chatID = tokens[0];

        // Get Participants
        line = reader.nextLine();
        tokens = line.split(",");

        for (String s : tokens)
            participants.add(s);

        // Get Chat Messages
        List<Message> messages = new ArrayList<>();
        // Loop until the end of the file to build messages
        Pattern pattern = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"); // Regex to split on commas outside of quotes
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            if (line.compareTo("") == 0)
                break;

            tokens = pattern.split(line);
            // tokens[0] = text
            // tokens[1] = sender
            // tokens[2] = chatid
            // tokens[3] = date
            for (int i = 0; i < tokens.length; i++) {
                tokens[i] = tokens[i].replaceFirst("^\"|\"$", "").replace("\"\"", "\"");
            }

            String[] dateTokens = tokens[3].split("\\."); // 

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateTokens[0], formatter);
            //Date date = (Date) dateFormat.parse(tokens[3]);

            Message newMsg = new Message(tokens[0], tokens[1],tokens[2],dateTime);
            messages.add(newMsg);
        }
        reader.close();
        // Build ChatRoom Object
        
        ChatRoom newChatRoom = new ChatRoom(participants, messages, chatID, filePath);
        return newChatRoom;
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;	// Error, couldn't build
    }
    public void appendChatToUserFile(String username, String chatID) {
        // check if the file exist
        // it should always exist since when we add a brand newuser the filename is created
        String directoryPath = "users";
        String filename = directoryPath + File.separator + username + ".txt";

        // Ensure the directory exists
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();  // Make the directory (including any necessary but nonexistent parent directories)
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            //bw.newLine();
            bw.write(chatID);
            bw.newLine();
            System.out.println(chatID + " was added to " + username);
        } catch (IOException e) {
            System.err.println("Error writing to credentials file: " + e.getMessage());
        }

    }
    public void createNewUserFile(String username) {
        String filename = "users\\" + username + ".txt";
        try (PrintWriter bw = new PrintWriter(new FileWriter(filename))){
            bw.print("");
        } catch (IOException e) {
            System.err.println("Error writing to credentials file: " + e.getMessage());
        }
    }   
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        
        //server.writeCredentials("Cola", "Coca", "Cocacoola", "soda", true); //this works
    }

    private void extraFileTests() {
                //
    	//buildChatRoom();
        //testBuildUsers();
        //testWriteUserChatList();
        
        // List<User> users = generateUsers(4,4);
        // User user = users.get(0);
        
        // for (ChatRoom chat : user.getChats()) {
        //     writeChatRoomFile(chat);
        // }
        // writeUserChatList(user);

    //    User builtUser =  buildUser("User1");
    //    for (ChatRoom chatroom : builtUser.getChats()) {
    //     String participants = "";
    //     System.out.println("ChatID: " + chatroom.getChatID() + "");
    //     for (String user : chatroom.getParticipants())
    //         participants = participants + user + ", ";
    //     System.out.println("Participants: " + participants + "\n");
    //     for (Message m : chatroom.getMessages()) {
    //         System.out.println(m.toString());
    //     }
    //     System.out.println("");
    // }
    }

}