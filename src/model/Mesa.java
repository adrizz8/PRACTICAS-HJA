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
		Pair hCards;
		if(random) {
			hCards = new Pair(getRandom(), getRandom());
			listaJugadores.add(new Jugador(this, hCards));
		}else {
			hCards= new Pair(new Carta(), new Carta());	
			listaJugadores.add(new Jugador(this, hCards));
		}
	}
	
	
	private void initCartas() {
		Palo[] palos = Palo.values();
		
		for (int p = 0; p < 4; p++) {            
		    for (int v = 2; v <= 14; v++) {     
		        Carta c = new Carta(v, palos[p]);
		        listaCartas.add(c);
		    }
		}
	}
	
	public void initJugadores(boolean isRandom) {
		for(int i = 0; i < NUM_JUGADORES; i++) {
			addJugadorRandom(isRandom);
		}
	}

	public List<Carta> getlistaCartas() {
		// TODO Auto-generated method stub
		return listaCartas;
	}

	public void modificarJugador(int jugador, int i, String nueva_ruta, String vieja_ruta) {
		// TODO Auto-generated method stub
		Carta nueva_carta=CartaAleatoria.eleccionCartaAleatoria(nueva_ruta);
	
		String texto_nueva_carta=nueva_carta.toString();
		for(int j=0;j<listaCartas.size();j++) {
			if(listaCartas.get(j).toString().equalsIgnoreCase(texto_nueva_carta))
				listaCartas.remove(j);
		}
		if(!"0h.png".equalsIgnoreCase(vieja_ruta))
			listaCartas.add(CartaAleatoria.eleccionCartaAleatoria(vieja_ruta));
		if(i==0)
			listaJugadores.get(jugador).setCarta1(nueva_carta);
		else
			listaJugadores.get(jugador).setCarta2(nueva_carta);
	}
	
	public List<Jugador> getListaJugadores(){
		return listaJugadores;
	}
	
	
}

