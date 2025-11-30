package model;

public class Carta {
	
	
	private char valor;
	private Palo palo;
	
	public Carta() {
		this.valor='0';
		this.palo=Palo.h;
	}
	
	public Carta(char v, Palo p) {
		this.valor = v;
		this.palo = p;
	}
	
	public Carta(int v, Palo p) {
		setValor(v);
		this.palo = p;
	}
	
	public Carta(String string) {
		this.valor = string.charAt(0);
		this.palo = Palo.valueOf(string.substring(1));
	}
	
	public char getValor() {
		return valor;
	}

	public void setValor(char valor) {
		this.valor = valor;
	}
	
	//Sobrecarga de setValor()
	public void setValor(int valor) {
			switch(valor) {
			case 2 : this.valor = '2'; break;
			case 3 : this.valor = '3'; break;
			case 4 : this.valor = '4'; break;
			case 5 : this.valor = '5'; break;
			case 6 : this.valor = '6'; break;
			case 7 : this.valor = '7'; break;
			case 8 : this.valor = '8'; break;
			case 9 : this.valor = '9'; break;
			case 10 : this.valor = 'T'; break;
			case 11 : this.valor = 'J'; break;
			case 12 : this.valor = 'Q'; break;
			case 13 : this.valor = 'K'; break;
			case 14 : this.valor = 'A'; break;
		
		}
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
