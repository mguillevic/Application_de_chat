package controller;

import java.util.List;



public class Client {

	private String pseudo;
	private String ip;
	private boolean connexion;
	private List<String> amis;
	
	public Client(String i) {
		ip=i;
		connexion=false;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isConnexion() {
		return connexion;
	}

	public void setConnexion(boolean connexion) {
		this.connexion = connexion;
	}

	public List<String> getAmis() {
		return amis;
	}

	public void setAmis(List<String> amis) {
		this.amis = amis;
	}
	
	
}

