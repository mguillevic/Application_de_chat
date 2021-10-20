package backend;
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

import javax.swing.JLabel;

import frontend.ContactPanel;
import frontend.Fenetre;

public class ThreadReceiver extends Thread{
	
	/**
	 * @param socIn recevoir les messages du serveur
	 * @param services pour gérer les services
	 */
	
	private BufferedReader socIn;
	private Services services;
	
	ThreadReceiver(BufferedReader s,Services c) {
		this.socIn = s;
		this.services=c;
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {
			  
				//Recuperation du message
				String line = socIn.readLine();
				String [] split = line.split(";");

				//Le client veut ajouter un contact
				if(split.length>=2 && split[0].equals("ajouterContact")) {
					
					//Le contact à ajouter existe
					if(split[1].equals("true")) {
						services.setContactAjoute(true);
					}else if(!split[1].equals("false")) {
						
						//Split[1] est egal au pseudo du client ayant ajouté le contact, on gère l'ajout du nom du client chez le contact
						services.setNomContactAAjouter(split[1]);
						services.setAjoutContactDest(true);
						services.ajouterAmis(split[1]);					
					}
					
				//Le client n'est pas dans le grouppe et à recu un messsage, on l'affiche
				}else if(!services.isInGroup()) {
					services.setReceived(true);
					services.setMessageSent("From "+services.getClientContact()+": "+line);
				}
				  
				  

			  
			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	

}
