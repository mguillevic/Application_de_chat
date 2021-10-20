package backend;
//package stream;
/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */


import java.io.*;
import java.net.*;
import java.util.HashMap;

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
	
	private String ipServer;
	private String portServer;
	
	
	public static HashMap<String,Socket> catalogueSocket;
	public static HashMap<String,String> cataloguePseudo;
	public static HashMap<String,String> conversations;
	
	public EchoServerMultiThreaded() throws IOException{
		cataloguePseudo = new HashMap<String,String>();
		catalogueSocket = new HashMap<String,Socket>();
		conversations= new HashMap<String,String>();
		recupererClients();
		ipServer = InetAddress.getLocalHost().getHostAddress();
		
	}
	
	
	//Recuperation des clients persist� dans le catalogue du serveur
	public void recupererClients()throws IOException{
		BufferedReader lecteur = null;
		String file ="res/catalogue.txt";
	    String ligne;
	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		cataloguePseudo.put(ligne,"false");
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    } 
	}
	
	
    public static void main(String args[]) throws IOException{ 
	   EchoServerMultiThreaded server = new EchoServerMultiThreaded();
       ServerSocket listenSocket;
       
		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		
		//Création d'un thread pour se connecter à la conversation de groupe et enregistrer
		MulticastSocket groupSocket = new MulticastSocket(6789);
		groupSocket.joinGroup(InetAddress.getByName("228.5.6.7"));
		GroupSaveThread saveThread = new GroupSaveThread(groupSocket);
		saveThread.start();
		
		try {
			listenSocket = new ServerSocket(Integer.valueOf(args[0])); //port
			server.portServer=args[0];
			System.out.println("Server ready..."); 
			
			while (true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				
				ClientThread ct = new ClientThread(clientSocket);
				ct.start();
				
				
			}
		} catch (Exception e) {
			System.err.println("Error in EchoServer:" + e);
		}
	}
}	