// Change to single selection or multiple selection for appropriate menus of manageCreds/viewLog or createChat, respectively


import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
//import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class CreateNewChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Client client;
	
	private ChatRoom chatroom;
	private List<User> users;
	private User currentUser;
	private ChatRoom chat;

	private User[] participants;
	private List<String> usernames;
	// passing User 
	
	
	public CreateNewChatPanel(Client client, User currentUser) {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		this.client = client;
		this.currentUser = currentUser;
		
		updateUsers(client.getCurrentUserlist());
		currentUser = users.get(0);
		
		participants = new User[10];
		
		// Panel for send button... putting button inside a border makes modifications easy!
//		JPanel cancelBorder = new JPanel();
//		cancelBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		cancelBorder.setBounds(353, 392, 81, 38);
//		add(cancelBorder);
//		cancelBorder.setLayout(new BorderLayout(0, 0));
//		
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.setToolTipText("Hit button to send message...");
//		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
//		cancelBorder.add(btnCancel, BorderLayout.CENTER);
		
		JPanel confirmBorder = new JPanel();
		confirmBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		confirmBorder.setBounds(255, 392, 81, 38);
		add(confirmBorder);
		confirmBorder.setLayout(new BorderLayout(0, 0));
		
		
		
		// Panel for text messages box
		JPanel userListBorder = new JPanel();
		userListBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		userListBorder.setBounds(3, 30, 431, 360);
		add(userListBorder);
		userListBorder.setLayout(new BorderLayout(0, 0));
		
		DefaultListModel userData = new DefaultListModel();
        for (User user1 : users) {
        	userData.addElement(user1);
        }
       
		
//		AbstractListModel<String> userListModel = new AbstractListModel<String>() {
//            @Override
//            public int getSize() {
//                return userData.size();
//            }
//
//            @Override
//            public String getElementAt(int index) {
//                return userData.get(index);
//            }
//        };
  
		JList userList = new JList<User>(userData);
		userListBorder.add(userList, BorderLayout.CENTER);
		
		userList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int[] selectedIndices = userList.getSelectedIndices();
					
					User[] selectedValues = new User[selectedIndices.length];
					usernames = new ArrayList<String>();
					
					for (int index = 0; index < selectedIndices.length; index++) {
						selectedValues[index] = (User) userData.getElementAt(selectedIndices[index]);
					}
					
					for (int i = 0; i < selectedValues.length; i++) {
						usernames.add(selectedValues[i].getUsername());					
					}
					
					//JOptionPane.showMessageDialog(userListBorder, usernames);	 // for debug
					
				}	
			}
		});
		JPanel promptBorder = new JPanel();
		promptBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		promptBorder.setBounds(3, 1, 431, 25);
		add(promptBorder);
		promptBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea promptText = new JTextArea();
		promptText.setText("Select user\r\n");
		promptText.setToolTipText("List of current room's members.");
		promptText.setEditable(false);
		promptText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		promptBorder.add(promptText, BorderLayout.CENTER);
		
		JButton btnConfirm = new JButton("OK");
		confirmBorder.add(btnConfirm, BorderLayout.CENTER);
		btnConfirm.setToolTipText("Press OK to confirm selection");
		btnConfirm.setFont(new Font("Dialog", Font.BOLD, 12));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// usernames contains my value
				usernames.add(client.getCurrentUser().getUsername()); // need to add currentUsers to participants lists
				CreateChatMessage msg = new CreateChatMessage(usernames);
				new Thread(()->{client.sendMessageToServer(msg);}).start();
			}
		});
		
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

	public void updateUsers(Map<String, User> newUserList) {
		List<User> toUpdate = new ArrayList<>();
		for (User u : newUserList.values()) {
			toUpdate.add(u);
		}

		this.users = toUpdate;
	}
}