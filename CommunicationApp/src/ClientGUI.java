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

	private MessagePanel mssgPanel;
	private UserPanel userPanel;
	private CreateNewChatPanel createNewChatPanel;

	// Building the main elements of the GUI - these will always be visible
	public ClientGUI(Client client, User currentUser) {
        this.client = client;
        this.currentUser = currentUser;
		//client.setupConnection();
        // this.activeChat = null;
		
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
		
		JPanel westPanel = new JPanel();
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
					ChatRoom selectedItem = (ChatRoom) chatrooms.getSelectedValue();
						//String selectedItem = (String) chatrooms.getSelectedValue();	
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
			
		
		// These are IT buttons
		ITButtons = new JButton[2];
		JButton viewLogsButton = new JButton("View Logs");
		//mainButtonPanel.add(viewLogsButton);
		JButton modifyUsersButtons = new JButton("Modify/Add Users");
		//mainButtonPanel.add(modifyUsersButtons);
		ITButtons[ITButtonAction.VIEW_LOGS.ordinal()] = viewLogsButton;
		ITButtons[ITButtonAction.ADD_MODIFY_USERS.ordinal()] = modifyUsersButtons;
				
		// if current user is IT
		// for (JButtons b : ITButtons)
		//		mainButtonPanel.add(b);
		// else 
		// 		only display add users
		for (JButton b : ITButtons)
			mainButtonPanel.add(b);			
			
		// centerPanel is where all main panels will go
		// The following panels will go here:
		// Inside of a chatroom panel
		// New chat panel
		// View Logs panel
		// Modify / Add user panel
		JPanel centerPanel = new JPanel();
		//centerPanel.setBorder(new EmptyBorder(0,0,1,1));
		centerPanel.setOpaque(false);
		//centerPanel.setBorder(new LineBorder(new Color(78, 167, 46), 1, true));
		contentPane.add(centerPanel);
		centerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		//Message Field
		mssgPanel = new MessagePanel();
		mssgPanel.setListener(new MessagePanel.MessageListener() {
			@Override
			public void onSendMessage (String mssg) {
				ChatMessage chatMessage = new ChatMessage(mssg, MessageStatus.SENT, MessageTypes.CHAT_MESSAGE);
				client.sendMessageToServer(chatMessage);
				System.out.println("Debug: onSendMessage");
			}
		});
		
		// Main Action Buttons inside mainButton Panel
		JButton createNewChatButton = new JButton("Create New Chat");
		mainButtonPanel.add(createNewChatButton);

		createNewChatButton.addActionListener(new ActionListener() {	// 4/26 JSN: working on bringing this panel forward to be visible
			public void actionPerformed(ActionEvent e) {
				createNewChatPanel = new CreateNewChatPanel(currentUser);
				centerPanel.add(createNewChatPanel,BorderLayout.CENTER);
				JOptionPane.showMessageDialog(createNewChatButton, "Debug");
			}																
		});
		createNewChatPanel = new CreateNewChatPanel(currentUser);
		userPanel = new UserPanel();
		//centerPanel.add(mssgPanel,BorderLayout.CENTER);
		centerPanel.add(userPanel, BorderLayout.CENTER);	
	}

    public void updateMessagePanel(String message) {
        SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				mssgPanel.getTextMessages().append(message + "\n");
			}
		}); 
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
					ClientGUI frame = new ClientGUI(new Client(), new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
