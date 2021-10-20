package backend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


public class MulticastReceiveThread extends Thread{
	
	/**
	 * @param socket pour g�rer la communication
	 * @param services pour g�rer les services
	 */
	
	private MulticastSocket socket;
	private Services services;
	
	public MulticastReceiveThread(MulticastSocket socket,Services services) {
		this.socket=socket;
		this.services=services;
	}
	
	
	public void run() {
		try{
			while(true) {
				byte[] message = new byte[256];
				DatagramPacket packet = new DatagramPacket(message, message.length);
	        
				// receive the packet
				boolean isInGroupe = services.isInGroup();
				if(isInGroupe) {
					
					socket.receive(packet);
					String messageReceived = new String(packet.getData());
					if(services.isInGroup()) {
						services.setReceived(true);
						services.setMessageSent(messageReceived);
					}
					
					
				}
				
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
