package ihm;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;

import controller.EchoClient;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Conteneur pour afficher la liste de tous les contacts de l'utilisateur

public class ContactPanel extends JPanel {
	private Fenetre fenetre;
	ContactListener listener;
	private List<String> listeContacts = new LinkedList<String>();
	private List<JLabel> listeLabels = new LinkedList<JLabel>();
	private int preferredVerticalSize;
	
	public ContactPanel(Fenetre f){
		fenetre=f;
		listener = new ContactListener(fenetre);
		setBackground(Color.WHITE);
	}
	
	public List<JLabel> getListeLabels() {
		return listeLabels;
	}
	
	/*public void ajouterContactListe(String contact) {
		listeContacts.add(contact);
		listeLabels.add(new JLabel(contact));
		this.add(listeLabels.get(listeLabels.size()-1));
		this.revalidate();
	}*/
	
	public boolean isContactInList(String contact) {
		for(String s : listeContacts) {
			if(s.equals(contact)) {
				return true;
			}
		}
		return false;
	}
	
	public void addContact(String nomContact){
		if(!isContactInList(nomContact)) {
			listeContacts.add(nomContact);
			JLabel label = new JLabel(nomContact);
			label.setOpaque(true);
			label.setBackground(null);
			label.addMouseListener(listener);  //Rend les labels cliquables
			listeLabels.add(label);
			this.add(label);
			this.revalidate();
		}
		
	}
	
	
	public void saveContact(String nomContact) {
		fenetre.getClient().ajouterAmis(nomContact);
	}
	
	private void setListesTest(){
		
		//Parcours des contacts du client et ajout dans les listes de contacts
		for(String s : fenetre.getClient().amis){
			addContact(s);
		}
	}

	public  void afficherContacts() {
		setListesTest();
		for(JLabel label : listeLabels) {
			preferredVerticalSize+=21;  //On ajuste la taille du conteneur en fonction du nombre de contact
			this.add(label);
		}
		this.setPreferredSize(new Dimension(50,preferredVerticalSize));
	}
	
	
}
