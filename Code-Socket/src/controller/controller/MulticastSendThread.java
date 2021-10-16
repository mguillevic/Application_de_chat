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
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String input = pseudoSender+": ";
				input+=stdIn.readLine();
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
