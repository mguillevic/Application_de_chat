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
	public String pseudoDestinataire="";
	
	
	ClientThread(Socket s) {
		this.clientSocket = s;
	}
	
	public boolean pseudoExiste(String pseudo, String ip){
		HashMap<String, String> catalogueIP = EchoServerMultiThreaded.catalogueIP;
		HashMap<String, Socket> catalogueSocket = EchoServerMultiThreaded.catalogueSocket;
		HashMap<String, String> cataloguePseudos = EchoServerMultiThreaded.cataloguePseudo;
		
		if (!catalogueIP.containsKey(pseudo)){
			catalogueIP.put(pseudo,ip);
			cataloguePseudos.put(pseudo, "true");
			catalogueSocket.put(pseudo, clientSocket);
			return true;
		}else if(catalogueIP.containsKey(pseudo) && ip.equals(catalogueIP.get(pseudo))){
			cataloguePseudos.replace(pseudo,"true");
			return true;
		}else{
			return false;
		}
	}
	
	public boolean connexion() {
		boolean connexion = false;
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			String[] line = socIn.readLine().split(";");
			String pseudo = line[0];
			String ip = line[1];
			connexion = pseudoExiste(pseudo,ip);
			socOut.println(connexion);
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		return connexion;
	}
	
	public String pseudoDestinataire() {
		String pseudoConnecte="false";
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		pseudoDestinataire = socIn.readLine();
    		
    		//Regarde si l'amis est connecté
    		pseudoConnecte = String.valueOf(EchoServerMultiThreaded.catalogueIP.containsKey(pseudoDestinataire));
    		if(pseudoConnecte.equals("true")) {
    			socOut.println("pseudoConnecte");
    			pseudoConnecte="pseudoConnecte";
    			return pseudoConnecte;
    		}
    		
    		//S'il n'est pas connecté regarde si le pseudo existe
    		pseudoConnecte = String.valueOf(EchoServerMultiThreaded.catalogueIP.containsKey(pseudoDestinataire));
    		if(pseudoConnecte.equals("true")) {
    			socOut.println("pseudoNonConnecte");
    			pseudoConnecte="pseudoNonConnecte";
    			return pseudoConnecte;
    		}
    		
    		    		
    		
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		
		//Si le pseudo n'existe pas on renvoie false
		return pseudoConnecte;

	}
	
	

	public void run() {
    	  try {
    		//Phase de connexion
    		while (!connexion) {
    		  connexion = connexion();
    		}
    		
    		boolean pseudoTrouve = false;
    		String reponse="";
        	while (!pseudoTrouve) {
        		reponse = pseudoDestinataire();
        		pseudoTrouve = !reponse.equals("false");
          	}
        	
        	Socket clientSocketDestinataire = null;
        	if(reponse.equals("pseudoConnecte")) {
        		clientSocketDestinataire = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
        	}
    		
        	ClientThreadConversation conversation = new ClientThreadConversation(clientSocket,clientSocketDestinataire);
        	conversation.start();
        		
    		//Phase de l'envoi ou la reception du message
    		
    		/*//Phase de choix de la conversation
    		ListenThread lt = new ListenThread(clientSocket);
    		lt.start();*/
    		
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
      }
	
	
  }