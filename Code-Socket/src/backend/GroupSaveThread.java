package backend;

import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;


public class GroupSaveThread extends Thread {
	private MulticastSocket serverSocket;
	
	public GroupSaveThread(MulticastSocket socket) {
		serverSocket=socket;
	}
	public void run() {
		try{
			
			while(true) {
				byte[] message = new byte[256];
				DatagramPacket packet = new DatagramPacket(message, message.length);
	        
				// receive the packet
				serverSocket.receive(packet);
				
				String messageReceived = new String(packet.getData());
				sauvegarderMessage(messageReceived);
				
				
			}
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	private void sauvegarderMessage(String messageToSave) {
		String fileName = "res/GroupConversation.txt";
		try {
			FileWriter writer = new FileWriter(fileName,true);
			writer.write(messageToSave+"\r\n");
			writer.close();
		}
		catch(IOException ioe){
			 System.err.println(ioe.getMessage());
		}
	}
}
