package Ejercicio1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Clasesini.Carta;
import Clasesini.Mano;
import Clasesini.Palo;

public class ej1 {
	
	public static void main(String[] args) {
        
		String ruta = "src/Ejercicio1/entrada.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
        	while ((linea = br.readLine()) != null) {
                
                
                String l = linea.trim();
                List<Carta> cartas = new ArrayList<>();
            
	            
	            for (int i = 0; i + 1 < l.length(); i += 2) {
            	char valor;
            	char palo;
            	Palo p = null;
    
            	
            	 valor =linea.charAt(i);
            	 palo = linea.charAt(i + 1);
            	 
            	 if (palo == 'h') {
            		 p = Palo.h;
            	 }
            	 
            	 else if (palo == 'd') {
            		 p = Palo.d;
            	 }
            	 
            	 else if (palo == 's') {
            		 p = Palo.s;
            	 }
            	 
            	 else if (palo == 'c') {
            		 p = Palo.c;
            	 }
            	 
            	 Carta c = new Carta(valor,p);
            	
            	cartas.add(c);
            	
            }
            
	        Mano m = new Mano(cartas);
            m.mostrarMano();
            System.out.println("Mejor jugada: " + m.mejorJugada());
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
}
