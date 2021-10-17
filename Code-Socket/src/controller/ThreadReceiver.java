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

import javax.swing.JLabel;

import ihm.ContactPanel;
import ihm.Fenetre;

public class ThreadReceiver extends Thread{
	
	private BufferedReader socIn;
	
	ThreadReceiver(BufferedReader s) {
		this.socIn = s;
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {
			  
				//Affichage du message recu
				String line = socIn.readLine();
				String [] split = line.split(";");

				
				if(split.length>=2 && split[0].equals("ajouterContact")) {
					
					if(split[1].equals("true")) {
						EchoClient.contactAjoute = true;
					}else if(!split[1].equals("false")) {
						
						Fenetre.contactPanel.addContact(split[1]);
						EchoClient.ajouterAmis(split[1]);					
					}
					
				}else {
					Fenetre.convPanel.afficherMessageRecu("From "+EchoClient.pseudoDestinataire+": "+line);
				}
				  
				  

			  
			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	

}
