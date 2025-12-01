package model;

import java.util.*;
import misc.Pair;


public class OutsCalculator {
    
	// Calula los outs del hero contra una mano específica del villano
    public static double calcularOuts(Pair heroCards, Pair villanoCards, 
                                     List<Carta> board, List<Carta> cartasUsadas) {
        

        List<Carta> todasLasCartas = crearBarajaCompleta();
        
        // Eliminamos las cartas que ya están en juego
        todasLasCartas.removeAll(cartasUsadas);
        
        // Ahora todasLasCartas contiene las 44 cartas que pueden salir en el River
        int outsQueGanan = 0;
        int outsQueEmpatan = 0;
        
        // Se prueba cada carta posible en la fase del River
        for (Carta cartaRiver : todasLasCartas) {
            
        	//Creamos el board completo con la carta del river
            List<Carta> boardCompleto = new ArrayList<>(board);
            boardCompleto.add(cartaRiver);
            
            // Calculamos la mejor mano del hero
            List<Carta> combinacionHero = new ArrayList<>(boardCompleto);
            combinacionHero.add((Carta) heroCards.getFirst());
            combinacionHero.add((Carta) heroCards.getSecond());
            Mano manoHero = new Mano(combinacionHero);
            manoHero.mejorJugada();
            
            // Calculamos la mejor mano del villano
            List<Carta> combinacionVillano = new ArrayList<>(boardCompleto);
            combinacionVillano.add((Carta) villanoCards.getFirst());
            combinacionVillano.add((Carta) villanoCards.getSecond());
            Mano manoVillano = new Mano(combinacionVillano);
            manoVillano.mejorJugada();
            
            // Comparamos manos
            int valorHero = manoHero.valor_jugada();
            int valorVillano = manoVillano.valor_jugada();
            
            if (valorHero > valorVillano) {
                outsQueGanan++;
            } else if (valorHero == valorVillano) {
                outsQueEmpatan++;
            }
        }
        
        // Los empates los contamos como 0.5 outs
        return outsQueGanan + (outsQueEmpatan * 0.5);
    }
    
   
     // Calcula la media de outs contra todas las manos posibles del rango del villano
    public static double calcularMediaOuts(Pair heroCards, List<String> rangoVillano,
                                          List<Carta> board, List<Carta> barajaDisponible) {
        

        List<Carta> cartasUsadas = new ArrayList<>();
        cartasUsadas.add((Carta) heroCards.getFirst());
        cartasUsadas.add((Carta) heroCards.getSecond());
        cartasUsadas.addAll(board);
        
        // Generamos todas las manos posibles del rango del villano
        List<Pair> manosVillano = generarManosDelRango(rangoVillano, cartasUsadas);
        
        if (manosVillano.isEmpty()) {
            return 0.0;
        }
        
        // Calculamos outs para cada mano posible del villano
        double sumaOuts = 0.0;
        
        for (Pair manoVillano : manosVillano) {
            // Añadimos las cartas del villano a las cartas usadas
            List<Carta> cartasUsadasConVillano = new ArrayList<>(cartasUsadas);
            cartasUsadasConVillano.add((Carta) manoVillano.getFirst());
            cartasUsadasConVillano.add((Carta) manoVillano.getSecond());
            
            // Calculamos los outs contra esta mano del villano
            double outs = calcularOuts(heroCards, manoVillano, board, cartasUsadasConVillano);
            sumaOuts += outs;
        }
        
        // Retornar la media
        return sumaOuts / manosVillano.size();
    }
    
