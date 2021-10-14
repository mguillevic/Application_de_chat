import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;

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

	private void setListesTest(){
		for(int i=0;i<50;i++){
			String text ="Contact "+i;
			listeContacts.add(text);
			JLabel label = new JLabel(text);
			label.setOpaque(true);
			label.setBackground(null);
			label.addMouseListener(listener);
			listeLabels.add(label);
		}
	}
//
	public void afficherContacts() {
		setListesTest();
		for(JLabel label : listeLabels) {
			preferredVerticalSize+=21;
			this.add(label);
		}
		this.setPreferredSize(new Dimension(100,preferredVerticalSize));
	}
}
