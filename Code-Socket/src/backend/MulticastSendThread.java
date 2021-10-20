package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSendThread extends Thread {
	
	/**
	 * @param socket pour gérer la communication
	 * @param services pour gérer les services
	 * @param pseudoSender pour savoir le pseudo du client ayant envoyé le message
	 */
	
	private MulticastSocket socket;
	private String pseudoSender;
	private Services services;
	
	public MulticastSendThread(MulticastSocket socket, String s,Services services) {
		this.socket=socket;
		this.pseudoSender = s;
		this.services=services;
	}
	
	public void run() {
		try{
			while(true){
				
				
				boolean action = services.isAction();
				System.out.print("");
				
				//Si un client envoie un message et est dans le grouppe on envoie le message
				if(action && services.isInGroup()) {
					
					String input = "From "+pseudoSender+": "+services.getMessageSent();
					byte[] message = input.getBytes();
					
					//Envoie du message au groupe
					DatagramPacket packet = new DatagramPacket(message, message.length, 
			            InetAddress.getByName("228.5.6.7"), 6789);
			        socket.send(packet);
			        
			        services.setAction(false);
				}
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
