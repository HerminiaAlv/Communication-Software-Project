import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.ScrollPane;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Client client;
	private ChatRoom currentChat;
	//private List<Messages> messages;

	private JFormattedTextField textBox;
	
	public MessagePanel() {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		// Panel for text box 
		JPanel textBorder = new JPanel();
		textBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		textBorder.setBounds(3, 391, 431, 40);
		add(textBorder);
		textBorder.setLayout(new BorderLayout(0, 0));
		
		textBox = new JFormattedTextField();
		textBox.setToolTipText("Type message...");
		textBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textBox.setHorizontalAlignment(SwingConstants.LEFT);
		textBox.setColumns(30);
		textBorder.add(textBox);
		
		// Panel for send button... putting button inside a border makes modifications easy!
		JPanel sendBorder = new JPanel();
		sendBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		sendBorder.setBounds(450, 393, 75, 38);
		add(sendBorder);
		sendBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.setToolTipText("Hit button to send message...");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 12));
		sendBorder.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Need to dispatch a new thread and run client.sendMessageToServer()
				// 
				int count = 0;
				String empty = "";
				if ((textBox.getText()).equals(empty)) {
					textBox.setText("");
					textBox.requestFocus();
				} else {
					// handle event of writing passed message to the messages area
					// suggested format: ['timestamp'] + username + ": " + message (textBox.getText()) + Data Type
//					try {
//						//write to message area using suggested format then
//						//flush the buffer;
//					} catch (Exception e1) {
//						textMessages.append("Message was not sent. \n"); //Print error message
//					};
					textBox.setText(""); //set text back to "" 
					textBox.requestFocus(); //send it back to text box..
				}
			}
		});
		
		// Panel for text messages box
		JPanel textMessagesBorder = new JPanel();
		textMessagesBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		textMessagesBorder.setBounds(3, 30, 431, 351);
		add(textMessagesBorder);
		textMessagesBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea textMessages = new JTextArea();
		textMessages.setEditable(false);
		//textMessagesBorder.add(textMessages);
		textMessages.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textMessages.setWrapStyleWord(true);
		textMessages.setLineWrap(true);
		textMessages.setText(testString);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setForeground(new Color(216, 191, 216));
		scrollPane.setFont(new Font("Dubai Medium", Font.PLAIN, 5));
		scrollPane.setBackground(new Color(245, 245, 245));
		scrollPane.setBounds(3, 32, 422, 350);
		textMessagesBorder.add(scrollPane, BorderLayout.NORTH);
		scrollPane.add(textMessages);
		//add(scrollPane);
		// Panel for participants
		JPanel participantsBorder = new JPanel();
		participantsBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		participantsBorder.setBounds(3, 1, 431, 25);
		add(participantsBorder);
		participantsBorder.setLayout(new BorderLayout(0, 0));
		
		JLabel lblRoomParticipants = new JLabel("Participants:");
		lblRoomParticipants.setBackground(new Color(240, 240, 240));
		lblRoomParticipants.setForeground(new Color(0, 0, 0));
		lblRoomParticipants.setFont(new Font("Monospaced", Font.PLAIN, 12));
		participantsBorder.add(lblRoomParticipants, BorderLayout.WEST);
		
		JTextArea roomMembers = new JTextArea();
		roomMembers.setText(" John (Online), Mark, Dave\r\n");
		roomMembers.setToolTipText("List of current room's members.");
		roomMembers.setEditable(false);
		roomMembers.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		participantsBorder.add(roomMembers, BorderLayout.CENTER);
		
		// Panel for Online users... Uses JLabel + JList (Might change) 
