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
import javax.swing.JCheckBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
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
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class modifyUserPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Client client;

	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtUserName;
	private JTextField txtPassword;
	private JCheckBox checkBoxIsIT;
	
	public modifyUserPanel(Client client) {
		setForeground(new Color(135, 206, 250));
		setBackground(new Color(21, 96, 130));
		setLayout(null);
		this.client = client;
		
//		JPanel cancelBorder = new JPanel();
//		cancelBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
//		cancelBorder.setBounds(353, 392, 81, 38);
//		add(cancelBorder);
//		cancelBorder.setLayout(new BorderLayout(0, 0));
//		
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.setToolTipText("Press cancel button to exit");
//		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
//		cancelBorder.add(btnCancel, BorderLayout.CENTER);
		
		
		
		// Panel for text messages box
		JPanel editMenuBorder = new JPanel();
		editMenuBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		editMenuBorder.setBounds(3, 32, 431, 360);
		add(editMenuBorder);
		editMenuBorder.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(131, 110, 68, 14);
		editMenuBorder.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(131, 135, 68, 14);
		editMenuBorder.add(lblLastName);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(131, 160, 68, 14);
		editMenuBorder.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(131, 185, 68, 14);
		editMenuBorder.add(lblPassword);
		
		JLabel lbl_ITPerms = new JLabel("IT User:");
		lbl_ITPerms.setBounds(131, 210, 68, 14);
		editMenuBorder.add(lbl_ITPerms);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(209, 107, 86, 20);
		editMenuBorder.add(txtFirstName);
		txtFirstName.setColumns(10);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(209, 132, 86, 20);
		editMenuBorder.add(txtLastName);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBounds(209, 157, 86, 20);
		editMenuBorder.add(txtUserName);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(209, 185, 86, 20);
		editMenuBorder.add(txtPassword);
		
		JCheckBox checkboxIs_IT = new JCheckBox("Is IT User");
		checkboxIs_IT.setBounds(209, 210, 164, 28);
        editMenuBorder.add(checkboxIs_IT);
		//add(scrollPane);
		// Panel for participants
		JPanel promptBorder = new JPanel();
		promptBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		promptBorder.setBounds(3, 1, 431, 25);
		add(promptBorder);
		promptBorder.setLayout(new BorderLayout(0, 0));
		
		JTextArea promptText = new JTextArea();
		promptText.setText("Edit credentials in the text fields");
		promptText.setToolTipText("List of current room's members.");
		promptText.setEditable(false);
		promptText.setFont(new Font("Monospaced", Font.PLAIN, 12));
		
		promptBorder.add(promptText, BorderLayout.CENTER);
		
		JPanel applyBorder = new JPanel();
		applyBorder.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46), new Color(78, 167, 46)));
		applyBorder.setBounds(255, 392, 81, 38);
		add(applyBorder);
		applyBorder.setLayout(new BorderLayout(0, 0));
		
		JButton btnApply = new JButton("Apply");
		applyBorder.add(btnApply, BorderLayout.CENTER);
		btnApply.setToolTipText("Press Apply to confirm selection");
		btnApply.setFont(new Font("Dialog", Font.BOLD, 12));
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstname = txtFirstName.getText();
				String lastname = txtLastName.getText();
				String username = txtUserName.getText();
				String password = txtPassword.getText();
				boolean isIT = checkboxIs_IT.isEnabled();
//				JOptionPane.showMessageDialog(applyBorder, txtFirstName);
				UpdateUserMessage updateUserMessage = new UpdateUserMessage(username, firstname, lastname, password, isIT);				
				// Need to dispatch a new thread and run client.sendMessageToServer()
				new Thread(()->{client.sendMessageToServer(updateUserMessage);}).start();
				txtFirstName.setText("");
				txtLastName.setText("");
				txtUserName.setText("");
				txtPassword.setText("");
				checkboxIs_IT.setSelected(false);

//				}
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
}