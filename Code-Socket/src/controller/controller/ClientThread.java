//package controller;
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
		
		//Si le client n'est pas d�ja un client, on l'ajoute
		if (!EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			
			//Ajout du client et de sa socket dans le catalogue et pr�cise qu'il est connect�
			EchoServerMultiThreaded.cataloguePseudo.put(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
			ajouterClientAuCatalogue();
			return true;
			
		}else if(EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			
			//Ajout de la socket dans le catalogue et pr�cise que le client est connect�
			EchoServerMultiThreaded.cataloguePseudo.replace(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
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
    		
    		//Reception du pseudo envoy� par le client
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
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		
    		//Reception du pseudo du destinataire
    		pseudoDestinataire = socIn.readLine();
    		
    		//Regarde si l'amis existe dans le catalogue du serveur
    		pseudoExiste= EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudoDestinataire);	
    		
    		//Envoie de la reponse : existence du pseudo ou non
    		socOut.println(pseudoExiste);
    		
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		
		//Si le pseudo n'existe pas on renvoie false
		return pseudoExiste;

	}
	
	
	//Persistence du client dans le catalogue du serveur lors de la connexion
	public void ajouterClientAuCatalogue() {
		String fileName = "../../../res/"+"catalogue.txt";
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
    		
    		//Phase de selection du destinataire
    		boolean pseudoTrouve = false;
        	while (!pseudoTrouve) {
        		pseudoTrouve = pseudoDestinataire();
          	}
        	
        	
        	//Regarde si le destinataire est connect�
        	Socket clientSocketDestinataire = null;
        	if(EchoServerMultiThreaded.cataloguePseudo.get(pseudoDestinataire).equals("true")) {
        		clientSocketDestinataire = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
        		
        	}
        	
        	//On pr�cise que le client parle au destinataire choisi et pas un autre
			EchoServerMultiThreaded.conversations.put(pseudo,pseudoDestinataire);
    		
        	ClientThreadConversation conversation = new ClientThreadConversation(clientSocket,clientSocketDestinataire,pseudo,pseudoDestinataire);
        	conversation.start();
    		
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
      }
	
	
  }