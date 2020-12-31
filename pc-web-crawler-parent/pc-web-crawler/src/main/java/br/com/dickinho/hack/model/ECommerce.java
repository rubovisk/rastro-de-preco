package br.com.dickinho.hack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("ecommerce")
public class ECommerce {
 @Id
 private String id;
 private String nome;
 private Double opcao;
 private String elementTag;
 private String formaPagamento;
 private String tipoElemento;
 
public String getTipoElemento() {
	return tipoElemento;
}
public void setTipoElemento(String tipoElemento) {
	this.tipoElemento = tipoElemento;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public Double getOpcao() {
	return opcao;
}
public void setOpcao(Double opcao) {
	this.opcao = opcao;
}
public String getElementTag() {
	return elementTag;
}
public void setElementTag(String elementTag) {
	this.elementTag = elementTag;
}
public String getFormaPagamento() {
	return formaPagamento;
}
public void setFormaPagamento(String formaPagamento) {
	this.formaPagamento = formaPagamento;
}
}
