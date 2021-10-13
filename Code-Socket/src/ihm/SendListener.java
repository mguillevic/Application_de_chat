import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SendListener implements ActionListener{
	private JTextField messageField;
	private JButton sendButton;
	private JTextArea conversationArea;
	
	public SendListener(JTextField jft, JButton button, JTextArea jta) {
		messageField=jft;
		sendButton=button;
		conversationArea=jta;
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sendButton) {
			String message=messageField.getText();
			conversationArea.append(message+"\n\n");
			messageField.setText("");
		}
	}
}
