package frontend;

import java.io.IOException;
import java.util.LinkedList;

public class mainTest {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			System.out.println("Usage: java EchoServer <EchoServer ip> <EchoServer port>");
			System.exit(1);
		}
		
		FenetreConnexion f = new FenetreConnexion(args[0],args[1]);
	}
}

