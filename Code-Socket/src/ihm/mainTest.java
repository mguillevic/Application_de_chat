package ihm;

import java.io.IOException;
import java.util.LinkedList;

public class mainTest {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 1) {
	          System.out.println("Usage: java EchoClient <EchoServer host>");
	          System.exit(1);
	    }
		FenetreConnexion f = new FenetreConnexion(args[0]);
		//~ f.getConvPanel().setListeMessages(messages);
	}
}
//
