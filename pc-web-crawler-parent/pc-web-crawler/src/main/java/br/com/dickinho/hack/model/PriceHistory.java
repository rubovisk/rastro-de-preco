package br.com.dickinho.hack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("price_history")
public class PriceHistory {
	@Id
	private String id;
	private String marca;
	private String modelo;
	private String ecommerce;
	private String link;
	private String preco;
	private String dataRegistro;
	private String tipoProduto;
	
	public String getTipoProduto() {
		return tipoProduto;
	}
	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getEcommerce() {
		return ecommerce;
	}
	public void setEcommerce(String ecommerce) {
		this.ecommerce = ecommerce;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}
	public String getDataRegistro() {
		return dataRegistro;
	}
	public void setDataRegistro(String dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
}
