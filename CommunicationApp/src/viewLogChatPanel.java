import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class viewLogChatPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public viewLogChatPanel() {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		
		// Panel for send button... putting button inside a border makes modifications easy!
		JPanel cancelBorder = new JPanel();
		cancelBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		cancelBorder.setBounds(353, 392, 81, 38);
		add(cancelBorder);
		cancelBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setToolTipText("Hit button to send message...");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		cancelBorder.add(btnCancel, BorderLayout.CENTER);
		
		JPanel confirmBorder = new JPanel();
		confirmBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		confirmBorder.setBounds(255, 392, 81, 38);
		add(confirmBorder);
		confirmBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnConfirm = new JButton("OK");
		confirmBorder.add(btnConfirm, BorderLayout.CENTER);
		btnConfirm.setToolTipText("Press OK to confirm selection");
		btnConfirm.setFont(new Font("Dialog", Font.BOLD, 12));
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				
				
				}
		});
		
		// Panel for text messages box
		JPanel chatListBorder = new JPanel();
		chatListBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		chatListBorder.setBounds(3, 30, 431, 360);
		add(chatListBorder);
		chatListBorder.setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setFont(new Font("Monospaced", Font.PLAIN, 12));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Chat 1", "Chat 2", "Chat 3"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		chatListBorder.add(list, BorderLayout.CENTER);
		//add(scrollPane);
		// Panel for participants
		JPanel promptBorder = new JPanel();
		promptBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		promptBorder.setBounds(3, 1, 431, 25);
		add(promptBorder);
		promptBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea promptText = new JTextArea();
		promptText.setText("Select chat to view\r\n");
		promptText.setToolTipText("List of current room's members.");
		promptText.setEditable(false);
		promptText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		promptBorder.add(promptText, BorderLayout.CENTER);
		
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
}