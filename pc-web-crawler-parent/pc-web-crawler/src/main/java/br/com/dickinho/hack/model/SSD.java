package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("ssd")
public class SSD extends ComponentModel{
	private String tipo;
	private String leitura;
	private String gravacao;
	private String tamanho;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getLeitura() {
		return leitura;
	}
	public void setLeitura(String leitura) {
		this.leitura = leitura;
	}
	public String getGravacao() {
		return gravacao;
	}
	public void setGravacao(String gravacao) {
		this.gravacao = gravacao;
	}
	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
}
