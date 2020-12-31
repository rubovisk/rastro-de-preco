package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("memory")
public class Memory extends ComponentModel{
	private String tamanho;
	private String velocidade;
	private String tipo;
	
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(String velocidade) {
		this.velocidade = velocidade;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
