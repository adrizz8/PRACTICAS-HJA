package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import misc.Pair;


public class Mesa {
	private final int NUM_JUGADORES = 6;
	private List<Carta> listaCartas = new ArrayList<Carta>(52);
	private List<Jugador> listaJugadores = new ArrayList<Jugador>(6);
	
	
	public Mesa() {
		initCartas();
	}
	
	public Carta getRandom() {
		//Coge una carta aleatoria de la mesa la quita
		Random rand = new Random();
	    Carta c = listaCartas.get(rand.nextInt(listaCartas.size()));
	    listaCartas.remove(c);
	    return c;
	}
	
	public Jugador getJugador(int index) {
		return listaJugadores.get(index);
	}
	
	public int getNumJugadores() {
		return NUM_JUGADORES;
	}
	
	
	private void addJugadorRandom(boolean random) {
		if(random) {
			Pair hCards = new Pair(getRandom(), getRandom());
			listaJugadores.add(new Jugador(this, hCards));
		}
	}
	
	
	private void initCartas() {
		Palo[] palos = Palo.values();
		
		for(int i = 0; i < 52; i++) {
			Carta c = new Carta(i % 13 + 2, palos[i % 4]);
			listaCartas.add(c);
		}
	}
	
	public void initJugadores(boolean isRandom) {
		for(int i = 0; i < NUM_JUGADORES; i++) {
			addJugadorRandom(isRandom);
		}
	}
	
	
}