 //Generamos todas las manos posibles del rango del villano
    private static List<Pair> generarManosDelRango(List<String> rangoVillano, 
                                                   List<Carta> cartasUsadas) {
        List<Pair> manosGeneradas = new ArrayList<>();
        List<Carta> barajaCompleta = crearBarajaCompleta();
        
        // Eliminamos las cartas que ya están en uso
        barajaCompleta.removeAll(cartasUsadas);
        
        for (String rango : rangoVillano) {
            rango = rango.trim();
            
            // Procesamos cada tipo de rango
            if (rango.matches("([AKQJT2-9])\\1")) {
                // Es una pareja (ej: "AA", "KK")
                char valor = rango.charAt(0);
                manosGeneradas.addAll(generarParejas(valor, barajaCompleta));
                
            } else if (rango.length() >= 2) {
                // Es una mano no pareada (ej: "AKs", "AKo", "AK")
                char valor1 = rango.charAt(0);
                char valor2 = rango.charAt(1);
                boolean suited = rango.contains("s");
                boolean offsuit = rango.contains("o");
                
                if (suited) {
                    manosGeneradas.addAll(generarManosSuited(valor1, valor2, barajaCompleta));
                } else if (offsuit) {
                    manosGeneradas.addAll(generarManosOffsuit(valor1, valor2, barajaCompleta));
                } else {
                    // Si no especifica, incluye ambas
                    manosGeneradas.addAll(generarManosSuited(valor1, valor2, barajaCompleta));
                    manosGeneradas.addAll(generarManosOffsuit(valor1, valor2, barajaCompleta));
                }
            }
        }
        
        return manosGeneradas;
    }
    
    
    // Genera todas las parejas posibles de un valor dado
    private static List<Pair> generarParejas(char valor, List<Carta> barajaDisponible) {
        List<Pair> parejas = new ArrayList<>();
        List<Carta> cartasDelValor = new ArrayList<>();
        
        //Encontramos todas las cartas del valor dado
        for (Carta c : barajaDisponible) {
            if (c.getValor() == valor) {
                cartasDelValor.add(c);
            }
        }
        
        // Generamos todas las combinacions de parejas posibles
        for (int i = 0; i < cartasDelValor.size(); i++) {
            for (int j = i + 1; j < cartasDelValor.size(); j++) {
                parejas.add(new Pair(cartasDelValor.get(i), cartasDelValor.get(j)));
            }
        }
        
        return parejas;
    }
    
    // Genera todas las manos del mismo palo
    private static List<Pair> generarManosSuited(char valor1, char valor2, 
                                                 List<Carta> barajaDisponible) {
        List<Pair> manos = new ArrayList<>();
        

        for (Palo palo : Palo.values()) {
            Carta carta1 = null;
            Carta carta2 = null;
            
            
            for (Carta c : barajaDisponible) {
                if (c.getValor() == valor1 && c.getPalo() == palo) {
                    carta1 = c;
                }
                if (c.getValor() == valor2 && c.getPalo() == palo) {
                    carta2 = c;
                }
            }
            
            //Al encontrar ambas cartas, creamos una mano suited
            if (carta1 != null && carta2 != null) {
                manos.add(new Pair(carta1, carta2));
            }
        }
        
        return manos;
    }
    
    //Genera todas las manos posibles de diferente palo
    private static List<Pair> generarManosOffsuit(char valor1, char valor2, 
                                                  List<Carta> barajaDisponible) {
        List<Pair> manos = new ArrayList<>();
        List<Carta> cartas1 = new ArrayList<>();
        List<Carta> cartas2 = new ArrayList<>();
        
        // Encontramos todas las cartas de cada valor
        for (Carta c : barajaDisponible) {
            if (c.getValor() == valor1) {
                cartas1.add(c);
            }
            if (c.getValor() == valor2) {
                cartas2.add(c);
            }
        }
        
        // Combinamos las cartas de diferente palo
        for (Carta c1 : cartas1) {
            for (Carta c2 : cartas2) {
                if (c1.getPalo() != c2.getPalo()) {
                    manos.add(new Pair(c1, c2));
                }
            }
        }
        
        return manos;
    }
    

    private static List<Carta> crearBarajaCompleta() {
        List<Carta> baraja = new ArrayList<>();
        Palo[] palos = Palo.values();
        
        for (int p = 0; p < 4; p++) {
            for (int v = 2; v <= 14; v++) {
                baraja.add(new Carta(v, palos[p]));
            }
        }
        
        return baraja;
    }
}