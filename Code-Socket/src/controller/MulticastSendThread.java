package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSendThread extends Thread {
	
	private MulticastSocket socket;
	private String pseudoSender;
	
	public MulticastSendThread(MulticastSocket socket, String s) {
		this.socket=socket;
		this.pseudoSender = s;
	}
	
	public void run() {
		try{
			while(true){
				
				System.out.println("");
				if(EchoClient.action) {
					String input = "From "+pseudoSender+": "+EchoClient.sent;
					System.out.println(input);
					byte[] message = input.getBytes();
					DatagramPacket packet = new DatagramPacket(message, message.length, 
			            InetAddress.getByName("228.5.6.7"), 6789);
			        socket.send(packet);
			        EchoClient.action=false;
				}
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
}
