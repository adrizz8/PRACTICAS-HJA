package practica1.model;

public enum Rango {

	AS('A', 14),
    REY('K', 13),
    REINA('Q', 12),
    JOTA('J', 11),
    DIEZ('T', 10),
    NUEVE('9', 9),
    OCHO('8', 8),
    SIETE('7', 7),
    SEIS('6', 6),
    CINCO('5', 5),
    CUATRO('4', 4),
    TRES('3', 3),
    DOS('2', 2);
	
	private final char simbolo; //simbolo respresenta el palo
	private final int valor; //valor representa el valor de la carta
	
	Rango(char s, int v) {
		this.simbolo = s;
		this.valor = v;
	}
	
	public char getSimbolo() {
		return this.simbolo;
	}
	
	public int getValor() {
		return this.valor;
	}
	
	//Convierte el simbolo del rango en el enum correspondiente
	public static Rango desdeSimbolo(char s) {
		for(Rango r : values()) {
			if(r.simbolo == s) {
				return r;
			}
		}
		
		throw new IllegalArgumentException("Rango no valido: " + s);
	}
}
