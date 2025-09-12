package Clasesini;

public class Carta {
	
	
	private char valor;
	private Palo palo;
	
	public Carta(char v, Palo p) {
		this.valor = v;
		this.palo = p;
	}

	public char getValor() {
		return valor;
	}

	public void setValor(char valor) {
		this.valor = valor;
	}

	public Palo getPalo() {
		return palo;
	}

	public void setPalo(Palo palo) {
		this.palo = palo;
	}
	
	@Override
	public String toString() {
	    return valor + "" + palo;
	}
	
	
}
