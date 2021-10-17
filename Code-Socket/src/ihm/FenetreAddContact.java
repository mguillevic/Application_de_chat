package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.EchoClient;

public class FenetreAddContact extends JFrame {
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
		
		validate.setBounds(10,70,50,20);
		validate.setText("Valider contact");
		validate.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED) {
						nom = inputContact.getText();
						EchoClient.ajouterContact=true;
						EchoClient.nomContactAAjouter =nom; 
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
				
				if(EchoClient.contactAjoute) {
					panel.addContact(nom);
					panel.saveContact(nom);
					EchoClient.contactAjoute=false;
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
