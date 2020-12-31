package br.com.dickinho.hack.enu;

public enum TipoComponente {
	MOTHERBOARD(1),
	VIDEOCARD(2),
	PROCESSOR(3),
	CASE(4),
	COOLER(5),
	MEMORY(6),
	HD(7),
	SSD(8),
	FONT(9);
	
	public Integer val;
	
	private TipoComponente(Integer val) {
		this.val = val;
	}
	
	public static TipoComponente getComponentByValue(Integer val) {
		for(TipoComponente e : TipoComponente.values()) {
			if(e.val == val)
				return e;
		}
		return null;
	}
	
	/*public static String getValueByName(String nome) {
		for(TipoECommerce e : TipoECommerce.values()) {
			if(e.name().equals(nome.toUpperCase()))
			return e.val;
		}
		return null;
	}*/
}
