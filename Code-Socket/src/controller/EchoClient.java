package controller;
/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




public class EchoClient {

	
	private Socket echoSocket = null;
	private MulticastSocket groupSocket = null;
    private PrintStream socOut = null;
    private BufferedReader stdIn = null;
    private BufferedReader socIn = null;
    private ThreadSender ts;
    private ThreadReceiver tr;
	private String ipServer;
	private String portServer;
	
	public static String pseudo;
	public static String pseudoDestinataire;
	public static List<String> amis;
	public static String received;
	public static String sent;
	public static boolean action;
	public static boolean changerConv;
	
	public static HashMap<String,String> messagesReceived; //Precise si le client a recu des messages de ses amis
	public static HashMap<String,List<String>> messagesRecus;//Stockage des messages recu des amis
	public static boolean contactAjoute;
	public static boolean ajouterContact;
	public static boolean inGroup;
	public static String nomContactAAjouter;
	
	
	public EchoClient(String ip) throws IOException {
		ipServer = ip;
		portServer = "1234";
		messagesRecus = new HashMap<String,List<String>>();
		messagesReceived = new HashMap<String,String>();
		amis = new ArrayList<String>();
		action=false;
		changerConv=false;
		contactAjoute=false;
		ajouterContact=false;
		inGroup=false;
		nomContactAAjouter="";
		
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
	
	//Connection au serveur avec un pseudo
	public boolean connexion(String pseudo) throws IOException {
		boolean connection = false;
		if(pseudo!=null || !pseudo.equals("")) {
			socOut.println(pseudo); 
			String connexion=socIn.readLine();
			connection = connexion.equals("true");
			if(connection==true) {
				this.pseudo=pseudo;
			}
		}
		return connection;
	}
	
	
	//Choix de l'amis destinataire au pr�s du serveur
	public boolean selectionnerAmis(String pseudoAmis)throws IOException  {
		//Envoi au serveur
    	socOut.println("Oui"+";"+pseudoAmis);
    		
    	//Reponse du serveur
    	boolean reponseServeur = socIn.readLine().equals("true");

    	pseudoDestinataire = pseudoAmis;
    	return reponseServeur;
	}
	
	
	//R�cuperation des amis du client
	public void recupererPseudosAmis() throws IOException{
		BufferedReader lecteur = null;
		String file = "res/"+pseudo + "Amis.txt";
	    String ligne;

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		
	    		//Ajout des pseudos dans les hashmap et liste
	  	      	amis.add(ligne);
	  	      	messagesRecus.put(ligne, null);
	  	      	messagesReceived.put(ligne, "false");
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    } 
	  
	}
	
	//Ajoute l'ami aux listes et les hashMap si le client n'est pas d�ja son ami. Fait une sauvegarde de cet amis
	public static boolean ajouterAmis(String pseudo) {
		if(!chercherDansMesAmis(pseudo)) {
			amis.add(pseudo);
			messagesRecus.put(pseudo,null);
			messagesReceived.put(pseudo, "false");
			sauvegarderAmis(pseudo);
			return true;
		}
		return false;
	}
	
	//Recherche dans les amis actuels du client
	public static boolean chercherDansMesAmis(String pseudo) {
		for(String s : amis) {
			if(s.equals(pseudo)) {
				return true;
			}
		}
		return false;
	}
	
	//Persiste les amis du client dans un fichier
	public static void sauvegarderAmis(String pseudoAmis) {
		String fileName = "res/"+pseudo+"Amis.txt";
		String file = "res/"+pseudoAmis+"Amis.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			String ami = pseudoAmis;
			writer.write(ami+"\r\n");
			writer.close();
			
			FileWriter fw = new FileWriter(file,true);
			fw.write(pseudo+"\r\n");
			fw.close();

		}
		catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
	
	
	//Recuperation des message envoy�s par l'ami avec le pseudoDest
	public static void recupererMessagesRecus(String pseudoDest)throws IOException {
		BufferedReader lecteur = null;
		String file ="res/"+ pseudo + "MessagesRecus.txt";
		
		List<String> messages = null;
	    String ligne;
	   
	    //Verifie que le pseudoDest correspond � un amis du client
	    if(messagesRecus.containsKey(pseudoDest)) {
	    	messagesRecus.replace(pseudoDest,new ArrayList<String>());
	    	try{
		    	lecteur = new BufferedReader(new FileReader(file));
		    	int i=0;
		    	while ((ligne = lecteur.readLine()) != null) {
		  	      	String [] reponses = ligne.split(";");
		  	      	
		  	      	//Si le client a re�u des messages de cet amis on place les messages dans messagesRecus et on indique qu'il en a re�u dans messagesReceived
		  	      	if(reponses[0].equals(pseudoDest)){
		  	      		messagesReceived.replace(pseudoDest,"true");
		  	      		messagesRecus.get(pseudoDest).add(reponses[1]);
		  	      		i++;
		  	      	}
		  	      	
		    	}
		  	    lecteur.close();
		    }catch(FileNotFoundException exc){
		    	  System.out.println("Erreur d'ouverture");
		    } 
	    }
	    
	}
	
	//Supprimer du fichier une fois qu'on a recu les messages
	public void suppressionMessagesRecu() {
		try{

		    File file = new File("res/"+pseudo+"MessagesRecus.txt");
		    PrintWriter printwriter = new PrintWriter(new FileOutputStream(file));
		    printwriter.println("");
		    
		}catch(Exception e){
		      e.printStackTrace();
		}
	}
	
	
	//Lancement des thread sender et receiver lors du commencement de la premi�re conversation
	public void commencerConversation()throws IOException  {
		ts.start();
		tr.start();
	}
	
	public String recupererConversationGroupe(){
		BufferedReader lecteur = null;
		String file = "res/GroupConversation.txt";
	    String ligne;
	    String buffer="";

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		buffer+=ligne;
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    }catch(IOException ioe){
			ioe.printStackTrace();
		}
	    return(buffer);
	}
	
	@SuppressWarnings("deprecation")
	public void rejoindreGroupe() throws IOException {
		groupSocket = new MulticastSocket(6789);
		groupSocket.joinGroup(InetAddress.getByName("228.5.6.7"));
		MulticastReceiveThread receiveThread = new MulticastReceiveThread(groupSocket);
		MulticastSendThread sendThread = new MulticastSendThread(groupSocket,pseudo);
		receiveThread.start();
		sendThread.start(); 
	}

	//A la deconnexion on ferme les flux
	public void endEchoClient() throws IOException {
		socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
	}

	public ThreadSender getTs() {
		return ts;
	}

	public void setTs(ThreadSender ts) {
		this.ts = ts;
	}

	public ThreadReceiver getTr() {
		return tr;
	}

	public void setTr(ThreadReceiver tr) {
		this.tr = tr;
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


