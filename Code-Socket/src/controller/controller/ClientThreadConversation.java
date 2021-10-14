//package controller;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
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
		PrintStream socOut=null;
		if(clientSocketReceiver!=null) {
			socOut = new PrintStream(clientSocketReceiver.getOutputStream());
		}
		while (true) {
		  String[] line = socIn.readLine().split(";");
		  //Envoi au destinataire le message
		  if(clientSocketReceiver!=null) {
			  if(line[0].equals("Oui")) {
				  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(line[1]);
				  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
			  }else {
				  
				  socOut.println(line[1]);
			  }
		  }
		 
		  
		}
  	} catch (Exception e) {
      	System.err.println("Error in EchoServer:" + e); 
      }
     }
}
