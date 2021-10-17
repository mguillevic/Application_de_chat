package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.EchoClient;

import javax.swing.JButton;

//Fenêtre principale de l'application

public class Fenetre extends JFrame{
	
	private JTextField messageField = new JTextField(20);
	private JButton sendButton = new JButton("SEND");
	private JButton addContactButton = new JButton("+ New Contact");
	private JPanel panelWriteMessage = new JPanel();
	public static ContactPanel contactPanel;
	public static ConversationPanel convPanel = new ConversationPanel();
	private EchoClient client;
	
	private String currentContact="";
	
	public Fenetre(EchoClient c) throws IOException {
		
		this.setTitle("Application Chat");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		contactPanel = new ContactPanel(this);
		
		client=c;
		c.commencerConversation();
		
		this.placerContactPanel();
		this.placerMessageField();
		this.placerConversationPanel();
		
		this.setVisible(true);
	}
	
	public JButton getAddContactButton() {
		return addContactButton;
	}

	public JTextField getMessageField() {
		return messageField;
	}

	public JButton getSendButton() {
		return sendButton;
	}
//
	public JPanel getPanelWriteMessage() {
		return panelWriteMessage;
	}

	public ContactPanel getContactPanel() {
		return contactPanel;
	}
	
	private void placerConversationPanel() {
		
		JScrollPane scroll = new JScrollPane(convPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}

	public String getCurrentContact() {
		return currentContact;
	}

	private void placerMessageField() {
		SendListener listener = new SendListener(this);
		messageField.setFont(new Font("Sherif",Font.PLAIN,30));
		panelWriteMessage.add(addContactButton);
		addContactButton.addActionListener(listener);
		panelWriteMessage.add(messageField);
		panelWriteMessage.add(sendButton);
		sendButton.addActionListener(listener);
		this.add(panelWriteMessage,BorderLayout.SOUTH);
	}
	
	private void placerContactPanel() {
		contactPanel.afficherContacts();
		JScrollPane scroll = new JScrollPane(contactPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll,BorderLayout.WEST);
	}
	
	public ConversationPanel getConvPanel() {
		return this.convPanel;
	}

	public void setCurrentContact(String text) {
		currentContact = text;
	}

	public EchoClient getClient() {
		return client;
	}

	public void setClient(EchoClient client) {
		this.client = client;
	}
	
	
}
