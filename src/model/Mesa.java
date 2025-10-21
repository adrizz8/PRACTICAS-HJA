package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Mesa {
	private List<Carta> listaCartas = new ArrayList<Carta>(52);
	
	
	
	public Mesa() {
		Palo[] palos = Palo.values();
		
		for(int i = 0; i < 52; i++) {
			Carta c = new Carta(i % 13 + 2, palos[i % 4]);
			listaCartas.add(c);
		}
		
	}
	
	public Carta getRandom() {
		//Coge una carta aleatoria de la mesa la quita
		Random rand = new Random();
	    Carta c = listaCartas.get(rand.nextInt(listaCartas.size()));
	    listaCartas.remove(c);
	    return c;
	}
	
}

