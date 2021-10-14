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
	private String pseudo;
	
	ClientThreadConversation(Socket s1, Socket s2,String p){
		clientSocketSender=s1;
		clientSocketReceiver=s2;
		pseudo = p;
	}
	
	
	//Sauvegarde les message pour le client non connecté
	public void sauvegarderMessage(String message) {
		String fileName = "../../../res/"+ClientThread.pseudoDestinataire+"MessagesRecus.txt";
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
		
		if(clientSocketReceiver!=null) {
			
			socOut = new PrintStream(clientSocketReceiver.getOutputStream());
		}
		
		while (true) {
		  String[] line = socIn.readLine().split(";");
		  
		  //On verifie que le destinataire est connecté
		  if(clientSocketReceiver==null && EchoServerMultiThreaded.cataloguePseudo.get(ClientThread.pseudoDestinataire).equals("true")) {
			  			  
			  
			  //Si le client vient de se connecter
			  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(ClientThread.pseudoDestinataire);
			  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
			  
			  
	      }else if(clientSocketReceiver!=null){
	    	  
	    	//Envoi au destinataire le message
			  if(line[0].equals("Oui")) {
				  			  
				  ClientThread.pseudoDestinataire =line[1];
				  System.out.println(ClientThread.pseudoDestinataire);
				  clientSocketReceiver=null;
				  socOut=null;
				  if(EchoServerMultiThreaded.cataloguePseudo.get(ClientThread.pseudoDestinataire).equals("true")) {
					  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(ClientThread.pseudoDestinataire);
					  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
				  }
				  
				  
			  }else {
				  socOut.println(line[1]);
			  }
			  
	      }else {
	    	  
	    	  //Si le client n'est pas connecté on sauvegarde le message si le client ne veux pas changer de destinataire
	    	  if(line[0].equals("Oui")) {
	    		  
	    		  ClientThread.pseudoDestinataire =line[1];
				  clientSocketReceiver=null;
				  socOut=null;
				  if(EchoServerMultiThreaded.cataloguePseudo.get(ClientThread.pseudoDestinataire).equals("true")) {
					  clientSocketReceiver = EchoServerMultiThreaded.catalogueSocket.get(ClientThread.pseudoDestinataire);
					  socOut = new PrintStream(clientSocketReceiver.getOutputStream());
				  }
				  
				  
			  }else {
				  sauvegarderMessage(line[1]);
			  }
	    	  
	      }
		  
		  
		  
		 
		  
		}
  	} catch (Exception e) {
      	System.err.println("Error in EchoServer:" + e); 
      }
     }
}
