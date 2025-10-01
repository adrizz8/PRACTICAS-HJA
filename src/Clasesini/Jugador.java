package Clasesini;

import java.util.Comparator;

public class Jugador {
	
	private String id;
	private Mano _mano;
	private String mejorJugada;
	
	public Jugador(String id, Mano m) {
		this.id = id;
		this._mano = m;
		mejorJugada = m.mejorJugada();
	}
	
	public String getId() {
		return this.id;
	}
	
	public Mano getMano() {
		return this._mano;
	}
	

	public int getValorMano() {
		return _mano.getValor();
	}
	
	public String getMejorJugada() {
		return this.mejorJugada;
	}
}		
