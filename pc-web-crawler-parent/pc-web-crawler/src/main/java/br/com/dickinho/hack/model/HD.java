package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("hd")
public class HD extends ComponentModel{
	private String tipo;
	private String tamanho;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
}
