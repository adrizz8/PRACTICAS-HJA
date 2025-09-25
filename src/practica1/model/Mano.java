package practica1.model;

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


/* Royal Flush (escalera real): es la escalera de color correspondiente a las cinco cartas de mayor valor consecutivas (10-J-Q-K-A).
 * Straight Flush (escalera de color): es una combinación de cinco cartas consecutivas del mismo palo.
 * Gutshot (proyecto de escalera): se necesita una carta interior para completar una escalera.
 * Poker: es una combinación que contiene cuatro cartas del mismo valor y una carta cualquiera (kicker)
 * Flush (color): es una combinación de cinco cartas del mismo palo, no necesariamente consecutivas entre sí.
 * Straight (escalera): es una combinación de cinco cartas consecutivas, no necesariamente del mismo palo.
 * Three of a Kind (Trio): está compuesto por tres cartas del mismo valor y otras dos cartas distintas al trío y distintas entre sí.
 * Two pairs (Doble par):  es una mano formada por dos pares distintos y una carta distinta de las anteriores.
 * Pair (par): es una mano formada por dos cartas del mismo valor más tres cartas adicionales de valor distinto de la pareja y distintas entre sí. 
 * High Card (Carta Alta): Cuando no se tiene ninguna combinación de las anteriores se dice que se tiene carta alta */

public class Mano {
		
	private List<Carta> mano; //lista que contiene las cartas de la mano
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
	 
	 //Compruba si la mano es un color
	 public boolean esColor(List<Carta> m) {
		 
		 boolean escolor = true;
		 Palo primerPalo = m.get(0).getPalo();
		 
		 for (Carta c : m) {
			 if (c.getPalo() != primerPalo) { //si alguna carta es distinta no tenemos color
				 escolor = false;
			 }
		 }
		 
		 return escolor;
	 }
	
	 //Comprueba su la mano es escalera (5 cartas consecutivas
	 public boolean esEscalera(List<Carta> m) {
		 	
		 	boolean esescalera = false;
		    Set<Integer> setValores = new HashSet<>();
		   
		 // Guardamos los valores numéricos de las cartas (para ordenarlos y eliminar duplicados)
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

		    // caso especial A,2,3,4,5 (escalera con As bajo)
		    if (setValores.contains(14) && setValores.contains(2) &&
		        setValores.contains(3) && setValores.contains(4) &&
		        setValores.contains(5)) {
		        esescalera = true;
		    }

		    return esescalera;
		}

