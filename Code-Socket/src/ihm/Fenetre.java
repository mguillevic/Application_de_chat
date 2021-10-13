import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Fenetre extends JFrame{
	
	private ConversationPanel convPanel = new ConversationPanel();
	
	public Fenetre() {
		this.setTitle("Application Chat");
		this.setSize(700,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.placerConversationPanel();
		
		this.setVisible(true);
	}
	
	private void placerConversationPanel() {
		
		JScrollPane scroll = new JScrollPane(convPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
	
	public ConversationPanel getConvPanel() {
		return this.convPanel;
	}
}
