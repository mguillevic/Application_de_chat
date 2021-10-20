package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import backend.EchoClient;
import backend.ThreadSender;

import javax.swing.JLabel;

//Listener pour envoyer un message

public class SendListener implements ActionListener{
	private Fenetre fenetre;
	
	public SendListener(Fenetre f) {
		fenetre=f;
	}
	public void actionPerformed(ActionEvent e) {  
		if(e.getSource()==fenetre.getSendButton()) {
			//Action appelée lorsque l'on appuie sur le bouton envoyer
			//TODO: Ajouter le message à l'historique des conversation, et l'envoyer au destinataire en passant par le serveur
			String message=fenetre.getMessageField().getText();
			fenetre.getServices().setMessageSent(message);
			fenetre.getServices().setAction(true);
			if(!fenetre.getServices().isInGroup()) {
				fenetre.getConvPanel().append("Me: "+message+"\n\n");
				fenetre.getMessageField().setText("");
			}
		}else if(e.getSource()==fenetre.getAddContactButton()) {
			//TODO Ouvrir fenêtre pop-up
			FenetreAddContact popup = new FenetreAddContact(fenetre.getContactPanel());
		}
	}
}//