//		JList list = new JList();
//		list.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		list.setBounds(442, 38, 107, 348);
//		add(list);
//		
//		JPanel usersBorder = new JPanel();
//		usersBorder.setBounds(442, 6, 107, 25);
//		add(usersBorder);
//		usersBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		usersBorder.setLayout(new BorderLayout(0, 0));
//		
//		JLabel lblOnlineUsers = new JLabel("Online Users");
//		lblOnlineUsers.setFont(new Font("Monospaced", Font.PLAIN, 12));
//		lblOnlineUsers.setHorizontalAlignment(SwingConstants.CENTER);
//		usersBorder.add(lblOnlineUsers, BorderLayout.CENTER);

	}

	/**
	 * Initializes the message panel for a specific chat room.
	 * 
	 * @param chatroom The chat room to set up the message panel for.
	 *                 This method will update the panel to display
	 *                 messages, participants, and other relevant information
	 *                 related to the provided chat room.
	 */
	public void setupChatroom(ChatRoom chatroom) {
		
	}

	// public void setCurrentChat(ChatRoom chatroom) {
	// 	this.currentChat = chatroom;
	// }

	public ChatRoom getCurrentChat() {
		return currentChat;
	}

	// extra private vars
	private String testString = "[04.16.24@08:03] John: Hey guys, don't forget our meeting tomorrow!\r\n"
	+ "[04.16.24@14:23] Mark: Hi John, what time is it again? \r\n"
	+ "[04.16.24@15:26] John: Hi Mark! We start at 12:15 PST  \r\n"
	+ "[04.16.24@16:01] Dave: Sounds good! Will be there on time!\r\n"
	+ "[04.17.24@11:23] Dave: Testing the scroll pane if it works!\r\n\n"
	+ "siUEMRyKrLS0EW8VnDTnqeeE6VVTP21U\r\n"
	+ "zTQHw8Z3zSeMaWIekXLe08N629TEvVPl\r\n"
	+ "no9G9WzlxtBGwpEimHBp9wJKmE3PxvAp\r\n"
	+ "kroeGhWtF6FhmcX4Xt3AfUE9OBtB2bfd\r\n"
	+ "2kdrtGZtD06QcpynusMd8VFt2IanF56F\r\n"
	+ "Eoph9yu5JhCkX92siC4lC3y3160jTcx6\r\n"
	+ "8pkxquTKbjhCdRlTn7KBkRMIrj1EfmsH\r\n"
	+ "NQA9feNy4ChSCDXEmnRhojoUupBtEigy\r\n"
	+ "VFySNs9kzHItDToukLCzEwYRYzfQXl0w\r\n"
	+ "c7rdUIhK9X9h1hS9Pd58oo4PmkCBLP9A\r\n"
	+ "mO33SW17sjnh9JEpb7Hm9kYoQPABPH9Q\r\n"
	+ "gWaQWbRiPmAxKa6MkDNs8jX9khJOKOYb\r\n"
	+ "AMM01xVXEGGlr7utKVVy75vHvnpAiDaB\r\n"
	+ "ka16TTZED6Vz5USGNiBL7yFPHQ28N8nZ\r\n"
	+ "cTZNLqW1coRU1dcJWEV9jGAqZynvPm55\r\n"
	+ "xYAkE2XCo4U35cfVMx1W9pLi34LzWMV1\r\n"
	+ "MqHbjnqKKWBEUSbAePLPU6aWV9iNBgxJ\r\n"
	+ "OKpF5jjoz8Jmzfi368Hpdb8OwjxztZdq\r\n"
	+ "pwr2rQZod8hPCernV5Vkm8Csmz3sY9nW\r\n"
	+ "g0LrKiqfWindj8LPh86DPZOGghlsvbfe\r\n"
	+ "BJUEtbi8tS8KO7234y7Jv9ocSgg5T9lO\r\n"
	+ "if7AxzlsXrcpz6zcUzQvXWYE20Lz3tuJ\r\n"
	+ "fIBezev4uWXdInZmsMNTu3YClWw7L1YW\r\n"
	+ "x2X0t6F146zuiIUjatMgZdUsFGKstUsz\r\n"
	+ "1PcAikSa0rK8FrNitdpFc1klH1lYiLAt";
}
