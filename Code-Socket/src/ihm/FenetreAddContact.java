import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class FenetreAddContact extends JFrame {
	private ContactPanel panel;
	private JTextField inputContact = new JTextField();
	private JButton addButton = new JButton("ADD");
	
	public FenetreAddContact(ContactPanel pan) {
		panel=pan;
		this.setTitle("Ajouter Contact");
		this.setSize(400,80);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		inputContact.setBounds(0,0,4*getWidth()/5-20,getHeight()-10);
		addButton.setBounds(4*getWidth()/5, 0, getWidth()/5-20, getHeight()-10);
		
		this.add(inputContact);
		this.add(addButton);
		
		this.setVisible(true);
	}
}
