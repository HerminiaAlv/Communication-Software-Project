import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import java.util.Random;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;

enum ITButtonAction {
	VIEW_LOGS,
	ADD_MODIFY_USERS
};

public class ClientGUI extends JFrame{
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
    private modifyUserPanel modifyUserPanel;
    private MessagePanel mssgPanel;
    private UserPanel userPanel;
    private CreateNewChatPanel createNewChatPanel;

    // West Panel Attributes
    private DefaultListModel westChatList;
    JList<ChatRoom> westChatrooms;

    // Building the main elements of the GUI - these will always be visible
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ClientGUI(Client client, User currentUser) {
    this.client = client;
    this.currentUser = currentUser;
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
		//bc_lblNewLabel.setFont(new Font("Monospaced", Font.PLAIN, 8));
				
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
		
		JScrollPane nonpinnedChatsScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		nonpinnedChatsScrollPane.setViewportBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		nonpinnedChatsScrollPane.setPreferredSize(new Dimension(80, 80));

		// JScrollPane constraints
		GridBagConstraints gbc_nonpinnedChatsScrollPane = new GridBagConstraints();
		gbc_nonpinnedChatsScrollPane.fill = GridBagConstraints.BOTH;
		gbc_nonpinnedChatsScrollPane.gridx = 0;
		gbc_nonpinnedChatsScrollPane.gridy = 2; // No gap in gridY, directly after pinnedList
		chatroomPanel.add(nonpinnedChatsScrollPane, gbc_nonpinnedChatsScrollPane);

		// CHAT LIST DISPLAY
		//@SuppressWarnings("rawtypes")
		westChatList = new DefaultListModel();
		for (ChatRoom room : currentUser.getChats())
			westChatList.addElement(room);
		

		westChatrooms = new JList(westChatList);
		westChatrooms.setFont(new Font("Aptos", Font.BOLD, 11));

		westChatrooms.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()){
					// This is where we will display the chatroom messages
					// Goal: When a chatroom is selected, display the chatroom logged messages (Assume that correct logged messaging will be implemented)
					   ChatRoom selectedItem = (ChatRoom) westChatrooms.getSelectedValue();
					   mssgPanel.setupChatroom(selectedItem);
					   invokeNewPanel(mssgPanel);
					}
				}
			});
		westChatrooms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		westChatrooms.createToolTip();
		nonpinnedChatsScrollPane.setViewportView(westChatrooms);
		
			
		// inside westPanel
		JPanel mainButtonPanel = new JPanel();
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
		JButton modifyUsersButton = new JButton("Modify/Add Users");
		ITButtons[ITButtonAction.VIEW_LOGS.ordinal()] = viewLogsButton;
		ITButtons[ITButtonAction.ADD_MODIFY_USERS.ordinal()] = modifyUsersButton;
		
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
		mssgPanel = new MessagePanel(client, currentUser.getChats().get(0)); 
		mssgPanel.setListener(new MessagePanel.MessageListener() {
			@Override
			public void onSendMessage (Message message) {
				//ChatMessage chatMessage = new ChatMessage(mssg, MessageStatus.SENT, MessageTypes.CHAT_MESSAGE); 
				ChatMessage chatMessage = new ChatMessage(message);
				chatMessage.setParticipants(mssgPanel.getCurrentChat().getParticipants());
				client.sendMessageToServer(chatMessage);
				//System.out.println("Debug: onSendMessage");
			}
		});
		// Create New Chat Initialization
		createNewChatPanel = new CreateNewChatPanel(client, currentUser);
		//userPanel = new UserPanel();

		// Log Viewing Panel Initialization
		logViewPanel = new viewLogChatPanel(client, currentUser);
		
		// Modify User Panel Initialization
		modifyUserPanel = new modifyUserPanel(client);

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
		modifyUsersButton.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
				invokeNewPanel(modifyUserPanel);
			}																
		});
		// Logout Button Listener
		logoutButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Clock Out", JOptionPane.YES_NO_OPTION);
				if (confirm  == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
			//displayLoggedMessages(activeChat);
		});
	} 
	// End Constructor 
    public void updateMessagePanel(String message) {
        SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				mssgPanel.getTextMessages().append(message + "\n");
			}
		}); 
	}

	public void invokeNewPanel(JPanel panel) {
/* 		if (panel == viewLogChatPanel)
			panel = new viewLogChatPanel(client, currentUser)
			return; */
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
	/*
	 * Removes the currentCenterPanel and places a new one.
	 * @param toPlace	
  	 * 	This is the panel you want to put on the centerPanel.
	 *      When you press a button to get a new view call this method
	 *      with the associated panel.
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
	
	public DefaultListModel getViewLogsChatList() {
		return logViewPanel.getViewLogsChatList();
	}

	// Call this from client when you get a new chatroom
	// you might have to do a for each loop addChatToWestPanel if you have a list of chatrooms.
	public void addChatToWestPanel(ChatRoom toAdd) {
		// This should update the west chatlist panel whenever theres a new incoming chatroom 
		westChatList.addElement(toAdd);
		westChatrooms.revalidate();
		westChatrooms.repaint();
	}

	public CreateNewChatPanel getCreateNewChatPanel() {
		return this.createNewChatPanel;
	}


	// Private Testing variables
	private String[] chats =  { "Chat 1",
				"Chat 2",
				"Chat 3",
				"Chat 4",
				"Chat 5",
				"Chat 6",
				"Chat 7",
				"Chat 8",
				"Chat 9"};
		
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

	
