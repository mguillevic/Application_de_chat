/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */


import java.io.*;
import java.net.*;
import java.util.HashMap;


public class ClientThread
	extends Thread {
	
	private Socket clientSocket;
	private Boolean connexion = false;
	private String pseudoDestinataire="";
	
	ClientThread(Socket s) {
		this.clientSocket = s;
	}
	
	public boolean pseudoExiste(String pseudo, String ip){
		HashMap<String, String> catalogueIP = EchoServerMultiThreaded.catalogueIP;
		HashMap<String, Socket> catalogueSocket = EchoServerMultiThreaded.catalogueSocket;
		
		if (!catalogueIP.containsKey(pseudo)){
			catalogueIP.put(pseudo,ip);
			catalogueSocket.put(pseudo, clientSocket);
			return true;
		}else if(catalogueIP.containsKey(pseudo) && ip.equals(catalogueIP.get(pseudo))){
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
	
	public boolean pseudoDestinataire() {
		boolean pseudoTrouve=false;
		try {
			BufferedReader socIn = null;
    		socIn = new BufferedReader(
    			new InputStreamReader(clientSocket.getInputStream()));    
    		PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
    		
    		
    		//On cherche si le pseudo indiqué comme destinataire existe
    		while(!pseudoTrouve) {
    			pseudoDestinataire = socIn.readLine();
    			pseudoTrouve = EchoServerMultiThreaded.catalogueIP.containsKey(pseudoDestinataire);
    			if(!pseudoTrouve) {
    				socOut.println("NonTrouve");
    			}
    		}
    		socOut.println("Trouve");
    		
		}catch(IOException ex) {
			System.err.println("Error in ConnexionThread: "+ex);
		}
		
		return pseudoTrouve;

	}
	

	public void run() {
    	  try {
    		//Phase de connexion
    		while (!connexion) {
    		  connexion = connexion();
    		}
    		
    		
    		//Phase du choix du pseudo
    		boolean pseudoTrouve = false;
    		while (!pseudoTrouve) {
    			pseudoTrouve = pseudoDestinataire();
      		}
    		
    		//Phase de l'envoi ou la reception du message
    		Socket clientSocketDestinataire = EchoServerMultiThreaded.catalogueSocket.get(pseudoDestinataire);
    		ClientThreadConversation conversation = new ClientThreadConversation(clientSocket,clientSocketDestinataire);
    		conversation.start();
    		
    		/*
    		//Phase de choix de la conversation
    		ListenThread lt = new ListenThread(clientSocket);
    		lt.start();*/
    		
    	} catch (Exception e) {
        	System.err.println("Error in EchoServer:" + e); 
        }
      }
	
	
  }