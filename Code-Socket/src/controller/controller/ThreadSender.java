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
	private String pseudoDest;
	public static String message;
	private BufferedReader stdIn;
	
	ThreadSender(PrintStream s) {
		this.socOut = s;
		stdIn = new BufferedReader(new InputStreamReader(System.in));
		message="";
	}
	
	public void sauvegarderMessage(String message) {
		String fileName = "../../../res/"+pseudoDest+"MessagesRecus.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			String messageSent = EchoClient.pseudo+";"+message;
			writer.write(messageSent+"\r\n");
			writer.close();
		}
		catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
	
	public void run() {
	  	  try {
	  		
			while (true) {
				pseudoDest=EchoClient.pseudoDestinataire;
				if(EchoClient.amisConnecte.get(pseudoDest).equals("false")) {
					
				}else {
					System.out.println("Voulez-vous changer de destinataire ? Oui/Non");
					String reponse = stdIn.readLine();
					if(reponse.equals("Oui")) {
						System.out.println("Rentrer le pseudo : ");
						pseudoDest = stdIn.readLine();
						message=reponse+";"+pseudoDest;
						EchoClient.setPseudoDest(pseudoDest);
						
					}else {
						System.out.println("To "+pseudoDest+": ");
						String messageSent = stdIn.readLine();
						message=reponse+";"+messageSent;
						sauvegarderMessage(messageSent);
					}
	        		socOut.println(message);
				}
				

			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	
	public String getPseudoDest() {
		return pseudoDest;
	}

	public void setPseudoDest(String pseudoDest) {
		this.pseudoDest = pseudoDest;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
