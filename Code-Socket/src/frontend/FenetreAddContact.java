package frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FenetreAddContact extends JFrame {
	
	/**
	 * @param panel pour l'affichages des contacts
	 * @param inputContact pour ecrire le nom du contact
	 * @param addButton pour ajouter le contact
	 * @param error pour afficher le message d'erreur si le contact n'existe pas
	 * @param validate pour valider le nom du contact a ajouter avant l'ajout
	 * @param nom pour savoir le nom du contact a ajouter
	 */
	
	private ContactPanel panel;
	private JTextField inputContact = new JTextField();
	private JButton addButton = new JButton("ADD");
	private JLabel error = new JLabel();
	private JCheckBox validate = new JCheckBox();
	private String nom="";
	
	public FenetreAddContact(ContactPanel pan) {
		panel=pan;
		this.setTitle("Ajouter Contact");
		this.setSize(400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		validate.setBounds(10,70,200,20);
		validate.setText("Valider contact");
		
		//Envoie au serveur pour qu'il verifie que le contact existe
		validate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
						nom = inputContact.getText();
						panel.getFenetre().getServices().setAjouterContact(true);
						panel.getFenetre().getServices().setNomContactAAjouter(nom) ;
				}
			}    
          });
		
		error.setBounds(10,110,4*getWidth()/5-10,50);
		inputContact.setBounds(10,10,4*getWidth()/5-40,50);
		addButton.setBounds(4*getWidth()/5, 10, getWidth()/5-20, 50);
		addButton.addActionListener(new UpdateContactListener(this));
		
		
		
		this.add(inputContact);
		this.add(addButton);
		this.add(error);
		this.add(validate);
		
		this.setVisible(true);
	}
	
	
	
	public class UpdateContactListener implements ActionListener{
		
		private FenetreAddContact popup;
		public UpdateContactListener(FenetreAddContact f) {
			popup=f;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(inputContact.getText()!="") {
				nom = inputContact.getText();
				panel.revalidate();
				
				//Si le contact existe on affiche le contact chez le client
				if(panel.getFenetre().getServices().isContactAjoute()) {
					panel.addContact(nom);
					panel.saveContact(nom);
					panel.getFenetre().getServices().setContactAjoute(false);
					panel.repaint();
					popup.setVisible(false);
					popup.dispose();
				}else {
					validate.setSelected(false);
					error.setText("Cette personne ne fait pas partie du catalogue serveur");
					panel.repaint();
				}
				
			}
		}
		
	}
}
