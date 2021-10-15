//package controller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//package stream;

public class ClientThreadConversation extends Thread{
	
	private Socket clientSocketSender;
	private Socket clientSocketReceiver;
	private String pseudo;
	private String pseudoDestinataire;
	
	ClientThreadConversation(Socket s1, Socket s2,String p, String pseudoD){
		clientSocketSender=s1;
		clientSocketReceiver=s2;
		pseudo = p;
		pseudoDestinataire = pseudoD;
		
	}
	
	
	
	
	//Sauvegarde les message pour le client non connecté
	public void sauvegarderMessage(String message) {
		String fileName = "../../../res/"+pseudoDestinataire+"MessagesRecus.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			String messageSent = pseudo+";"+message;
			writer.write(messageSent+"\r\n");
			writer.close();
		}
		catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
	
	
	public void run() {
		
  	  try {
  		  
  		BufferedReader socIn = null;
		socIn = new BufferedReader(new InputStreamReader(clientSocketSender.getInputStream())); 
		PrintStream socOut=null;
		
		//On verifie que le clientSocket de l'amis receiver n'est pas nul(amis pas connecté)
		if(clientSocketReceiver!=null) {
			socOut = new PrintStream(clientSocketReceiver.getOutputStream());
		}
		
		while (true) {
			
		  //Lecture du message envoyé
		  String[] line = socIn.readLine().split(";");
		  
		  
		  //Si line[0]=Oui, alors le client veut changer de destinataire
		  if(line[0].equals("Oui")) {
			  
			  pseudoDestinataire =line[1];
			  clientSocketReceiver=null;
			  socOut=null;
			  if(EchoServerMultiThreaded.cataloguePseudo.get(pseudoDestinataire).equals("true")) {
				  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
				  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
			  }
			  
			//On précise que le client parle au destinataire choisi et pas un autre
			  EchoServerMultiThreaded.conversations.replace(pseudo,pseudoDestinataire);
			  
		  }
		  
		  
		  //On verifie que le destinataire est connecté et qu'il est aussi en conversation avec le client
		  if(clientSocketReceiver==null && EchoServerMultiThreaded.cataloguePseudo.get(pseudoDestinataire).equals("true") &&  EchoServerMultiThreaded.conversations.get(pseudoDestinataire).equals(pseudo)) {
			  			  
			  //Si le client vient de se connecter on lui attribut une socket
			  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
			  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
			  
	      }else if(clientSocketReceiver!=null &&  EchoServerMultiThreaded.conversations.get(pseudoDestinataire).equals(pseudo)){
	    	  	    	  
	    	//Le destinataire est connecté et le client ne veut pas changer de destinataire : on envoi le message
			  if(line[0].equals("Non")) {
				  socOut.println(line[1]);
			  }
			  
	      }else {
	    	  
	    	  //Le destinataire n'est pas connecté ou en conversation avec un autre client et le client ne veut pas changer de destinataire: on sauvegarde le message
	    	  if(line[0].equals("Non")) {
	    		  sauvegarderMessage(line[1]);
			  }

	      }
		  
		  
		  
		 
		  
		}
  	} catch (Exception e) {
      	System.err.println("Error in EchoServer:" + e); 
      }
     }
}
