//package stream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadSender extends Thread{
	
	private PrintStream socOut;
	private String pseudoDest;
	private BufferedReader stdIn;
	
	ThreadSender(PrintStream s, String pseudoDest,BufferedReader b) {
		this.socOut = s;
		this.pseudoDest = pseudoDest;
		stdIn=b;
	}
	
	public void run() {
	  	  try {
			while (true) {
				System.out.println("To "+pseudoDest+": ");
        		String message = stdIn.readLine();
        		if(message.equals("Exit")) {
        			EchoClient.finConv=true;
        			break;
        		}
        	    socOut.println(message);

			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

}
