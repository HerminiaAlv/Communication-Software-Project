import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.io.IOException;

public class LoginUI extends JFrame {
	private JTextField usernameBox;
	private JPasswordField passwordBox;
	private JButton loginButton;
	private Client client; // Reference to client

	private boolean awaitingServer; // flag to determine if waiting for server response
	
	public LoginUI(Client client) {
		this.client = client;

		//Frames 
		setTitle("Login");
		setSize(350,300); //size
		setResizable(false); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		JPanel borderPanel = new JPanel();
		borderPanel.setBorder(new EmptyBorder(50,80,50,80)); //padding for panel
		getContentPane().setBackground(new Color(21, 96, 130)); // bg color
		borderPanel.setOpaque(false); //transparent
		
		//borderPanel.setBorder(null);
		
		//components
		JPanel loginPanel = new JPanel(new GridLayout(3,2,5,5));
		loginPanel.setOpaque(false);
		borderPanel.add(loginPanel);
		
		//add login components
		//username field
	//	loginPanel.add(new JLabel("Username: "));
		usernameBox = new JTextField(15);
		usernameBox.setBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Username: "));
		usernameBox.setForeground(Color.WHITE);
		usernameBox.setOpaque(false);
		usernameBox.setCaretColor(Color.WHITE);
		usernameBox.setSelectionColor(Color.WHITE);
		loginPanel.add(usernameBox);
		
		//password field
		passwordBox = new JPasswordField(15);  //first 2 letters of name, last 2 of surname, + 4 digit ID
		passwordBox.setBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Password: "));
		passwordBox.setOpaque(false);
		passwordBox.setCaretColor(Color.WHITE);
		loginPanel.add(passwordBox);
		
		//login button
	    loginButton = new JButton("Login");
	    loginButton.setOpaque(true);
	    //loginButton.setBorder(new LineBorder(new Color(78,167,46))); 
	   // loginButton.setBackground(new Color(78,167,46)); // button background
	    loginButton.setForeground(new Color(21, 96, 130)); // text color
        borderPanel.add(loginButton);
        
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent loginEvent) {
                login();
            }
        });
        
        add(borderPanel);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	public void login() {
		awaitingServer = true; // flag set
		setWaiting();

		String username = usernameBox.getText();
		String password = new String(passwordBox.getPassword());

		// Create login message to send to server
		LoginMessage request = new LoginMessage(password, username);

		// pass to a new thread
		new Thread(()->{
			client.sendMessageToServer(request);
		}).start();
		// This UI is now in a waiting state 
			
		//Login Success then Home screen for message app is revealed
		//Show standard view for standard users
		//Show IT view for IT users, it is the same as standard view but with extra buttons 
		//Will be handled in ClientGUI 
		
	}

	// Call this method when a response message is received from the server
	// This will allow the user to retry their login 
	public void updateWaitingStatus(boolean connected){
		if (awaitingServer) 
			awaitingServer = false;
		if (!connected) { // Attempt Unsuccessfull
			// need to reset GUI
			EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
				// Switch GUI into an accepting state again
				passwordBox.setText("");
				loginButton.setText("Login");
				loginButton.setEnabled(true);
				loginButton.setBackground(new Color(241,241,241));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
		}
	}

	public void clearPasswordBox() {
		// Intended to clear the password entered in the password box
	}
	// Private Methods
	private void setWaiting() {
		// Display waiting indicator 
		EventQueue.invokeLater(new Runnable() { 
			public void run() {
				try {
				// Switch GUI into a nonaccepting state 
				//passwordBox.setText("");
				loginButton.setText("Waiting...");
				loginButton.setEnabled(false);
				loginButton.setBackground(new Color(241,241,241));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		    });
		awaitingServer = true;
	}

	//testing 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginUI(new Client());
			}
		});
	}
}
