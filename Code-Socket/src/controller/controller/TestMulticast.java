import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticast {

	public static void main(String[] args) {
		try{
			MulticastSocket socket = new MulticastSocket(6789);
			socket.joinGroup(InetAddress.getByName("228.5.6.7"));
			MulticastReceiveThread receiveThread = new MulticastReceiveThread(socket);
			MulticastSendThread sendThread = new MulticastSendThread(socket);
			
			receiveThread.start();
			sendThread.start();  
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

}
