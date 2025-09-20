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
	
	
	//Este método convierte el char del valor en un valor numérico para compararlo de manera más sencilla
	public Integer getValorNumerico() {
	    switch (valor) {
	        case '2': return 2;
	        case '3': return 3;
	        case '4': return 4;
	        case '5': return 5;
	        case '6': return 6;
	        case '7': return 7;
	        case '8': return 8;
	        case '9': return 9;
	        case 'T': return 10;
	        case 'J': return 11;
	        case 'Q': return 12;
	        case 'K': return 13;
	        case 'A': return 14;
	        default: return -1;
	    }
	}
	
	
}
