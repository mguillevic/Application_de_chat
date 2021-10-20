package frontend;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import backend.Services;

import javax.swing.JButton;

//FenÃªtre principale de l'application

public class Fenetre extends JFrame{
	
	/**
	 * @param messageField pour ecrire les messages
	 * @param sendButton pour envoyer les messages
	 * @param addContactButton pour ajouter des contacts
	 * @param panelWriteMessage pour la gestion des composants responsables de l'envoi des messages
	 * @param contactPanel pour gérer l'affichage des contacts
	 * @param convPanel pour gérer l'affichage de la conversation
	 * @param services pour l'interaction front/backend
	 */
	
	private JTextField messageField = new JTextField(20);
	private JButton sendButton = new JButton("SEND");
	private JButton addContactButton = new JButton("+ New Contact");
	private JPanel panelWriteMessage = new JPanel();
	public ContactPanel contactPanel;
	public ConversationPanel convPanel;
	private Services services;
	
	private String currentContact="";
	
	public Fenetre(Services s) throws IOException {
		
		this.setTitle("Application Chat");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		contactPanel = new ContactPanel(this);
		
		
		services=s;
		convPanel = new ConversationPanel(services);
		s.commencerConversation();
		
		
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
	
	//Gestion de l'affichage des messages avec scroll
	private void placerConversationPanel() {
		
		JScrollPane scroll = new JScrollPane(convPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}

	public String getCurrentContact() {
		return currentContact;
	}

	//Placement de la zone d'envoi des messages
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
	
	//Placement et gestion de l'affichage des contacts avec scroll
	private void placerContactPanel() {
		contactPanel.afficherContacts();
		JScrollPane scroll = new JScrollPane(contactPanel);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll,BorderLayout.WEST);
	}
	
	//Getters and setters
	public ConversationPanel getConvPanel() {
		return this.convPanel;
	}

	public void setCurrentContact(String text) {
		currentContact = text;
	}

	public Services getServices() {
		return services;
	}

	public void setServices(Services services) {
		this.services = services;
	}

	
	
	
}
