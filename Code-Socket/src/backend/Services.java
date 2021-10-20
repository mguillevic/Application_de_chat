package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

public class Services {
	
	  /**
	   *  @param client pour gérer le client
	   *  @param ts pour gérer la thread des messages envoyés à un contact
	   *  @param tr pour gérer la thread des messages recus d'un contact
	   *  @param receiveThread pour gérer la thread des messages recus du grouppe
	   *  @param sendThread pour gérer la thread des messages envoyés au grouppe
	   *  @param action pour savoir si le client vient d'envoyer un messages
	   *  @param changerConv pour savoir si le client vient de changer de conversation
	   *  @param contactAjoute pour savoir si le client vient d'ajouter un client
	   *  @param ajouterContact pour savoir si le client veut ajouter un client
	   *  @param nomContactAAjouter pour savoir le nom du contact à ajouter chez le client
	   *  @param inGroup pour savoir si le client est rentré dans le grouppe
	   *  @param ajouterContact pour savoir si le client veut ajouter un client
	   *  @param received pour savoir si le client a recu un message
	   *  @param ajoutContactDest pour savoir si un autre client a ajouté le client à ses contacts
	   *  @param messageSent pour savoir le message envoyé par le client
	   */
	
	//Notre modele: le client
	private EchoClient client;
	
	//Les threads pour les protocoles TCP et UDP
	private ThreadSender ts;
    private ThreadReceiver tr;
    private MulticastReceiveThread receiveThread;
    private MulticastSendThread sendThread;
    
    //Variables de gestions des actions ihm/threads
    private boolean action; 
    private boolean changerConv; 
    private boolean contactAjoute;
    private boolean ajouterContact;
    private String nomContactAAjouter;
    private boolean inGroup;
    private boolean received;
    private boolean ajoutContactDest;
    private String messageSent;
    

	public Services(String ip, String port) throws IOException {
		super();
		
		//Initialisation des variables
		this.client = new EchoClient(ip,port);
		ts = new ThreadSender(client.getSocOut(),this);
  	    tr = new ThreadReceiver(client.getSocIn(),this); 
  	    action = false;
  	    changerConv =false;
  	    contactAjoute= false;
  	    ajouterContact= false;
  	    inGroup=false;
  	    ajoutContactDest=false;
  	    nomContactAAjouter="";
  	    messageSent="";
	}

