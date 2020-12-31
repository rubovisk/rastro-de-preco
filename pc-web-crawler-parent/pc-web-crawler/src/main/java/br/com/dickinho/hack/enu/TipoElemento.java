package br.com.dickinho.hack.enu;

public enum TipoElemento {
	ID("id"),
	CLASS("class");
	
	public String val;
	
	private TipoElemento(String val) {
		this.val = val;
	}
	
	public String toString() {
		return val;
	}
}
