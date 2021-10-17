package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSendThread extends Thread {
	
	private MulticastSocket socket;
	
	public MulticastSendThread(MulticastSocket socket) {
		this.socket=socket;
	}
	
	public void run() {
		try{
			while(true){
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String input = stdIn.readLine();
				byte[] message = (input.getBytes());
				DatagramPacket packet = new DatagramPacket(message, message.length, 
		            InetAddress.getByName("228.5.6.7"), 6789);
		        socket.send(packet);
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
