import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;

public class ContactListener implements MouseListener{
	private Fenetre fenetre;
	
	public ContactListener(Fenetre f){
		fenetre=f;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//TODO Implementer changement de conversation
		JLabel clickedLabel = (JLabel)e.getSource();
		List<JLabel> listeLabels = fenetre.getContactPanel().getListeLabels();
		for(JLabel label:listeLabels) {
			if(label!=clickedLabel) {
				label.setBackground(null);
			}
		}
		clickedLabel.setBackground(Color.BLUE);
		fenetre.setCurrentContact(clickedLabel.getText());
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
