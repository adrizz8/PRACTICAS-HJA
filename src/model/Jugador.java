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
	
	public void setCarta1(Carta nueva_carta) {
		holeCards= new Pair<Carta>(nueva_carta, holeCards.getSecond());
	}


	public void setCarta2(Carta nueva_carta) {
		holeCards= new Pair<Carta>(holeCards.getFirst(),nueva_carta);
	
	}
}
