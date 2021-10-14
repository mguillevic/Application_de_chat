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
		if(e.getSource()==fenetre.getSendButton()) {
			String message=fenetre.getMessageField().getText();
			fenetre.getConvPanel().append(message+"\n\n");
			fenetre.getMessageField().setText("");
		}
		else if(fenetre.getContactPanel().getListeLabels().contains(e.getSource())){
			//Implémenter service pour changer de conversation
			JLabel clickedLabel = (JLabel)e.getSource();
			fenetre.getConvPanel().append("Pseudo: "+clickedLabel.getText()+"\n\n");
		}
	}
}//
