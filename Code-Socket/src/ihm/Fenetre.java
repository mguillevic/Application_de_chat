import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Fenetre extends JFrame{
	
	private ConversationPanel convPanel = new ConversationPanel();
	private JTextField messageField = new JTextField(20);
	private JButton sendButton = new JButton("SEND");
	private JPanel panelWriteMessage = new JPanel();
	
	private ContactPanel contactPanel = new ContactPanel();
	
	public Fenetre() {
		this.setTitle("Application Chat");
		this.setSize(700,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.placerContactPanel();
		this.placerMessageField();
		this.placerConversationPanel();
		
		this.setVisible(true);
	}
	
	private void placerConversationPanel() {
		
		JScrollPane scroll = new JScrollPane(convPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
	
	private void placerMessageField() {
		messageField.setFont(new Font("Sherif",Font.PLAIN,30));
		panelWriteMessage.add(messageField);
		panelWriteMessage.add(sendButton,BorderLayout.EAST);
		sendButton.addActionListener(new SendListener(messageField,sendButton,convPanel));
		this.add(panelWriteMessage,BorderLayout.SOUTH);
	}
	
	private void placerContactPanel() {
		contactPanel.setPreferredSize(new Dimension(100,getHeight()));
		contactPanel.afficherContacts();
		this.add(contactPanel,BorderLayout.WEST);
	}
	
	public ConversationPanel getConvPanel() {
		return this.convPanel;
	}
}
