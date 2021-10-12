/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */

import java.io.*;
import java.net.*;



public class EchoClient {

	public static boolean finConv = false;
	
  /**
  *  main method
  *  accepts a connection, receives a message from client then sends an echo to the client
  **/
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;
		String ip = InetAddress.getLocalHost().getHostAddress();
		String pseudo;
		

        if (args.length != 2) {
          System.out.println("Usage: java EchoClient <EchoServer host> <EchoServer port>");
          System.exit(1);
        }

        try {
      	    // creation socket ==> connection
      	    echoSocket = new Socket(args[0], Integer.valueOf(args[1]));
      	    socIn = new BufferedReader(
	    		          new InputStreamReader(echoSocket.getInputStream()));    
      	    socOut= new PrintStream(echoSocket.getOutputStream());
      	    stdIn = new BufferedReader(new InputStreamReader(System.in));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + args[0]);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ args[0]);
            System.exit(1);
        }
		
		String line;
		
		//Connexion
		boolean isConnected=false;
		while(!isConnected){
			System.out.print("Entrez votre pseudo: ");
			pseudo=stdIn.readLine();
			socOut.println(pseudo+";"+ip);  //Envoi du pseudo et de l'ip au serveur
			
			//Reponse du serveur
			String connexion=socIn.readLine();
			isConnected = connexion.equals("true");
			if(!isConnected){
				System.out.println("Le pseudo existe déjà, veuillez en choisir un autre");
			}
		}
		System.out.println("Connexion réussie");
		
		
		
		//Envoi de message
        while (true) {
        	
        	//Trouver un pseudo correspondant
        	boolean pseudoTrouve=false;
        	String pseudoDest="";
        	while(!pseudoTrouve) {
        		System.out.println("A qui voulez envoyer un message?");
        		System.out.print("pseudo: ");
        		pseudoDest=stdIn.readLine();
        		socOut.println(pseudoDest);
        		
        		//Reponse du serveur
        		pseudoTrouve = socIn.readLine().equals("Trouve");
        	}
        	System.out.println("Vous pouvez discuter avec "+pseudoDest);
        	
        	
        	//Envoi du message
        	ThreadSender st = new ThreadSender(socOut,pseudoDest,stdIn);
        	st.start();
        	//Reception du message
        	ThreadReceiver sr = new ThreadReceiver(socIn,pseudoDest);
        	sr.start();
        	
        	while(!finConv) {
        	}
        	
        	//Quitter application
        	System.out.print("Voulez vous quitter (Y/N): ");
        	String reponse = stdIn.readLine();
        	if(reponse.equals("Y")) {
        		break;
        	}
        }
        socOut.close();
        socIn.close();
        stdIn.close();
        echoSocket.close();
    }
}