	 //determina la mejor jugada de la mano
	 public String mejorJugada() {
		 boolean escalera = esEscalera(mano);
		    boolean color = esColor(mano);

		    // Contar frecuencias
		    Map<Integer, Integer> cuenta = new HashMap<>();
		    for (Carta c : mano) {
		        cuenta.put(c.getValorNumerico(), cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		    }

		    //obtenemos el valor que mas se repite comprobando valores
		    int valorClave = cuenta.entrySet().stream().max((a, b) -> Integer.compare(a.getValue(), b.getValue())).get().getKey();

		    //Convertimos los valores del mapa a string para
		    //mostrarlos por pantalla
		    Map<Integer, String> nombres = Map.ofEntries(
		        Map.entry(2, "Twos"), Map.entry(3, "Threes"), Map.entry(4, "Fours"),
		        Map.entry(5, "Fives"), Map.entry(6, "Sixes"), Map.entry(7, "Sevens"),
		        Map.entry(8, "Eights"), Map.entry(9, "Nines"), Map.entry(10, "Tens"),
		        Map.entry(11, "Jacks"), Map.entry(12, "Queens"), Map.entry(13, "Kings"),
		        Map.entry(14, "Aces")
		    );

		    if (escalera && color) return "Straight Flush with " + cartasComoString();
		    else if (cuenta.containsValue(4)) {
		        return "Four of " + nombres.get(valorClave) + " with " + cartasComoString();
		    }
		    else if (cuenta.containsValue(3) && cuenta.containsValue(2)) {
		        return "Full House with " + cartasComoString();
		    }
		    else if (color) return "Flush with " + cartasComoString();
		    else if (escalera) return "Straight with " + cartasComoString();
		    else if (cuenta.containsValue(3)) {
		        return "Three of a kind (" + nombres.get(valorClave) + ") with " + cartasComoString();
		    }
		    else if (Collections.frequency(cuenta.values(), 2) == 2) {
		        return "Two Pair with " + cartasComoString();
		    }
		    else if (cuenta.containsValue(2)) {
		        return "Pair of " + nombres.get(valorClave) + " with " + cartasComoString();
		    }
		    else {
		        int max = Collections.max(cuenta.keySet());
		        return "High Card " + nombres.get(max) + " with " + cartasComoString();
		    }
		 
		 
		 
		 
		    /*boolean escalera = esEscalera(mano);
		    boolean color = esColor(mano);

		    // Contar frecuencias
		    Map<Integer, Integer> cuenta = new HashMap<>();
		    for (Carta c : mano) {
		        cuenta.put(c.getValorNumerico(), cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		    }

		    // Para identificar las cartas implicadas
		    int valorClave = cuenta.entrySet()
		            .stream()
		            .max((a, b) -> Integer.compare(a.getValue(), b.getValue()))
		            .get().getKey();

		    // Mapa para nombres
		    Map<Integer, String> nombres = Map.ofEntries(
		        Map.entry(2, "Twos"), Map.entry(3, "Threes"), Map.entry(4, "Fours"),
		        Map.entry(5, "Fives"), Map.entry(6, "Sixes"), Map.entry(7, "Sevens"),
		        Map.entry(8, "Eights"), Map.entry(9, "Nines"), Map.entry(10, "Tens"),
		        Map.entry(11, "Jacks"), Map.entry(12, "Queens"), Map.entry(13, "Kings"),
		        Map.entry(14, "Aces")
		    );

		    // Jugadas
		    if (escalera && color) return "Straight Flush";
		    else if (cuenta.containsValue(4)) {
		        return "Four of " + nombres.get(valorClave) + " (" + cartasDeValor(valorClave) + ")";
		    }
		    else if (cuenta.containsValue(3) && cuenta.containsValue(2)) {
		        return "Full House (" + cartasDeValor(valorClave) + ")";
		    }
		    else if (color) return "Flush";
		    else if (escalera) return "Straight";
		    else if (cuenta.containsValue(3)) {
		        return "Three of " + nombres.get(valorClave) + " (" + cartasDeValor(valorClave) + ")";
		    }
		    else if (Collections.frequency(cuenta.values(), 2) == 2) {
		        // doble pareja: obtener los dos valores
		        List<Integer> pares = new ArrayList<>();
		        for (var e : cuenta.entrySet()) {
		            if (e.getValue() == 2) pares.add(e.getKey());
		        }
		        Collections.sort(pares, Collections.reverseOrder());
		        return "Two Pair (" + cartasDeValor(pares.get(0)) + " & " + cartasDeValor(pares.get(1)) + ")";
		    }
		    else if (cuenta.containsValue(2)) {
		        return "Pair of " + nombres.get(valorClave) + " (" + cartasDeValor(valorClave) + ")";
		    }
		    else {
		        // carta más alta
		        int max = Collections.max(cuenta.keySet());
		        return "High Card " + nombres.get(max);
		    }*/
		}
	 
	 /*private String cartasDeValor(int valor) {
		    StringBuilder sb = new StringBuilder();
		    for (Carta c : mano) {
		        if (c.getValorNumerico() == valor) {
		            sb.append(c.toString());
		        }
		    }
		    return sb.toString();
		}*/
	 
	/*public String mejorJugada() {
		
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
		
	}*/
	
	
	public List<String> detectarDraws() {
		List<String> draws = new ArrayList<String>();
		
		//Flush draw
		Map<Palo, Integer> contPalos = new HashMap<>();
		for(Carta c : this.mano) {
			contPalos.put(c.getPalo(), contPalos.getOrDefault(c.getPalo(), 0) + 1);
		}
		
		if(contPalos.containsValue(4)) draws.add("Flush draw");
		
		
		//Straight draws
		Set<Integer> setValores = new HashSet<>();
		for (Carta c : this.mano) {
			setValores.add(c.getValorNumerico());
		}
		List<Integer> valores = new ArrayList<Integer>(setValores);
		Collections.sort(valores);
		
		
		//Open-ended y gutShot
		for(int i = 0; i < valores.size() - 3; i++) {
			int primero = valores.get(i);
			int cuarto = valores.get(i + 3);
			
			//open-ended: 4 consecutivos
			if(cuarto - primero == 3) {
				draws.add("Open-ended straight draw");
			}
			else if (cuarto - primero == 4) { //guthsot: hueco de 1
				draws.add("Gutshot straight draw");
			}
		}
		
		
		// Caso especial A234 o TJQK para gutshot
		// Caso especial A234 (faltaría el 5) o TJQK (faltaría el A)
	    if (setValores.containsAll(List.of(14, 2, 3, 4))) {
	        draws.add("Straight Gutshot");
	    }
	    if (setValores.containsAll(List.of(10, 11, 12, 13))) {
	        draws.add("Straight Gutshot");
	    }

	    return draws;
		
	}
	
	
	public String cartasComoString() {
	    StringBuilder sb = new StringBuilder();
	    for (Carta c : mano) {
	        sb.append(c.toString());
	    }
	    return sb.toString();
	}
	
	
	
	
	
	
	
	
	
}