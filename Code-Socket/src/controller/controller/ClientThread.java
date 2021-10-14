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
	
	public boolean pseudoExiste(String pseudo){
		
		if (!EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			EchoServerMultiThreaded.cataloguePseudo.put(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
			ajouterClientAuCatalogue();
			return true;
		}else if(EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudo)){
			EchoServerMultiThreaded.cataloguePseudo.replace(pseudo,"true");
			EchoServerMultiThreaded.catalogueSocket.put(pseudo, clientSocket);
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
			String line = socIn.readLine();
			pseudo = line;
			connexion = pseudoExiste(pseudo);
			socOut.println(connexion);
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		return connexion;
	}
	
	public boolean pseudoDestinataire() {
		boolean pseudoExiste=false;
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		pseudoDestinataire = socIn.readLine();
    		
    		//Regarde si l'amis existe
    		pseudoExiste= EchoServerMultiThreaded.cataloguePseudo.containsKey(pseudoDestinataire);	
    		socOut.println(pseudoExiste);
    		
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		
		//Si le pseudo n'existe pas on renvoie false
		return pseudoExiste;

	}
	
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
    		
    		boolean pseudoTrouve = false;
        	while (!pseudoTrouve) {
        		pseudoTrouve = pseudoDestinataire();
          	}
        	
        	//Regarde si le destinataire est connecté
        	Socket clientSocketDestinataire = null;
        	if(EchoServerMultiThreaded.cataloguePseudo.get(pseudoDestinataire).equals("true")) {
        		clientSocketDestinataire = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
        		
        	}
    		System.out.println(pseudo);
        	ClientThreadConversation conversation = new ClientThreadConversation(clientSocket,clientSocketDestinataire,pseudo);
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