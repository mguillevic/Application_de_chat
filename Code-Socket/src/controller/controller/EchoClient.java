//package controller;
/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;




public class EchoClient {

	
	Socket echoSocket = null;
    PrintStream socOut = null;
    BufferedReader stdIn = null;
    BufferedReader socIn = null;
    ThreadSender ts;
    ThreadReceiver tr;
	String ip;
	String ipServer;
	String portServer;
	Client client;
	public static String pseudo;
	public static String pseudoDestinataire;
	public static boolean messagesReceived=false;
	public static boolean connection=false;
	public static HashMap<String,HashMap<Integer,String>> messagesRecus;
	public static HashMap<String,String> amisConnecte;
	
	public EchoClient() throws IOException {
		client = new Client(InetAddress.getLocalHost().getHostAddress());
		ipServer = "127.0.0.1";
		portServer = "1234";
		messagesRecus = new HashMap<String,HashMap<Integer,String>>();
		amisConnecte = new HashMap<String,String>();
		
		try {
      	    // creation socket ==> connection
      	    echoSocket = new Socket(ipServer,Integer.valueOf(portServer));
      	    socIn = new BufferedReader(
	    		          new InputStreamReader(echoSocket.getInputStream()));    
      	    socOut= new PrintStream(echoSocket.getOutputStream());
      	    stdIn = new BufferedReader(new InputStreamReader(System.in));
      	    ts = new ThreadSender(socOut);
      	    tr = new ThreadReceiver(socIn);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + ipServer);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ ipServer);
            System.exit(1);
        }
		
	}
	
	
	public boolean connexion(String pseudo) throws IOException {
		socOut.println(pseudo+";"+client.getIp()); 
		String connexion=socIn.readLine();
		boolean connection = connexion.equals("true");
		if(connection==true) {
			this.pseudo=pseudo;
			client.setPseudo(pseudo);
			client.setConnexion(true);
			connection=true;
		}
		return connection;
	}
	
	public String selectionnerAmis(String pseudoAmis)throws IOException  {
		//Envoi au serveur
    	socOut.println(pseudoAmis);
    		
    	//Reponse du serveur
    	String reponseServeur = socIn.readLine();
    	pseudoDestinataire = pseudoAmis;
    	return reponseServeur;
	}
	
	public boolean recupererMessagesRecus(String pseudoDest)throws IOException {
		BufferedReader lecteur = null;
		String file ="../../../res/"+ pseudo + "MessagesRecus.txt";
		List<String> messages = null;
	    String ligne;
	   
	    
	    if(messagesRecus.containsKey(pseudoDest)) {
	    	messagesRecus.replace(pseudoDest,new HashMap<Integer,String>());
	    	try{
		    	lecteur = new BufferedReader(new FileReader(file));
		    	int i=0;
		    	while ((ligne = lecteur.readLine()) != null) {
		  	      	String [] reponses = ligne.split(";");
		  	      	if(reponses[0].equals(pseudoDest)){
		  	      		messagesReceived=true;
		  	      		messagesRecus.get(pseudoDest).put(i,reponses[1]);
		  	      		i++;
		  	      	}
		  	      	
		    	}
		  	    lecteur.close();
		    }catch(FileNotFoundException exc){
		    	  System.out.println("Erreur d'ouverture");
		    } 
	    }
	    return messagesReceived;
	}
	
	public void commencerConversation(String pseudoDest)throws IOException  {
		ts.start();
		tr.start();
	}
	
	public boolean terminerConv() {
		if(ts.getMessage().equals("Exit")) {
			System.out.println("aaaa");
			return true;
		}
		return false;
	}
	
	public void recupererPseudosAmis() throws IOException{
		BufferedReader lecteur = null;
		String file = "../../../res/"+pseudo + "Amis.txt";
	    String ligne;

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	  	      	amisConnecte.put(ligne,"false");
	  	      	messagesRecus.put(ligne, null);
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    } 
	  
	}
	
	public static void setPseudoDest(String pseudoDest) {
		pseudoDestinataire=pseudoDest;
	}
	
	public void endEchoClient() throws IOException {
		socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
	}
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    /*public static void main(String[] args) throws IOException {

        
		

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }

        
		
		String line;
		
		//Connexion terminal
		boolean isConnected=false;
		while(!isConnected){
			System.out.print("Entrez votre pseudo: ");
			pseudo=stdIn.readLine();
			socOut.println(pseudo+";"+ip);  //Envoi du pseudo et de l'ip au serveur
			
			//Reponse du serveur
			String connexion=socIn.readLine();
			isConnected = connexion.equals("true");
			if(!isConnected){
				System.out.println("Le pseudo existe déjà, veuillez en choisir un autre");
			}
		}
		System.out.println("Connexion réussie");
		
		
		//Connexion ihm
		boolean isConnected=false;
		while(!isConnected){
			System.out.print("Entrez votre pseudo: ");
			pseudo=stdIn.readLine();
			socOut.println(pseudo+";"+ip);  //Envoi du pseudo et de l'ip au serveur
			
			//Reponse du serveur
			String connexion=socIn.readLine();
			isConnected = connexion.equals("true");
			if(!isConnected){
				System.out.println("Le pseudo existe déjà, veuillez en choisir un autre");
			}
		}
		System.out.println("Connexion réussie");
		
		
		
		//Envoi de message
        while (true) {
        	
        	//Trouver un pseudo correspondant
        	boolean pseudoTrouve=false;
        	String pseudoDest="";
        	while(!pseudoTrouve) {
        		System.out.println("A qui voulez envoyer un message?");
        		System.out.print("pseudo: ");
        		pseudoDest=stdIn.readLine();
        		socOut.println(pseudoDest);
        		
        		//Reponse du serveur
        		pseudoTrouve = socIn.readLine().equals("Trouve");
        	}
        	System.out.println("Vous pouvez discuter avec "+pseudoDest);
        	
        	
        	//Envoi du message
        	ThreadSender st = new ThreadSender(socOut,pseudoDest,stdIn);
        	st.start();
        	//Reception du message
        	ThreadReceiver sr = new ThreadReceiver(socIn,pseudoDest);
        	sr.start();
        	
        	while(!finConv) {
        	}
        	
        	//Quitter application
        	System.out.print("Voulez vous quitter (Y/N): ");
        	String reponse = stdIn.readLine();
        	if(reponse.equals("Y")) {
        		break;
        	}
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }*/
}

