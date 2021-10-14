//package controller;
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

public class ThreadReceiver extends Thread{
	
	private BufferedReader socIn;
	private String pseudoDest;
	
	ThreadReceiver(BufferedReader s) {
		this.socIn = s;
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {
			pseudoDest=EchoClient.pseudoDestinataire;
			  if(EchoClient.messagesReceived==true) {
				  HashMap<Integer,String> map = EchoClient.messagesRecus.get(pseudoDest);
				  Iterator iterator = map.entrySet().iterator();
			        while (iterator.hasNext()) {
			          Map.Entry mapentry = (Map.Entry) iterator.next();
			          System.out.println("From "+pseudoDest + ": " + mapentry.getValue());
			        } 
			        EchoClient.messagesReceived=false; 
			  }else {
				  String line = socIn.readLine();
				  
				  //Affiche le message recu
				  if(line.equals("Exit")) {
					  
				  }
				  System.out.println("From " + pseudoDest +": " + line);
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
	

}
