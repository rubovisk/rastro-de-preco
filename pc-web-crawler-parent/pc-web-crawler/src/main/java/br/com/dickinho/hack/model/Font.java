package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("font")
public class Font extends ComponentModel{
	public String getPotencia() {
		return potencia;
	}
	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	private String potencia;
	private String tipo;
}
