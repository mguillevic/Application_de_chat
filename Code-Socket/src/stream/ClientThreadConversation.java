import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

//package stream;

public class ClientThreadConversation extends Thread{
	
	private Socket clientSocketSender;
	private Socket clientSocketReceiver;
	
	ClientThreadConversation(Socket s1, Socket s2){
		clientSocketSender=s1;
		clientSocketReceiver=s2;
	}
	
	public void run() {
  	  try {
  		BufferedReader socIn = null;
		socIn = new BufferedReader(new InputStreamReader(clientSocketSender.getInputStream()));    
		PrintStream socOut = new PrintStream(clientSocketReceiver.getOutputStream());
		while (true) {
		  String line = socIn.readLine();
		  //Envoi au destinataire le message
		  socOut.println(line);
		}
  	} catch (Exception e) {
      	System.err.println("Error in EchoServer:" + e); 
      }
     }
}
