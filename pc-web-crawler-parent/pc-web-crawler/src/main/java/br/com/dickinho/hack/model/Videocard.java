package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("videocard")
public class Videocard extends ComponentModel{
	private String intefaceMemoria;
	private String vram;
	private String tipo;
	private String barramento;
	
	public String getIntefaceMemoria() {
		return intefaceMemoria;
	}
	public void setIntefaceMemoria(String intefaceMemoria) {
		this.intefaceMemoria = intefaceMemoria;
	}
	public String getVram() {
		return vram;
	}
	public void setVram(String vram) {
		this.vram = vram;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getBarramento() {
		return barramento;
	}
	public void setBarramento(String barramento) {
		this.barramento = barramento;
	}
	
	
}
