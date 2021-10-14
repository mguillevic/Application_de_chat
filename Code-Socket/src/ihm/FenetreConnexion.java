import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class FenetreConnexion extends JFrame{
	private JTextField inputPseudo = new JTextField();
	private JButton connectionButton = new JButton("Connexion");
	private JLabel errorMessage = new JLabel();
	
	public FenetreConnexion() {
		this.setTitle("Connexion");
		this.setSize(300,300);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
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
			Fenetre f = new Fenetre();
			fenetre.setVisible(false);
			fenetre.dispose();
			
		}
	}
}
