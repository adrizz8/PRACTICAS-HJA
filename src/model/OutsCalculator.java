package model;

import java.util.*;
import misc.Pair;

/**
 * Clase para calcular los outs en el Turn
 * Un "out" es una carta que nos hace ganar la mano en el River
 */
public class OutsCalculator {
    
    /**
     * Calcula cuántas cartas del River nos hacen ganar contra una mano específica del villano
     * 
     * @param heroCards Las 2 cartas del hero
     * @param villanoCards Las 2 cartas del villano
     * @param board Las 4 cartas del board (estamos en Turn)
     * @param cartasUsadas Lista de todas las cartas que ya están en juego
     * @return Número de outs (cartas que nos hacen ganar en el River)
     */
    public static double calcularOuts(Pair heroCards, Pair villanoCards, 
                                     List<Carta> board, List<Carta> cartasUsadas) {
        
        // Crear una lista con todas las cartas posibles (52 cartas)
        List<Carta> todasLasCartas = crearBarajaCompleta();
        
        // Eliminar las cartas que ya están en juego
        todasLasCartas.removeAll(cartasUsadas);
        
        // Ahora todasLasCartas contiene las 44 cartas que pueden salir en el River
        int outsQueGanan = 0;
        int outsQueEmpatan = 0;
        
        // Probamos cada carta posible en el River
        for (Carta cartaRiver : todasLasCartas) {
            // Crear el board completo con esta carta del River
            List<Carta> boardCompleto = new ArrayList<>(board);
            boardCompleto.add(cartaRiver);
            
            // Calcular la mejor mano del hero
            List<Carta> combinacionHero = new ArrayList<>(boardCompleto);
            combinacionHero.add((Carta) heroCards.getFirst());
            combinacionHero.add((Carta) heroCards.getSecond());
            Mano manoHero = new Mano(combinacionHero);
            manoHero.mejorJugada();
            
            // Calcular la mejor mano del villano
            List<Carta> combinacionVillano = new ArrayList<>(boardCompleto);
            combinacionVillano.add((Carta) villanoCards.getFirst());
            combinacionVillano.add((Carta) villanoCards.getSecond());
            Mano manoVillano = new Mano(combinacionVillano);
            manoVillano.mejorJugada();
            
            // Comparar las manos
            int valorHero = manoHero.valor_jugada();
            int valorVillano = manoVillano.valor_jugada();
            
            if (valorHero > valorVillano) {
                outsQueGanan++;
            } else if (valorHero == valorVillano) {
                outsQueEmpatan++;
            }
        }
        
        // Los empates cuentan como 0.5 outs
        return outsQueGanan + (outsQueEmpatan * 0.5);
    }
    
    /**
     * Calcula la media de outs contra un rango del villano
     * 
     * @param heroCards Las 2 cartas del hero
     * @param rangoVillano Lista de rangos del villano (ej: "AA", "KK", "AKs")
     * @param board Las 4 cartas del board
     * @param barajaDisponible Cartas que aún no están en juego
     * @return Media aritmética de outs
     */
    public static double calcularMediaOuts(Pair heroCards, List<String> rangoVillano,
                                          List<Carta> board, List<Carta> barajaDisponible) {
        
        // Lista con todas las cartas que ya están en uso
        List<Carta> cartasUsadas = new ArrayList<>();
        cartasUsadas.add((Carta) heroCards.getFirst());
        cartasUsadas.add((Carta) heroCards.getSecond());
        cartasUsadas.addAll(board);
        
        // Generar todas las manos posibles del rango del villano
        List<Pair> manosVillano = generarManosDelRango(rangoVillano, cartasUsadas);
        
        if (manosVillano.isEmpty()) {
            return 0.0;
        }
        
        // Calcular outs para cada mano posible del villano
        double sumaOuts = 0.0;
        
        for (Pair manoVillano : manosVillano) {
            // Añadir las cartas del villano a las cartas usadas
            List<Carta> cartasUsadasConVillano = new ArrayList<>(cartasUsadas);
            cartasUsadasConVillano.add((Carta) manoVillano.getFirst());
            cartasUsadasConVillano.add((Carta) manoVillano.getSecond());
            
            // Calcular outs contra esta mano específica
            double outs = calcularOuts(heroCards, manoVillano, board, cartasUsadasConVillano);
            sumaOuts += outs;
        }
        
        // Retornar la media
        return sumaOuts / manosVillano.size();
    }
    
