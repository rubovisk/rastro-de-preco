package br.com.dickinho.hack.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("processor")
public class Processor extends ComponentModel{
	private String geracao;
	private String velocidade;
	private String socket;
	private String cache;
	
	public String getGeracao() {
		return geracao;
	}
	public void setGeracao(String geracao) {
		this.geracao = geracao;
	}
	public String getVelocidade() {
		return velocidade;
	}
	public void setVelocidade(String velocidade) {
		this.velocidade = velocidade;
	}
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	
	
}
