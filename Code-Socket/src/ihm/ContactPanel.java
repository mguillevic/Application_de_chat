import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;

public class ContactPanel extends JPanel {
	private List<String> listeContacts = new LinkedList<String>();
	private List<JLabel> listeLabels = new LinkedList<JLabel>();
	private int preferredVerticalSize;
	
	public ContactPanel(){
		setBackground(Color.WHITE);
	}
	
	public List<JLabel> getListeLabels() {
		return listeLabels;
	}

	private void setListeTest(){
		for(int i=0;i<50;i++){
			listeContacts.add("Contact "+i);
		}
	}
//
	public void afficherContacts() {
		setListeTest();
		int y=20;
		//SendListener listener = new SendListener
		for(String contact : listeContacts) {
			JLabel label = new JLabel(contact);
			listeLabels.add(label);
			//label.add(
			preferredVerticalSize+=21;
			this.add(label);
		}
		this.setPreferredSize(new Dimension(100,preferredVerticalSize));
	}
}