    /**
     * Genera todas las manos posibles a partir de un rango
     * Por ejemplo: "AA" genera todas las parejas de ases posibles
     */
    private static List<Pair> generarManosDelRango(List<String> rangoVillano, 
                                                   List<Carta> cartasUsadas) {
        List<Pair> manosGeneradas = new ArrayList<>();
        List<Carta> barajaCompleta = crearBarajaCompleta();
        
        // Eliminar las cartas que ya están en uso
        barajaCompleta.removeAll(cartasUsadas);
        
        for (String rango : rangoVillano) {
            rango = rango.trim();
            
            // Procesar cada tipo de rango
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
    
    /**
     * Genera todas las parejas posibles de un valor (ej: todas las combinaciones de AA)
     */
    private static List<Pair> generarParejas(char valor, List<Carta> barajaDisponible) {
        List<Pair> parejas = new ArrayList<>();
        List<Carta> cartasDelValor = new ArrayList<>();
        
        // Encontrar todas las cartas con este valor que están disponibles
        for (Carta c : barajaDisponible) {
            if (c.getValor() == valor) {
                cartasDelValor.add(c);
            }
        }
        
        // Generar todas las combinaciones posibles (C(n,2))
        for (int i = 0; i < cartasDelValor.size(); i++) {
            for (int j = i + 1; j < cartasDelValor.size(); j++) {
                parejas.add(new Pair(cartasDelValor.get(i), cartasDelValor.get(j)));
            }
        }
        
        return parejas;
    }
    
    /**
     * Genera todas las manos suited (mismo palo)
     */
    private static List<Pair> generarManosSuited(char valor1, char valor2, 
                                                 List<Carta> barajaDisponible) {
        List<Pair> manos = new ArrayList<>();
        
        // Para cada palo
        for (Palo palo : Palo.values()) {
            Carta carta1 = null;
            Carta carta2 = null;
            
            // Buscar las cartas con estos valores y este palo
            for (Carta c : barajaDisponible) {
                if (c.getValor() == valor1 && c.getPalo() == palo) {
                    carta1 = c;
                }
                if (c.getValor() == valor2 && c.getPalo() == palo) {
                    carta2 = c;
                }
            }
            
            // Si encontramos ambas cartas, crear la mano
            if (carta1 != null && carta2 != null) {
                manos.add(new Pair(carta1, carta2));
            }
        }
        
        return manos;
    }
    
    /**
     * Genera todas las manos offsuit (diferente palo)
     */
    private static List<Pair> generarManosOffsuit(char valor1, char valor2, 
                                                  List<Carta> barajaDisponible) {
        List<Pair> manos = new ArrayList<>();
        List<Carta> cartas1 = new ArrayList<>();
        List<Carta> cartas2 = new ArrayList<>();
        
        // Encontrar todas las cartas con cada valor
        for (Carta c : barajaDisponible) {
            if (c.getValor() == valor1) {
                cartas1.add(c);
            }
            if (c.getValor() == valor2) {
                cartas2.add(c);
            }
        }
        
        // Combinar cartas de diferente palo
        for (Carta c1 : cartas1) {
            for (Carta c2 : cartas2) {
                if (c1.getPalo() != c2.getPalo()) {
                    manos.add(new Pair(c1, c2));
                }
            }
        }
        
        return manos;
    }
    
    /**
     * Crea una baraja completa de 52 cartas
     */
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