package backend;
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

	/**
	   *  @param echoSocket pour gérer la communication avec le server
	   *  @param groupSocket pour gérer la communication avec le grouppe
	   *  @param socOut flux de sortie pour envoyer les messages au serveur
	   *  @param socIn pour recevoir les messages
	   *  @param ipServer pour récuperer l'ip du server 
	   *  @param portServer pour recuperer le port du serveur
	   *  @param pseudo du client
	   *  @param pseudoDestinataire pour savoir le pseudo du contact en communication avec le client
	   *  @param amis pour avoir la liste des contacts/amis du client
	   *  @param messagesReceived pour savoir si le client a recu des messages de ses amis
	   *  @param messageRecus pour le stockage des messages recu des amis
	   */
	

	private Socket echoSocket = null;
	private MulticastSocket groupSocket = null;
    private PrintStream socOut = null;
    private BufferedReader socIn = null;
	private String ipServer;
	private String portServer;
	public String pseudo;
	public String pseudoDestinataire;
	public List<String> amis;
	public HashMap<String,String> messagesReceived; 
	public HashMap<String,List<String>> messagesRecus;
	
	
	public EchoClient(String ip, String port) throws IOException {
		
		//Initialisation des variables
		ipServer = ip;
		portServer = port;
		messagesRecus = new HashMap<String,List<String>>();
		messagesReceived = new HashMap<String,String>();
		amis = new ArrayList<String>();
		
		
		try {
      	    // creation socket ==> connection
      	    echoSocket = new Socket(ipServer,Integer.valueOf(portServer));
      	    socIn = new BufferedReader(
	    		          new InputStreamReader(echoSocket.getInputStream()));    
      	    socOut= new PrintStream(echoSocket.getOutputStream());
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + ipServer);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ ipServer);
            System.exit(1);
        }
		
	}
	
	
	
	public void addAmis(String pseudoAmis) {
		amis.add(pseudoAmis);
	}
	
	public void addMessageRecu(String pseudoAmis,List<String> messages) {
		
		if(!messagesRecus.containsKey(pseudoAmis)) {
			messagesRecus.put(pseudoAmis, messages);
		}else {
			messagesRecus.replace(pseudoAmis, messages);
		}
		
	}
	
	public void addBooleanMessageReceived(String pseudoAmis, String recus) {

		if(!messagesReceived.containsKey(pseudoAmis)) {
			messagesReceived.put(pseudoAmis, recus);
		}else {
			messagesReceived.replace(pseudoAmis, recus);
		}
	}
	
	

	//A la deconnexion on ferme les flux
	public void endEchoClient() throws IOException {
		socOut.close();
        socIn.close();
        echoSocket.close();
	}
	

	//Getters and Setters
	public Socket getEchoSocket() {
		return echoSocket;
	}

	public void setEchoSocket(Socket echoSocket) {
		this.echoSocket = echoSocket;
	}

	public PrintStream getSocOut() {
		return socOut;
	}

	public void setSocOut(PrintStream socOut) {
		this.socOut = socOut;
	}

	public BufferedReader getSocIn() {
		return socIn;
	}

	public void setSocIn(BufferedReader socIn) {
		this.socIn = socIn;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<String> getAmis() {
		return amis;
	}

	public void setAmis(List<String> amis) {
		this.amis = amis;
	}

	public HashMap<String, String> getMessagesReceived() {
		return messagesReceived;
	}

	public void setMessagesReceived(HashMap<String, String> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

	public HashMap<String, List<String>> getMessagesRecus() {
		return messagesRecus;
	}

	public void setMessagesRecus(HashMap<String, List<String>> messagesRecus) {
		this.messagesRecus = messagesRecus;
	}

	public MulticastSocket getGroupSocket() {
		return groupSocket;
	}

	public void setGroupSocket(MulticastSocket groupSocket) {
		this.groupSocket = groupSocket;
	}

	
	public String getPseudoDestinataire() {
		return pseudoDestinataire;
	}

	public void setPseudoDestinataire(String pseudoDestinataire) {
		this.pseudoDestinataire = pseudoDestinataire;
	}

	

}


