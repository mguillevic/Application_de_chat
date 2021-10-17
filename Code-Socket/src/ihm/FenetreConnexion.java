package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.EchoClient;

public class FenetreConnexion extends JFrame{
	
	private JTextField inputPseudo = new JTextField();
	private JButton connectionButton = new JButton("Connexion");
	private JLabel errorMessage = new JLabel();
	private EchoClient client;
	
	public FenetreConnexion() throws IOException {
		
		this.setTitle("Connexion");
		this.setSize(300,300);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		client = new EchoClient();
		
		
		placerInputPseudo();
		placerBoutonConnexion();
		placerErrorMessage();
		
		this.setVisible(true);
	}

	private void placerErrorMessage() {
		errorMessage.setBounds(getWidth()/2-50,2*getHeight()/3+30,200,50);
		this.add(errorMessage);
	}

	private void placerBoutonConnexion() {
		connectionButton.setBounds(getWidth()/2-50,2*getHeight()/3-30,100,50);
		connectionButton.addActionListener(new ConnexionListener(this));
		this.add(connectionButton);
	}

	private void placerInputPseudo() {
		JLabel label = new JLabel("Entrez pseudo: ");
		label.setBounds(10,getHeight()/3-75,100,50);
		inputPseudo.setBounds(110,getHeight()/3-25,150,50);
		
		this.add(label); this.add(inputPseudo);		
	}
	
	public class ConnexionListener implements ActionListener{
		private FenetreConnexion fenetre;
		
		public ConnexionListener(FenetreConnexion f) {
			fenetre=f;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String pseudo = inputPseudo.getText();
			System.out.println(pseudo);
			
			/*TODO:
			 * envoyer le pseudo au serveur pour vérifier s'il existe (le créer si besoin).
			 * si le pseudo n'existe pas, faire errorMessage.setText("Pseudo incorrect").
			 */
			if(e.getSource()==connectionButton) {
				boolean connexion;
				try {
					connexion = client.connexion(pseudo);
					if(!connexion) {
						errorMessage.setText("Pseudo incorrect");
					}else {
						client.recupererPseudosAmis();
						Fenetre f = new Fenetre(client);
						fenetre.setVisible(false);
						fenetre.dispose();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
			
			
		}
	}
}
