package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("motherboard")
public class Motherboard extends ComponentModel{
	public String getChipset() {
		return chipset;
	}
	public void setChipset(String chipset) {
		this.chipset = chipset;
	}
	public String getIsDualChannel() {
		return isDualChannel;
	}
	public void setIsDualChannel(String isDualChannel) {
		this.isDualChannel = isDualChannel;
	}

	private String socket;
	private String memoria;
	private String tipo;
	private String chipset;
	private String isDualChannel;
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	public String getMemoria() {
		return memoria;
	}
	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
