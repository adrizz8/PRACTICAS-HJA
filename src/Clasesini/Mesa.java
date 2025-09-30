package Clasesini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Mesa {
		
	private List<Carta> mesa;
	
	public Mesa (List<Carta> m) {
		
		//Ordenamos por numero de carta para ahorrar en un futuro
		m.sort( Comparator.comparingInt((Carta c) -> c.getValorNumerico()));
		                      
		this.mesa = m;
	}


	public List<Carta> getMesa() {
		return mesa;
	}


	public void setMano(List<Carta> mesa) {
		this.mesa = mesa;
	}

	
	 public void mostrarMesa() {
	        
		 	System.out.print("Cartas de la mesa: ");
	        for (Carta c : mesa) {
	            System.out.print(c);
	        }
	        System.out.println();
	    }
	 
	
}
