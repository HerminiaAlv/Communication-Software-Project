import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import java.util.Random;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.BorderFactory;

enum ITButtonAction {
	VIEW_LOGS,
	ADD_MODIFY_USERS
};

public class ClientGUI extends JFrame{
/* 	private ObjectOutputStream out;
    private ObjectInputStream in;
	private JTextArea textMessages;
	private JFormattedTextField textBox; */

	//private void createGUI(){
    private Client client; // to access methods from the client
	private static final long serialVersionUID = 1L; // from JFrame
	private JPanel contentPane; // Where all componets attach to
	private JButton[] ITButtons; // size [2]
		// [0] - VIEW_LOGS
		// [1] - ADD_MODIFY_USERS
	
	
    private User currentUser;
    private ChatRoom activeChat; // The chat that is currently being viewed
    private int HEIGHT = 480;
    private int WIDTH = 720;
    //private static int notificationCounter; // This might change 

	private JPanel centerPanel;
	private JPanel westPanel;
	private JPanel currentCenterPanel; // This is the panel that is currently visible on the center panel

	// Possible Center Panels
	private viewLogChatPanel logViewPanel;
	//private ModifyUserPanel modifyUserPanel;
	private MessagePanel mssgPanel;
	private UserPanel userPanel;
	private CreateNewChatPanel createNewChatPanel;

