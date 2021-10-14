//package controller;
//package stream;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
				
				System.out.println("Voulez-vous changer de destinataire ? Oui/Non");
				String reponse = stdIn.readLine();
				if(reponse.equals("Oui")) {
					System.out.println("Rentrer le pseudo : ");
					EchoClient.pseudoDestinataire = stdIn.readLine();
					message=reponse+";"+EchoClient.pseudoDestinataire;
						
				}else {
					System.out.println("To "+EchoClient.pseudoDestinataire+": ");
					String messageSent = stdIn.readLine();
					message=reponse+";"+messageSent;
				}
				socOut.println(message);

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
