package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

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
				System.out.println(new String(packet.getData()));
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
