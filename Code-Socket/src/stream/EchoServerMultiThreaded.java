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
	
	public static HashMap<String,String> catalogueIP;
	public static HashMap<String,Socket> catalogueSocket;
	
	public EchoServerMultiThreaded(){
		catalogueIP = new HashMap<String,String>();
		catalogueSocket = new HashMap<String,Socket>();
	}
	
	
    public static void main(String args[]){ 
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