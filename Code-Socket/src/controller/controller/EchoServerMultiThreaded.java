//package controller;
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
import java.util.List;

public class EchoServerMultiThreaded  {
  
 	/**
  	* main method
	* @param EchoServer port
  	* 
  	**/
	
	public static String ipServer;
	public static String portServer;
	
	public static HashMap<String,String> catalogueIP;
	public static HashMap<String,Socket> catalogueSocket;
	public static HashMap<String,String> cataloguePseudo;
	
	public EchoServerMultiThreaded() throws IOException{
		catalogueIP = new HashMap<String,String>();
		catalogueSocket = new HashMap<String,Socket>();
		ipServer = InetAddress.getLocalHost().getHostAddress();
		portServer = "1234";
		cataloguePseudo = recupererClients();
	}
	

	public HashMap<String,String> recupererClients()throws IOException{
		BufferedReader lecteur = null;
		String file ="../../../res/catalogue.txt";
		HashMap<String,String> clients = new HashMap<String,String>();
	    String ligne;
	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	  	      	clients.put(ligne,"false");
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    } 
	    return clients;
	}
	
    public static void main(String args[]) throws IOException{ 
	   EchoServerMultiThreaded server = new EchoServerMultiThreaded();
       ServerSocket listenSocket;
       
		if (args.length != 1) {
			System.out.println("Usage: java EchoServer <EchoServer port>");
			System.exit(1);
		}
		try {
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
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