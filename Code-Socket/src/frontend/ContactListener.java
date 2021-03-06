package frontend;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;


import javax.swing.JLabel;
//Listener pour changer de conversation lorsqu'on clique sur un des contacts

public class ContactListener implements MouseListener{
	
	/**
	 * @param fenetre pour gerer les interactions
	 */
	private Fenetre fenetre;
	
	public ContactListener(Fenetre f){
		fenetre=f;
	}
	
	public void recuperationMesssagesRecus(String pseudoContact) throws IOException {
		
		//Recuperation des messages recus
		fenetre.getServices().recupererMessagesRecus(pseudoContact);
		
		//Si le client a recu des messages de la part de son amis on les affiche
		if(fenetre.getServices().getBooleanMessagesReceived(pseudoContact).equals("true")) {
			  
			  //Ajouter les textos recus pendant la d�connexion
			  List<String> map = fenetre.getServices().getMessagesRecus(pseudoContact);
			  for(String s : map) {
				  fenetre.getConvPanel().append(s+"\n\n");
			  }
		      fenetre.getServices().replaceMessageReceived(pseudoContact, "false"); 
		      
		      //Que si l'on affiche les messages recu lors de la deconnexion
		     // fenetre.getServices().suppressionMessagesRecu();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO Implementer changement de conversation
		if(e.getSource()==fenetre.getContactPanel().getLabelGroupe()) {
			String message = fenetre.getServices().recupererConversationGroupe();
			
			try {
				fenetre.getServices().setInGroup(true);
				fenetre.getServices().rejoindreGroupe();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fenetre.getConvPanel().setText(message);
			List<JLabel> listeLabels = fenetre.getContactPanel().getListeLabels();
			for(JLabel label:listeLabels) {
				if(label!=fenetre.getContactPanel().getLabelGroupe()) {
					label.setBackground(null);   //On met tous les contacts en blanc
				}
			}
			fenetre.getContactPanel().getLabelGroupe().setBackground(Color.BLUE);
			
		}else {
			JLabel clickedLabel = (JLabel)e.getSource();  //Label correspondant à un des contacts
			String pseudoContact = clickedLabel.getText();
			try {
				
				
				//Effacer les textos de l'autre conv
				fenetre.getConvPanel().setText("");
				if(fenetre.getServices().isInGroup()) {
					fenetre.getServices().setInGroup(false);
				}
				
				fenetre.getServices().setClientContact(pseudoContact);
				fenetre.getServices().setChangerConv(true);
				
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
			fenetre.getContactPanel().getLabelGroupe().setBackground(null);
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
