//package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadReceiver extends Thread{
	
	private BufferedReader socIn;
	private String pseudoDest;
	
	ThreadReceiver(BufferedReader s,String pseudoDest) {
		this.socIn = s;
		this.pseudoDest=pseudoDest;
	}
	
	public void run() {
	  	  try {
			while (true) {
			  String line = socIn.readLine();
			  //Affiche le message recu
			  System.out.println("From " + pseudoDest +": " + line);
			  System.out.println("To "+pseudoDest+": ");
			  if(EchoClient.finConv) {
				  break;
			  }
			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}
	

}