	// Building the main elements of the GUI - these will always be visible
	public ClientGUI(Client client, User currentUser) {
        this.client = client;
        this.currentUser = currentUser;
		//client.setupConnection();
        //activeChat = currentUser.getChats().get(0); // Active
		
        //start up 
        setResizable(false); //disable maximize button
		setTitle("Employee Message App");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, WIDTH, HEIGHT);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 96, 130));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(25, 15));
		
		westPanel = new JPanel();
		westPanel.setBackground(new Color(21, 96, 130));
		contentPane.add(westPanel, BorderLayout.WEST);
		westPanel.setLayout(new GridLayout(0, 1, 10, 20));
			
		// inside westPanel
		JPanel chatroomPanel = new JPanel();
		chatroomPanel.setBorder(new LineBorder(new Color(78, 167, 46), 1, true));
		westPanel.add(chatroomPanel);
		GridBagLayout gbl_chatroomPanel = new GridBagLayout();
		gbl_chatroomPanel.columnWidths = new int[]{115, 0};
		gbl_chatroomPanel.rowHeights = new int[] {10, 60, 90};
		gbl_chatroomPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_chatroomPanel.rowWeights = new double[]{0.0, 0.0, 1.0};
		chatroomPanel.setLayout(gbl_chatroomPanel);
		
		// inside chatroomPanel
		TitledBorder roomBorder = BorderFactory.createTitledBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Chat Rooms"));
		chatroomPanel.setBorder(roomBorder);
		// Label constraints
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		//chatroomPanel.add(chatroomPanel, gbc_lblNewLabel);
				
		JList pinnedList = new JList<String>(pinned);
		pinnedList.repaint();
		pinnedList.setVisibleRowCount(3);
				
		// pinnedList constraints
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH; // Changed to BOTH to allow vertical stretch
		gbc_list.insets = new Insets(0, 0, 18, 0);
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		//gbc_list.
		chatroomPanel.add(pinnedList, gbc_list);
		
		JScrollPane nonpinnedChatsScrollPane = new JScrollPane();
		nonpinnedChatsScrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		// JScrollPane constraints
		GridBagConstraints gbc_nonpinnedChatsScrollPane = new GridBagConstraints();
		gbc_nonpinnedChatsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_nonpinnedChatsScrollPane.gridx = 0;
		gbc_nonpinnedChatsScrollPane.gridy = 2; // No gap in gridY, directly after pinnedList
		chatroomPanel.add(nonpinnedChatsScrollPane, gbc_nonpinnedChatsScrollPane);
		JList chatrooms = new JList<String>(chats);
		chatrooms.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					//ChatRoom selectedItem = (ChatRoom) chatrooms.getSelectedValue();
					// Need to mssgpanel.setupChatroom(selectedItem)
					// then invoke new panel

					// TODO Go back and test this when we are actually initializing chatroom objects
					// need to test the case where mssgPanel is already currentCenterPanel.
					invokeNewPanel(mssgPanel);
					}
				}
			});
		chatrooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		nonpinnedChatsScrollPane.setViewportView(chatrooms);
			
		// inside westPanel
		JPanel mainButtonPanel = new JPanel();
		//mainButtonPanel.setBorder(new LineBorder(new Color(78, 167, 46), 1, true));
		westPanel.add(mainButtonPanel);
		mainButtonPanel.setLayout(new GridLayout(0, 1, 5, 15));
		mainButtonPanel.setOpaque(false);
		
		// Main Action Buttons inside mainButton Panel
		JButton createNewChatButton = new JButton("Create New Chat");
		mainButtonPanel.add(createNewChatButton);
		JButton logoutButton = new JButton("Logout");
		
		// These are IT buttons
		ITButtons = new JButton[2];
		JButton viewLogsButton = new JButton("View Logs");
		JButton modifyUsersButtons = new JButton("Modify/Add Users");
		ITButtons[ITButtonAction.VIEW_LOGS.ordinal()] = viewLogsButton;
		ITButtons[ITButtonAction.ADD_MODIFY_USERS.ordinal()] = modifyUsersButtons;
		
		// Button Display Logic 
		if (!currentUser.isIT()) { // Standard user
			// place logout right after create new chat
			mainButtonPanel.add(logoutButton);
			for (JButton b : ITButtons) {
				mainButtonPanel.add(b);	
				if (!currentUser.isIT())
					b.setVisible(false);
			}
		}
		else { // IT User
			// it buttons first then logout
			for (JButton b : ITButtons) 
				mainButtonPanel.add(b);
			mainButtonPanel.add(logoutButton);
		}

		// centerPanel is where all main panels will go
		// The following panels will go here:
		// Inside of a chatroom panel
		// New chat panel
		// View Logs panel
		// Modify / Add user panel
		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		contentPane.add(centerPanel);
		centerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		//Message Field
		mssgPanel = new MessagePanel(client, currentUser.getChats().get(0)); // initialize the currentChat of message panel with users first chat. Might change
		mssgPanel.setListener(new MessagePanel.MessageListener() {
			@Override
			public void onSendMessage (Message mssg) {
				ChatMessage chatMessage = new ChatMessage(mssg);
				new Thread(()->{client.sendMessageToServer(chatMessage);}).start();
				System.out.println("Debug: onSendMessage");
			}
		});

		// Create New Chat Initialization
		createNewChatPanel = new CreateNewChatPanel(currentUser);
		//userPanel = new UserPanel();

		// Log Viewing Panel Initialization
		logViewPanel = new viewLogChatPanel();

		// Placing objects on the center panel
		//centerPanel.add(mssgPanel,BorderLayout.CENTER);
		invokeNewPanel(mssgPanel);
		
		// Button Listeners Here
		createNewChatButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				invokeNewPanel(createNewChatPanel);
			}																
		});
		viewLogsButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				invokeNewPanel(logViewPanel);
			}																
		});
		
	} // End Constructor 

    public void updateMessagePanel(String message) {
        SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				mssgPanel.getTextMessages().append(message + "\n");
			}
		}); 
	}

	public void invokeNewPanel(JPanel panel) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					placePanelOnCenter(panel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Removes the currentCenterPanel and places a new one.
	 * 
	 * @param toPlace	This is the panel you want to put on the centerPanel.
	 *               	When you press a button to get a new view call this method
	 *                  with the associated panel.
	 */
	private void placePanelOnCenter(JPanel toPlace) {
		// Check if the panel toPlace is the same panel
		if (toPlace == currentCenterPanel)
			return; // do nothing

		if (currentCenterPanel == null)
			currentCenterPanel = toPlace;
		else {
			centerPanel.remove(currentCenterPanel);
			currentCenterPanel = toPlace;
		}
		// place the new one
		centerPanel.add(currentCenterPanel);
		centerPanel.revalidate();  
		centerPanel.repaint();     
	}


	// Private Testing variables
	// private variables just for testing
	private String[] chats =  { "Chat Room 1",
			"Chat Room 2",
			"Chat Room 3",
			"Chat Room 4",
			"Chat Room 5",
		};
		
	private String[] pinned =  { "Pinned Chat 1",
				"Pinned Chat 2",
				"Pinned Chat 3"};

	// Testing methods
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client();
					User testUser = new User();
					testUser.setIT(true); // It User GUI
					//testUser.setIT(false); // Stanard user GUI
					ClientGUI frame = new ClientGUI(new Client(), testUser);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
