package Clasesini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Mano {
		
	private List<Carta> mano;
	private String mejorjugada;
	
	
	public Mano (List<Carta> m) {
		this.mano = m;
	}


	public List<Carta> getMano() {
		return mano;
	}


	public void setMano(List<Carta> mano) {
		this.mano = mano;
	}

	
	 public void mostrarMano() {
	        
		 	System.out.println("Cartas del jugador:");
	        for (Carta c : mano) {
	            System.out.println(c);
	        }
	    }
	 
	 public boolean esColor(List<Carta> m) {
		 
		 boolean escolor = true;
		 
		 Palo primerPalo = m.get(0).getPalo();
		 
		 for (Carta c : m) {
			 
			 if (c.getPalo() != primerPalo) {
				
				 escolor = false;
			 }
		 }
		 
		 return escolor;
	 }
	
	 public boolean esEscalera(List<Carta> m) {
		 	
		 	boolean esescalera = false;
		    Set<Integer> setValores = new HashSet<>();
		   
		    for (Carta c : m) {
		        setValores.add(c.getValorNumerico());
		    }

		    List<Integer> valores = new ArrayList<>(setValores);
		    Collections.sort(valores);

		    //Aqui se comprueba si hay 5 consecutivos
		    for (int i = 0; i <= valores.size() - 5; i++) {
		        int primero = valores.get(i);
		        int ultimo = valores.get(i + 4);
		        if (ultimo - primero == 4) {
		            esescalera = true;
		        }
		    }

		    // caso especial A,2,3,4,5
		    if (setValores.contains(14) && setValores.contains(2) &&
		        setValores.contains(3) && setValores.contains(4) &&
		        setValores.contains(5)) {
		        esescalera = true;
		    }

		    return esescalera;
		}

	
	public String mejorJugada() {
		
		boolean escalera = esEscalera(mano);
		boolean color = esColor(mano);
		
		Map<Integer, Integer> cuenta = new HashMap<>();
		
		for (Carta c : mano) {
		    cuenta.put(c.getValorNumerico(),cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		}
		
		Collection<Integer> valores = cuenta.values();
		
		if (escalera && color) mejorjugada = "Escalera de color";
		else if (valores.contains(4)) mejorjugada = "Poker";
		else if (valores.contains(3) && valores.contains(2)) mejorjugada = "Full House";
		else if (color) mejorjugada = "Color";
		else if (escalera) mejorjugada = "Escalera";
		else if (valores.contains(3)) mejorjugada = "Trío";
		else if (Collections.frequency(valores, 2) == 2) mejorjugada = "Doble par";
		else if (valores.contains(2)) return "Par";
		else mejorjugada = "Carta alta";
		
		
		return mejorjugada;
		
	}
	
	
}
