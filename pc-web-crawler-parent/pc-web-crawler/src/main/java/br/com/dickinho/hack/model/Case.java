package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("case")
public class Case extends ComponentModel {

	private String tipo;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
