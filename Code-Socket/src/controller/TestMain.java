package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestMain {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Service services = new Service();
		services.initialisation();
		
		
		
		String pseudo = "";
		boolean connexion =false;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		while(!connexion) {
			System.out.println("Rentrez votre pseudo : ");
			pseudo = stdIn.readLine();
			connexion = services.connexion(pseudo);
		}
		System.out.println(connexion);
		
		services.recupererAmisClient();
		
		String pseudoDest="";
		boolean pseudoAmis = false;
		while(pseudoAmis==false) {
			System.out.println("A qui voulez vous parlez : ");
			pseudoDest = stdIn.readLine();
			pseudoAmis = services.selectionnerAmis(pseudoDest);
		}
		
		System.out.println(pseudoAmis);
		services.ajouterAmis(pseudoDest);
		
		
		
		services.recupererMessagesRecu(pseudoDest);
		services.commencerConversation(pseudoDest);
		
		
		/*boolean changerConv = false;
		while(!changerConv) {
			changerConv=services.terminerConversation();
		}
		pseudoAmis =false;
		while(!pseudoAmis) {
			pseudoAmis = services.selectionnerAmis(pseudoDest);
		}
		System.out.println("A qui voulez vous parlez : ");
		pseudoDest = stdIn.readLine();
		System.out.println(pseudoAmis);*/
	
	}
	
	/*public String conversation(Service services,BufferedReader stdIn) throws IOException {
		String pseudoDest="";
		boolean pseudoAmis =false;
		while(!pseudoAmis) {
			System.out.println("A qui voulez vous parlez : ");
			pseudoDest = stdIn.readLine();
			pseudoAmis = services.selectionnerAmis(pseudoDest);
		}
		System.out.println(pseudoAmis);
		
		services.commencerConversation(pseudoDest);
		return pseudoDest;
	}*/

}
