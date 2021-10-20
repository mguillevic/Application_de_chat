package frontend;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.Timer;

import backend.EchoClient;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//Conteneur pour afficher la liste de tous les contacts de l'utilisateur

public class ContactPanel extends JPanel implements ActionListener {
	
	/**
	 * @param fenetre pour gérer les interactions
	 * @param listener pour gérer les interactions d'ajout des contacts
	 * @param listeContacts pour gérer la liste des contacts
	 * @param listeLabels pour gérer l'affichage de la liste des contacts
	 * @param labelGroupe pour gérer l'affichage du Grouppe
	 * @param preferredVerticalSize pour gérer la hauteur du panel avec les contacts
	 * @param timer pour gérer la mise à jour de l'affichage
	 */
	
	private Fenetre fenetre;
	private ContactListener listener;
	private List<String> listeContacts = new LinkedList<String>();
	private List<JLabel> listeLabels = new LinkedList<JLabel>();
	private JLabel labelGroupe = new JLabel("Groupe");
	private int preferredVerticalSize;
	private Timer timer;
	
	public ContactPanel(Fenetre f){
		
		//Initialisation des variables
		fenetre=f;
		listener = new ContactListener(fenetre);
		setBackground(Color.WHITE);
		labelGroupe.setOpaque(true);
		labelGroupe.setBackground(null);
		labelGroupe.addMouseListener(listener);
		timer = new Timer(1,this);
		timer.start();
		this.add(labelGroupe);
	}
	
	//Verification de la presence du contact dans la liste des contacts du client
	public boolean isContactInList(String contact) {
		for(String s : listeContacts) {
			if(s.equals(contact)) {
				return true;
			}
		}
		return false;
	}
	
	//Ajout d'un client dans la liste du contact et MAJ de l'affichage
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
	
	//Sauvegarde le contact dans les amis du client
	public void saveContact(String nomContact) {
		fenetre.getServices().ajouterAmis(nomContact);
	}
	
	private void setListesTest(){
		
		//Parcours des contacts du client et ajout dans les listes de contacts
		for(String s : fenetre.getServices().recupererAmisClient()){
			addContact(s);
		}
	}

	//Maj de l'affichage des contacts
	public  void afficherContacts() {
		setListesTest();
		for(JLabel label : listeLabels) {
			preferredVerticalSize+=21;  //On ajuste la taille du conteneur en fonction du nombre de contact
			this.add(label);
		}
		this.setPreferredSize(new Dimension(50,preferredVerticalSize));
	}
	
	
	public void actionPerformed(ActionEvent e) {  
		if(e.getSource()==timer) {
			
			//Maj de l'affichage lorsque un autre client ajoute le client à ses contacts
			if(fenetre.getServices().isAjoutContactDest()) {
				addContact(fenetre.getServices().getNomContactAAjouter());
				fenetre.getServices().setAjoutContactDest(false);
			}
		}
	}

	
	//Getters et Setters
	
	public Fenetre getFenetre() {
		return fenetre;
	}

	public void setFenetre(Fenetre fenetre) {
		this.fenetre = fenetre;
	}
	public List<JLabel> getListeLabels() {
		return listeLabels;
	}
	
	
	public JLabel getLabelGroupe() {
		return labelGroupe;
	}

	public void setLabelGroupe(JLabel labelGroupe) {
		this.labelGroupe = labelGroupe;
	}

	
	
	
}
