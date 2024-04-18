import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.ListSelectionModel;
import java.util.Random;
import javax.swing.SwingConstants;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;

public class ClientGUI extends JFrame{
	enum ITButtonAction {
		VIEW_LOGS,
		ADD_MODIFY_USERS
	};
    private Client client; // to access methods from the client
	private static final long serialVersionUID = 1L; // from JFrame
	private JPanel contentPane; // Where all componets attach to
	private JButton[] ITButtons; // size [2]
    // [0] - VIEW_LOGS
	// [1] - ADD_MODIFY_USERS
    private User currentUser;
    //private ChatRoom activeChat; // The chat that is currently being viewed

    private int HEIGHT = 480;
    private int WIDTH = 720;
    private static int notificationCounter; // This might change 

	// Building the main elements of the GUI - these will always be visible
	public ClientGUI(Client client, User currentUser) {
        this.client = client;
        this.currentUser = currentUser;
        // this.activeChat = null;
		
        //start up 
        setResizable(false); //disable maximize button
		setTitle("Wow sick chat app");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, WIDTH, HEIGHT);
		//setSize(720,480);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(21, 96, 130));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//getContentPane().setBackground(new Color(21, 96, 130));
		//contentPane.setOpaque(false);
		
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
				//JLabel lblNewLabel = new JLabel("Chat Rooms");
			    //chatroomPanel.setBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Chat Rooms "));
			
				TitledBorder roomBorder = BorderFactory.createTitledBorder(new TitledBorder(new LineBorder(new Color(78,167,46)),"Chat Rooms"));
			    //roomBorder.set;
				chatroomPanel.setBorder(roomBorder);
			    //chatroomPanel.setBackground(Color.WHITE);
			    //chatroomPanel.setOpaque(false);
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
			
				// Main Action Buttons inside mainButton Panel
				JButton createNewChatButton = new JButton("Create New Chat");
				mainButtonPanel.add(createNewChatButton);
				
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
		
		JLabel lblNewLabel_1 = new JLabel("All panels that go on the center panel go here and should fill up the whole panel. Remove all objects on centerPanel and place it... ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		//centerPanel.add(lblNewLabel_1);
		
		//Component verticalStrut = Box.createVerticalStrut(20);
		//centerPanel.add(verticalStrut);
		JTextArea lblQuoteLabel = new JTextArea(getRandomQuote());
		
		lblQuoteLabel.setBackground(new Color(240, 240, 240));
		lblQuoteLabel.setWrapStyleWord(true);
		lblQuoteLabel.setLineWrap(true);
		lblQuoteLabel.setSize(10,20); //didnt do anything
		lblQuoteLabel.setAlignmentY(BOTTOM_ALIGNMENT);
		
		//Message Field
		MessagePanel MessagePanel = new MessagePanel();
		//MessagePanel.setLayout(new GridLayout(6,6,0,0));
		//MessagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.add(MessagePanel,BorderLayout.CENTER);
		
		
		//centerPanel.add(lblQuoteLabel);
	}
	// Private Testing variables
	// private variables just for testing
	private String[] chats =  { "Chat Room 1",
			"Chat Room 2",
			"Chat Room 3",
			"Chat Room 4",
			"Chat Room 5",
			"Chat Room 6",
			"Chat Room 7",
			"Chat Room 8",
			"Chat Room 9",
			"Chat Room 10",
			"Chat Room 11",
			"Chat Room 12",
			"Chat Room 13",
			"Chat Room 14",
			"Chat Room 15",
		};
		
	private String[] pinned =  { "Pinned Chat 1",
				"Pinned Chat 2",
				"Pinned Chat 3"};
	
    private static final String[] wittyQuotesWithPhilosophers = {
            "I think, therefore I am... still thinking. - René Descartes",
            "The only thing I know is that I know nothing, but I'm pretty sure about it. - Socrates",
            "Cogito ergo sum... but sometimes I doubt even that. - René Descartes",
            "To be or not to be? That is the question. Well, I'm still deciding. - William Shakespeare",
            "Life is what happens when you're busy contemplating other things. - Confucius",
            "The unexamined life might not be worth living, but neither is the overanalyzed one. - Socrates",
            "Give a man a fish and you feed him for a day. Teach a man to fish and he'll philosophize about it for a lifetime. - Laozi",
            "I know that I know nothing, but I'm pretty good at faking it. - Socrates",
            "I think, therefore I am... not entirely convinced. - René Descartes",
            "The only thing that interferes with my learning is my education... and my procrastination. - Albert Einstein",
            "Reality is merely an illusion, albeit a very persistent one... or so they say. - Albert Einstein",
            "I'm not a pessimist. I'm just an optimist with experience. - Albert Einstein",
            "The more I learn, the more I realize how much I don't know... which is a lot. - Albert Einstein",
            "Philosophy is a battle against the bewitchment of our intelligence by means of language... and memes. - Ludwig Wittgenstein",
            "If a tree falls in a forest and no one is around to hear it, does it make a sound? I don't know, but I do know I'm hungry. - George Berkeley",
            "Happiness is not something readymade. It comes from your actions... and sometimes, from a really good pizza. - Dalai Lama"
        };
	
	// Testing methods
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI(new Client(), new User());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    public static String getRandomQuote() {
        Random random = new Random();
        int index = random.nextInt(wittyQuotesWithPhilosophers.length);
        return wittyQuotesWithPhilosophers[index];
    }
    
}