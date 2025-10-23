package model;

import misc.Pair;

public class Jugador {
	
	private Pair<Carta> holeCards;
	private Mano mano;
	private Mesa mesa;
	
	public Jugador(Mesa mesa, Pair<Carta> holeCards) {
		this.mesa = mesa;
		this.holeCards = holeCards;
	}
	
	
	public Pair<Carta> getCartas() {
		return holeCards;
	}
}
