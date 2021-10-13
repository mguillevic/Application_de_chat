import java.util.LinkedList;

public class mainTest {

	public static void main(String[] args) {
		String m1="Bonjour";
		String m2="Ca va?";
		String m3="Bien et toi?";
		
		LinkedList<String> messages = new LinkedList<String>();
		for(int i=0;i<20;i++){
			messages.add(m1); messages.add(m2); messages.add(m3);
		}
		
		Fenetre f = new Fenetre();
		f.getConvPanel().setListeMessages(messages);
		f.getConvPanel().writeMessages();
	}

}
