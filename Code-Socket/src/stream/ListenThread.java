
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ListenThread extends Thread {
	
	private Socket clientSocket;
	public ListenThread(Socket s) {
		this.clientSocket=s;
	}
	
	public void run() {
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		
    		while(true) {
    			
    			boolean pseudoTrouve=false;
    			
    			//On cherche si le pseudo indiqué comme destinataire existe
    			String pseudoDestinataire="";
    			while(!pseudoTrouve) {
    				pseudoDestinataire = socIn.readLine();
    				pseudoTrouve = EchoServerMultiThreaded.catalogueIP.containsKey(pseudoDestinataire);
    				if(!pseudoTrouve) {
    					socOut.println("NonTrouve");
    				}
    			}
    			socOut.println("Trouve");
    			
    			
    			
    			//On redirige les messages vers le destinataire
    			Socket socketDestinataire = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
    			PrintStream fluxDestination = new PrintStream(socketDestinataire.getOutputStream());
    			String message="";
    			while(!message.equals("EXIT")) {
    				message = socIn.readLine();
    				fluxDestination.println(message);
    			}
    		}
			
		}catch(Exception ex) {
			System.err.println("Exception in ListenThread: "+ex);
		}
	}
}
