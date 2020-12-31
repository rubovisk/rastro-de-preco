package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("cooler")
public class Cooler extends ComponentModel {
	private String socket;
	private String led;
	
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	public String getLed() {
		return led;
	}
	public void setLed(String led) {
		this.led = led;
	}
}
