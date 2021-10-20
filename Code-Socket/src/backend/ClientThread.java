package backend;
/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ClientThread
	extends Thread {
	
	private Socket clientSocket;
	private Boolean connexion = false;
	public static String pseudoDestinataire="";
	public static String pseudo="";
	
	
	ClientThread(Socket s) {
		this.clientSocket = s;
	}
	
	//Ajout de la socket client dans le catalogue du serveurs
	public boolean pseudoExiste(String pseudo){
		
		//Si le client n'est pas déja un client, on l'ajoute
		if (!EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			
			//Ajout du client et de sa socket dans le catalogue et précise qu'il est connecté
			EchoServerMultiThreaded.cataloguePseudo.put(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
			EchoServerMultiThreaded.conversations.put(pseudo,"false");
			ajouterClientAuCatalogue();
			return true;
			
		}else if(EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			
			//Ajout de la socket dans le catalogue et précise que le client est connecté
			EchoServerMultiThreaded.cataloguePseudo.replace(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
			EchoServerMultiThreaded.conversations.put(pseudo,"false");
			return true;
			
		}else{
			return false;
		}
	}
	
	//Connexion au serveur
	public boolean connexion() {
		boolean connexion = false;
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		
    		//Reception du pseudo envoyé par le client
			String line = socIn.readLine();
			pseudo = line;
			
			//Ajout de la socket client dans les catalogues du serveurs
			connexion = pseudoExiste(pseudo);
			
			//Envoie de la reponse au client
			socOut.println(connexion);
			
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		return connexion;
	}
	
	
	//Verfie si le destinataire existe dans le catalogue du serveur
	public boolean pseudoDestinataire() {
		boolean pseudoExiste=false;
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		
    		String [] line = socIn.readLine().split(";");
    		//Reception du pseudo du destinataire
    		pseudoDestinataire = line[1];
    		
    		//Regarde si l'amis existe dans le catalogue du serveur
    		pseudoExiste= EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudoDestinataire);	
    		
    		
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		
		//Si le pseudo n'existe pas on renvoie false
		return pseudoExiste;

	}
	
	
	//Persistence du client dans le catalogue du serveur lors de la connexion
	public void ajouterClientAuCatalogue() {
		String fileName = "res/"+"catalogue.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			String messageSent = pseudo;
			writer.write(messageSent+"\r\n");
			writer.close();
		}
		catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
	
	

	public void run() {
    	  try {
    		//Phase de connexion
    		while (!connexion) {
    		  connexion = connexion();
    		}
    		
    		
        	
        	
        	//Regarde si le destinataire est connecté
        	Socket clientSocketDestinataire = null;
    		
        	ClientThreadConversation conversation = new ClientThreadConversation(clientSocket,clientSocketDestinataire,pseudo,pseudoDestinataire);
        	conversation.start();
    		
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
      }
	
	
  }