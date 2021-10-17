package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

import ihm.Fenetre;

public class MulticastReceiveThread extends Thread{
	private MulticastSocket socket;
	
	public MulticastReceiveThread(MulticastSocket socket) {
		this.socket=socket;
	}
	
	
	public void run() {
		try{
			while(true) {
				byte[] message = new byte[256];
				DatagramPacket packet = new DatagramPacket(message, message.length);
	        
				// receive the packet
				socket.receive(packet);
				String messageReceived = new String(packet.getData());
				Fenetre.convPanel.afficherMessageRecu(messageReceived);
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
