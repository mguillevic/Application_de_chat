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
	
	ThreadReceiver(BufferedReader s) {
		this.socIn = s;
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {
			  
				  //Affichage du message recu
				  String line = socIn.readLine();
				  System.out.println("From " + EchoClient.pseudoDestinataire +": " + line);

			  
			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	

}
