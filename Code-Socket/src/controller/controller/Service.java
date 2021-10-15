//package controller;

import java.io.IOException;
import java.util.HashMap;
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
	
	public void recupererAmis() throws IOException {
		echoClient.recupererPseudosAmis();
	}
	
	public boolean selectionnerAmis(String pseudoAmis)throws IOException  {
		return echoClient.selectionnerAmis(pseudoAmis);
	}
	
	public void recupererMessagesRecu(String pseudoDest)throws IOException {
		echoClient.recupererMessagesRecus(pseudoDest);
	}
	
	public void ajouterAmis(String pseudo) {
		echoClient.ajouterAmis(pseudo));
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
	
	public void recupererAmisClient() throws IOException{
		echoClient.recupererPseudosAmis();
	}
	
}
