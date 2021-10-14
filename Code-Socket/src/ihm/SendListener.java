import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class SendListener implements ActionListener{
	private Fenetre fenetre;
	
	public SendListener(Fenetre f) {
		fenetre=f;
	}
	public void actionPerformed(ActionEvent e) {
		String message=fenetre.getMessageField().getText();
		fenetre.getConvPanel().append("Me: "+message+"\n\n");
		fenetre.getMessageField().setText("");
	}
}//
