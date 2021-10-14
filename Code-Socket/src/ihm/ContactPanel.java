import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;

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
	
	public void addContact(String nomContact){
		listeContacts.add(nomContact);
		JLabel label = new JLabel(nomContact);
		label.setOpaque(true);
		label.setBackground(null);
		label.addMouseListener(listener);  //Rend les labels cliquables
		listeLabels.add(label);
	}
	
	private void setListesTest(){
		for(int i=0;i<50;i++){
			String text ="Contact "+i;
			addContact(text);
		}
	}
//
	public void afficherContacts() {
		this.removeAll();
		setListesTest();
		for(JLabel label : listeLabels) {
			preferredVerticalSize+=21;  //On ajuste la taille du conteneur en fonction du nombre de contact
			this.add(label);
		}
		this.setPreferredSize(new Dimension(100,preferredVerticalSize));
	}
}
