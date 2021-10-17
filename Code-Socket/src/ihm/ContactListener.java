package ihm;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import controller.EchoClient;

//Listener pour changer de conversation lorsqu'on clique sur un des contacts

public class ContactListener implements MouseListener{
	private Fenetre fenetre;
	
	public ContactListener(Fenetre f){
		fenetre=f;
	}
	
	public void recuperationMesssagesRecus(String pseudoContact) throws IOException {
		
		//Recuperation des messages recus
		fenetre.getClient().recupererMessagesRecus(pseudoContact);
		
		//Si le client a recu des messages de la part de son amis on les affiche
		if(fenetre.getClient().messagesReceived.get(pseudoContact).equals("true")) {
			  
			  //Ajouter les textos recus pendant la d�connexion
			  List<String> map = fenetre.getClient().messagesRecus.get(pseudoContact);
			  for(String s : map) {
				  fenetre.getConvPanel().append(s+"\n\n");
			  }
		      fenetre.getClient().messagesReceived.replace(pseudoContact, "false"); 
		      
		      //Que si l'on affiche les messages recu lors de la deconnexion
		     // fenetre.getClient().suppressionMessagesRecu();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO Implementer changement de conversation
		if(e.getSource()==fenetre.getContactPanel().getLabelGroupe()) {
			String message = fenetre.getClient().recupererConversationGroupe();
			try {
				EchoClient.inGroup=true;
				//fenetre.getClient().getTr().wait();
				//fenetre.getClient().getTs().wait();
				fenetre.getClient().rejoindreGroupe();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fenetre.getConvPanel().setText(message);
			
		}else {
			JLabel clickedLabel = (JLabel)e.getSource();  //Label correspondant à un des contacts
			String pseudoContact = clickedLabel.getText();
			try {
				
				
				//Effacer les textos de l'autre conv
				fenetre.getConvPanel().setText("");
	
				EchoClient.pseudoDestinataire=pseudoContact;
				EchoClient.changerConv=true;
				recuperationMesssagesRecus(pseudoContact);
	
				
				
				
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			List<JLabel> listeLabels = fenetre.getContactPanel().getListeLabels();
			for(JLabel label:listeLabels) {
				if(label!=clickedLabel) {
					label.setBackground(null);   //On met tous les contacts en blanc
				}
			}
			clickedLabel.setBackground(Color.BLUE);   //Sauf celui sur qui on a cliqué
			fenetre.setCurrentContact(clickedLabel.getText());
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
