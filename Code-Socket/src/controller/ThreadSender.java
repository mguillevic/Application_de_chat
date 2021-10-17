package controller;
//package stream;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ihm.SendListener;

public class ThreadSender extends Thread{
	
	private PrintStream socOut;
	private BufferedReader stdIn;
	private String message;
	
	ThreadSender(PrintStream s) {
		this.socOut = s;
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		message="";
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {			
				
				//PARTIE SANS IHM
				//Demande au client s'il veut changer de destinataire
				/*System.out.println("Voulez-vous changer de destinataire ? Oui/Non");
				String reponse = stdIn.readLine();
				
				if(reponse.equals("Oui")) {
					
					//Changement du pseudo du destinataire
					System.out.println("Rentrer le pseudo : ");
					EchoClient.pseudoDestinataire = stdIn.readLine();
					message=reponse+";"+EchoClient.pseudoDestinataire;
					
					//Ajout de l'amis s'ils ne sont pas déja amis
					EchoClient.ajouterAmis(EchoClient.pseudoDestinataire);
					
					//Recuperation des messages recus
					EchoClient.recupererMessagesRecus(EchoClient.pseudoDestinataire);
					
					//Si le client a recu des messages de la part de son amis on les affiche
					if(EchoClient.messagesReceived.get(EchoClient.pseudoDestinataire).equals("true")) {
						  HashMap<Integer,String> map = EchoClient.messagesRecus.get(EchoClient.pseudoDestinataire);
						  Iterator iterator = map.entrySet().iterator();
					        while (iterator.hasNext()) {
					          Map.Entry mapentry = (Map.Entry) iterator.next();
					          System.out.println("From "+EchoClient.pseudoDestinataire + ": " + mapentry.getValue());
					        } 
					        EchoClient.messagesReceived.replace(EchoClient.pseudoDestinataire, "false"); 
					}
						
				}else {
					
					//Recuperation des messages recus
					EchoClient.recupererMessagesRecus(EchoClient.pseudoDestinataire);
					
					//Si le client a recu des messages de la part de son amis on les affiche
					if(EchoClient.messagesReceived.get(EchoClient.pseudoDestinataire).equals("true")) {
						  HashMap<Integer,String> map = EchoClient.messagesRecus.get(EchoClient.pseudoDestinataire);
						  Iterator iterator = map.entrySet().iterator();
					        while (iterator.hasNext()) {
					          Map.Entry mapentry = (Map.Entry) iterator.next();
					          System.out.println("From "+EchoClient.pseudoDestinataire + ": " + mapentry.getValue());
					        } 
					        EchoClient.messagesReceived.replace(EchoClient.pseudoDestinataire, "false"); 
					}
					
					//Envoi du message
					System.out.println("To "+EchoClient.pseudoDestinataire+": ");
					String messageSent = stdIn.readLine();
					message=reponse+";"+messageSent;
				}
				socOut.println(message);
				*/
				
				boolean action = EchoClient.action;
				System.out.print("");
				//PARTIE AVEC IHM
				if(EchoClient.ajouterContact) {
					socOut.println("ajouterContact;"+EchoClient.nomContactAAjouter);
					EchoClient.ajouterContact = false;
				}else if(EchoClient.changerConv) {
					socOut.println("Oui;" + EchoClient.pseudoDestinataire);
					EchoClient.changerConv=false;
				}else if(action) {
					socOut.println("Non;" + EchoClient.sent);
					EchoClient.action=false;
				}
				
				

			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
