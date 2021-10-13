import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;

public class ConversationPanel extends JTextArea {
	private List<String> listeMessages;
	private int textSize;
	
	public ConversationPanel() {
		listeMessages = new LinkedList<String>();
		textSize=10;
		this.setEditable(false);
	}
	
	public void addMessage(String message) {
		this.listeMessages.add(message);
	}
	
	public void setListeMessages(List<String> uneListe) {
		this.listeMessages=uneListe;
	}
	
	public void writeMessages(){
		for(String message:listeMessages){
			this.append(message+"\n\n");
		}
	}
}
