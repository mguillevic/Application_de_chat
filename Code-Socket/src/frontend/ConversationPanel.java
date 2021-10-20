package frontend;

import java.awt.Color;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.Timer;

import backend.Services;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Conteneur pour afficher les conversations des utilisateurs

public class ConversationPanel extends JTextArea implements ActionListener{
	
	/**
	 * @param listeMessages pour stocker la liste des messages recus
	 * @param texteSize pour gérer la taille des JLabels
	 * @param services pour l'interaction front/backend
	 * @param timer pour gérer la mise à jour de l'affichage
	 */
	
	private List<String> listeMessages;
	private int textSize=20;
	private Timer timer;
	private Services services;
	
	public ConversationPanel(Services services) {
		
		//Initialisation des variables
		listeMessages = new LinkedList<String>();
		this.setFont(new Font("Sherif",Font.PLAIN,textSize));
		this.setEditable(false);
		
		timer = new Timer(1,this);
		timer.start();
		this.services=services;
	}
	
	//Ajoute un message à la liste
	public void addMessage(String message) {
		this.listeMessages.add(message);
	}
	
	public void setListeMessages(List<String> uneListe) {
		this.listeMessages=uneListe;
	}
	
	
	//Afiche le message recu
	public void afficherMessageRecu(String message) {
		this.append(message+"\n\n");
	}
	
	public void actionPerformed(ActionEvent e) {  
		if(e.getSource()==timer) {
			if(services.isReceived()) {
				
				//MAJ de l'affichage lorsque un message est recu
				afficherMessageRecu(services.afficherMessage());
				services.setReceived(false);
			}
		}
	}
	
	
}//