	//Gestion de la connexion du client
	public boolean connexion(String pseudo) throws IOException {
		boolean connection = false;
		if(pseudo!=null || !pseudo.equals("")) {
			
			//Envoie du pseudo au serveur
			client.getSocOut().println(pseudo); 
			
			//Reponse du serveur : succès ou non de la connexion
			String reponse = client.getSocIn().readLine();
			connection = reponse.equals("true");
			if(connection==true) {
				client.setPseudo(pseudo);
			}
		}
		return connection;
	}
	
	
	//Recuperation des amis du client
	public void recuperationAmis() throws IOException {
		BufferedReader lecteur = null;
		String file = "res/"+client.getPseudo() + "Amis.txt";
	    String ligne;

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		
	    		//Ajout des pseudos dans les hashmap et liste du client
	  	      	client.addAmis(ligne);
	  	      	client.addMessageRecu(ligne, null);
	  	      	replaceMessageReceived(ligne, "false");
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    } 
	}
	
	//Lancement des thread sender et receiver lors du commencement de la premiï¿½re conversation
	public void commencerConversation()throws IOException  {
		tr.start();
		ts.start();
	}
	
	//Ajoute l'ami aux listes et les hashMap si le client n'est pas déjà son ami. Fait une sauvegarde de cet amis
	public boolean ajouterAmis(String pseudo) {
		if(!chercherDansMesAmis(pseudo)) {
			client.addAmis(pseudo);
			client.addMessageRecu(pseudo, null);
			replaceMessageReceived(pseudo, "false");
			sauvegarderAmis(pseudo);
			return true;
		}
		return false;
	}
		
	//Recherche dans les amis actuels du client
	private boolean chercherDansMesAmis(String pseudo) {
		for(String s : client.getAmis()) {
			if(s.equals(pseudo)) {
				return true;
			}
		}
		return false;
	}
	
	//Remplace la valeur de message received s'il a recu un message d'un de ses amis ou pas
	public void replaceMessageReceived(String pseudo, String recus) {
		client.addBooleanMessageReceived(pseudo, recus);
	}
	
	//Persiste les amis du client dans un fichier
	public void sauvegarderAmis(String pseudoAmis) {
		String fileName = "res/"+client.getPseudo()+"Amis.txt";
		String file = "res/"+pseudoAmis+"Amis.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			String ami = pseudoAmis;
			writer.write(ami+"\r\n");
			writer.close();
			
			FileWriter fw = new FileWriter(file,true);
			fw.write(client.getPseudo()+"\r\n");
			fw.close();
		
		}catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
	
	//Retourne la liste des amis du client
	public List<String> recupererAmisClient(){
		return client.getAmis();
	}
	
	//Recuperation des message envoyï¿½s par l'ami avec le pseudoDest
	public void recupererMessagesRecus(String pseudoDest)throws IOException {
		BufferedReader lecteur = null;
		String file ="res/"+ client.getPseudo() + "MessagesRecus.txt";
		List<String> messages = new ArrayList<String>();
	    String ligne;
		   
	    //Verifie que le pseudoDest correspond à un amis du client
	    if(client.getMessagesRecus().containsKey(pseudoDest)) {
	    	
	    	try{
		    	lecteur = new BufferedReader(new FileReader(file));
		    	int i=0;
		    	while ((ligne = lecteur.readLine()) != null) {
		  	      	String [] reponses = ligne.split(";");
		  	      	
		  	      	//Si le client a recu des messages de cet amis on place les messages dans messagesRecus et on indique qu'il en a reï¿½u dans messagesReceived
		  	      	if(reponses[0].equals(pseudoDest)){
		  	      		client.addBooleanMessageReceived(pseudoDest, "true");
		  	      		messages.add(reponses[1]);
		  	      		i++;
		  	      	}
		  	      	
		    	}
		    	
		    	client.addMessageRecu(pseudoDest, messages);
		    	
		  	    lecteur.close();
		    }catch(FileNotFoundException exc){
		    	  System.out.println("Erreur d'ouverture");
		    } 
	    }
	    
	}
	
	//Supprimer du fichier une fois qu'on a recu les messages
	public void suppressionMessagesRecu() {
		try{
		    File file = new File("res/"+client.getPseudo()+"MessagesRecus.txt");
		    PrintWriter printwriter = new PrintWriter(new FileOutputStream(file));
			printwriter.println("");
			    
		}catch(Exception e){
		    e.printStackTrace();
		}
	}
	
	
	//Recuperation de la conversation du grouppe
	public String recupererConversationGroupe(){
		BufferedReader lecteur = null;
		String file = "res/GroupConversation.txt";
	    String ligne;
	    String buffer="";

	    try{
	    	lecteur = new BufferedReader(new FileReader(file));
	    	while ((ligne = lecteur.readLine()) != null) {
	    		buffer+=ligne+"\r\n";
	    	}
	  	    lecteur.close();
	    }catch(FileNotFoundException exc){
	    	  System.out.println("Erreur d'ouverture");
	    }catch(IOException ioe){
			ioe.printStackTrace();
		}
	    return(buffer);
	}
	
	
	//Connexion du client au grouppe
	@SuppressWarnings("deprecation")
	public void rejoindreGroupe() throws IOException {
		client.setGroupSocket(new MulticastSocket(6789));
		client.getGroupSocket().joinGroup(InetAddress.getByName("228.5.6.7"));
		receiveThread=new MulticastReceiveThread(client.getGroupSocket(),this);
		sendThread = new MulticastSendThread(client.getGroupSocket(),client.getPseudo(),this);
		receiveThread.start();
		sendThread.start(); 
	}
	
	public String afficherMessage() {
		if(received) {
			return messageSent;
		}
		return null;
	}
	
	
	
	//Getters et Setters
	
	public String getBooleanMessagesReceived(String pseudoDest) {
		return client.getMessagesReceived().get(pseudoDest);
	}
	
	public List<String> getMessagesRecus(String pseudoDest) {
		return client.getMessagesRecus().get(pseudoDest);
	}
	
	public void setClientContact(String contact) {
		client.setPseudoDestinataire(contact);
	}

	public EchoClient getClient() {
		return client;
	}

	public void setClient(EchoClient client) {
		this.client = client;
	}

	public String getClientContact() {
		return client.getPseudoDestinataire();
	}
	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public boolean isChangerConv() {
		return changerConv;
	}

	public void setChangerConv(boolean changerConv) {
		this.changerConv = changerConv;
	}

	public boolean isContactAjoute() {
		return contactAjoute;
	}

	public void setContactAjoute(boolean contactAjoute) {
		this.contactAjoute = contactAjoute;
	}

	public boolean isAjouterContact() {
		return ajouterContact;
	}

	public void setAjouterContact(boolean ajouterContact) {
		this.ajouterContact = ajouterContact;
	}

	public boolean isInGroup() {
		return inGroup;
	}

	public void setInGroup(boolean inGroup) {
		this.inGroup = inGroup;
	}

	public String getNomContactAAjouter() {
		return nomContactAAjouter;
	}

	public void setNomContactAAjouter(String nomContactAAjouter) {
		this.nomContactAAjouter = nomContactAAjouter;
	}

	public String getMessageSent() {
		return messageSent;
	}

	public void setMessageSent(String messageSent) {
		this.messageSent = messageSent;
	}
	
	
	public boolean isReceived() {
		return received;
	}

	public void setReceived(boolean received) {
		this.received = received;
	}

	public boolean isAjoutContactDest() {
		return ajoutContactDest;
	}

	public void setAjoutContactDest(boolean ajoutContactDest) {
		this.ajoutContactDest = ajoutContactDest;
	}

	
	
	
}
