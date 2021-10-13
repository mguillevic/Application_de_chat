import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ContactPanel extends JPanel {
	private List<String> listeContacts = new LinkedList<String>();
	private List<JLabel> listeLabels = new LinkedList<JLabel>();
	
	private void setListeTest(){
		for(int i=0;i<5;i++){
			listeContacts.add("Contact "+i);
		}
	}
	public void afficherContacts() {
		setListeTest();
		int y=20;
		for(String contact : listeContacts) {
			JLabel label = new JLabel(contact);
			label.setBounds(0,y,50,20);
			y+=30;
			listeLabels.add(label);
			this.add(label);
		}
	}
}
