package model;

import java.util.*;

public class Equity {

    private static final int NUM_SIMULACIONES = 5000;

    public static Map<Jugador, Double> calcularEquity(Mesa mesa, List<Carta> boardActual) {
        Map<Jugador, Double> equityAcumulada = new HashMap<>();
        List<Jugador> jugadores = mesa.getListaJugadores();
        List<Carta> barajaRestante = new ArrayList<>(mesa.getlistaCartas());

        for (Jugador j : jugadores) {
            equityAcumulada.put(j, 0.0);
        }

        Random rand = new Random();

        for (int i = 0; i < NUM_SIMULACIONES; i++) {
            List<Carta> board = new ArrayList<>(boardActual);

            // Completa el board hasta 5 cartas
            while (board.size() < 5) {
                Carta c = barajaRestante.get(rand.nextInt(barajaRestante.size()));
                if (!board.contains(c)) board.add(c);
            }

            // Calcular la mejor jugada de cada jugador
            Map<Jugador, Mano> mejoresManos = new HashMap<>();
            for (Jugador j : jugadores) {
            	if(!j.getFold()) {
	                List<Carta> combinacion = new ArrayList<>(board);
	                combinacion.add((Carta) j.getCartas().getFirst());
	                combinacion.add((Carta) j.getCartas().getSecond());
	                Mano mano = new Mano(combinacion);
	                mano.mejorJugada();
	                mejoresManos.put(j, mano);
            	}
            }

            // Determinar ganadores
            List<Jugador> ganadores = determinarGanadores(mejoresManos);

            //En caso de empate reparto equitativo entre los jugadores
            double puntosPorJugador = 1.0 / ganadores.size();

            for (Jugador j : ganadores) {
                equityAcumulada.put(j, equityAcumulada.get(j) + puntosPorJugador);
            }
        }

        //Calculo del equity en porcenta
        Map<Jugador, Double> equity = new HashMap<>();
        for (Jugador j : jugadores) {
            double eq = (equityAcumulada.get(j) / NUM_SIMULACIONES) * 100.0;
            equity.put(j, eq);
        }

        return equity;
    }

    

    private static List<Jugador> determinarGanadores(Map<Jugador, Mano> manos) {
        List<Jugador> ganadores = new ArrayList<>();
        int mejorRanking = 0;

        for (Map.Entry<Jugador, Mano> e : manos.entrySet()) {
        	 //int rank = rankingJugada(e.getValue().mejorJugada());
            int rank = e.getValue().valor_jugada();
            if (rank > mejorRanking) {
                mejorRanking = rank;
                ganadores.clear();
                ganadores.add(e.getKey());
            } else if (rank == mejorRanking) {
                ganadores.add(e.getKey());
            }
        }
        return ganadores;
    }

   
}
