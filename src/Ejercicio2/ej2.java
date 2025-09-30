package Ejercicio2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Clasesini.Carta;
import Clasesini.Mano;
import Clasesini.Mesa;
import Clasesini.Palo;

public class ej2 {
	
	public static void main(String[] args) {
        
		String ruta = "src/Ejercicio2/entrada2.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
        	while ((linea = br.readLine()) != null) {
                
                
                String l = linea.trim();
                List<Carta> cartas = new ArrayList<>();
                List<Carta> cartasmesa = new ArrayList<>();
                int numcartas;
                int i = 0;
            
	            
	            while (i < 4) {
            	char valor;
            	char palo;
            	Palo p = null;
    
            	
            	 valor =linea.charAt(i);
            	 palo = linea.charAt(i + 1);
            	 
            	 p=Palo.valueOf(palo+"");
            	 /*
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
            	 */
            	 
            	 Carta c = new Carta(valor,p);
            	
            	cartas.add(c);
            	i+= 2;
	            }
            
	        Mano m = new Mano(cartas);
	        
	        i+=3;
	        
	        
	        for (int j = i; j + 1 < l.length(); j+=2) {
	        	
	        	char valor;
            	char palo;
            	Palo p = null;
    
            	
            	 valor =linea.charAt(j);
            	 palo = linea.charAt(j + 1);
            	 
            	 p=Palo.valueOf(palo+"");
            	 
            	 Carta c = new Carta(valor,p);
             	
             	cartasmesa.add(c);
	        	
	        }
	        
	        Mesa mesa = new Mesa(cartasmesa);
	        
	        m.mostrarMano();
	        
	        System.out.println("\n");
	        
	        mesa.mostrarMesa();
	        
	        
	        System.out.println("\n");
	        
	        String mejor = m.mejorJugadaConMesa(mesa.getMesa());
	        System.out.println(" - Mejor jugada: " + mejor + " con " + m.mostrar_Cartas_Jugada());
	        m.mostrarDraws();
            
            System.out.println("\n");
            
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }
}
