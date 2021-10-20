package backend;
//package stream;

import java.io.PrintStream;

public class ThreadSender extends Thread{
	
	private PrintStream socOut;
	private String message;
	private Services services;
	private boolean alreadySent=false;
	
	ThreadSender(PrintStream s,Services services) {
		this.socOut = s;
		message="";
		this.services=services;
	}
	
	
	
	public void run() {
	  	  try {
	  		
			while (true) {			
				
				boolean action =services.isAction();
				System.out.print("");
	
				//Si le client veut ajouter un contact, on envoie son nom au serveur
				if(services.isAjouterContact()) {
					socOut.println("ajouterContact;"+services.getNomContactAAjouter());
					services.setAjouterContact(false);
					
				//Si le client veut changer de conversation, on envoie le nom du prochain contact au serveur
				}else if(services.isChangerConv()) {
					socOut.println("Oui;" + services.getClientContact());
					services.setChangerConv(false);
					alreadySent = false;
					
				//Si le client est dans le groupe, on l'envoie au serveur
				}else if(services.isInGroup()) {
					if(!alreadySent) {
						socOut.println("inGroup;"+"Groupe");
						alreadySent=true;
					}
					
				//Le client envoie un message
				}else if(action) {
					socOut.println("Non;" + services.getMessageSent());
					services.setAction(false);;
				}
				
				

			}
	  	} catch (Exception e) {
	      	System.err.println("Error in EchoServer:" + e); 
	      }
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
