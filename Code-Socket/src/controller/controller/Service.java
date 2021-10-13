//package controller;

import java.io.IOException;
import java.util.List;

public class Service {
	
	private EchoClient echoClient;
	
	public Service() {
		
	}
	
	public void initialisation() throws IOException {
		echoClient = new EchoClient();
	}
	
	public boolean connexion(String pseudo) throws IOException {
		return echoClient.connexion(pseudo);
	}
	
	public List<String> recupererAmis() throws IOException {
		return echoClient.recupererPseudosAmis();
	}
	
	public boolean selectionnerAmis(String pseudoAmis)throws IOException  {
		return echoClient.selectionnerAmis(pseudoAmis);
	}
	
	public void commencerConversation(String pseudoDest)throws IOException  {
		echoClient.commencerConversation(pseudoDest);
	}
	
	public boolean terminerConversation()throws IOException  {
		return echoClient.terminerConv();
	}
	
	public String recupererPseudoClient(String ip) {
		return null;
	}
	
	public List<String> recupererAmisClient(String pseudo) {
		return null;
	}
	
}
