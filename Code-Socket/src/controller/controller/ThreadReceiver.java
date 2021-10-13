//package controller;
//package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadReceiver extends Thread{
	
	private BufferedReader socIn;
	private String pseudoDest;
	
	ThreadReceiver(BufferedReader s) {
		this.socIn = s;
	}
	
	public void run() {
	  	  try {
	  		
			while (true) {
			  
			  String line = socIn.readLine();
			  pseudoDest=EchoClient.pseudoDestinataire;
			  //Affiche le message recu
			  if(line.equals("Exit")) {
				  
			  }
			  System.out.println("From " + pseudoDest +": " + line);
			  //System.out.println("To "+pseudoDest+": ");
			  
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
	

}
