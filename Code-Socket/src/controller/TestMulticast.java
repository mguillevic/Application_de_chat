package controller;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticast {
	
	public static void recupererConversation(){
		BufferedReader lecteur = null;
		String file = "../../../res/GroupConversation.txt";
	    String ligne;

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		System.out.print(ligne);
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    }catch(IOException ioe){
			ioe.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try{
			
			Service services = new Service();
			services.initialisation();
			
			String pseudo = "";
			boolean connexion =false;
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			while(!connexion) {
				System.out.println("Rentrez votre pseudo : ");
				pseudo = stdIn.readLine();
				connexion = services.connexion(pseudo);
			}
			System.out.println("Bienvenue dans le groupe TEST");
			
			recupererConversation();
			
			MulticastSocket socket = new MulticastSocket(6789);
			socket.joinGroup(InetAddress.getByName("228.5.6.7"));
			MulticastReceiveThread receiveThread = new MulticastReceiveThread(socket);
			MulticastSendThread sendThread = new MulticastSendThread(socket,pseudo);
			
			receiveThread.start();
			sendThread.start();  
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

}
