import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

//Conteneur pour afficher les conversations des utilisateurs

public class ConversationPanel extends JTextArea {
	private List<String> listeMessages;
	private int textSize=20;
	
	public ConversationPanel() {
		listeMessages = new LinkedList<String>();
		this.setFont(new Font("Sherif",Font.PLAIN,textSize));
		this.setEditable(false);
	}
	
	public void addMessage(String message) {
		this.listeMessages.add(message);
	}
	
	public void setListeMessages(List<String> uneListe) {
		this.listeMessages=uneListe;
	}
	
}//
