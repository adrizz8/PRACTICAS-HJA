package model;

import java.util.*;

public class EquityCalculator {

    private static final int NUM_SIMULACIONES = 5000;

    public static Map<Jugador, Double> calcularEquity(Mesa mesa, List<Carta> boardActual) {
        Map<Jugador, Integer> victorias = new HashMap<>();
        Map<Jugador, Integer> empates = new HashMap<>();

        List<Jugador> jugadores = mesa.getNumJugadores();
        List<Carta> barajaRestante = new ArrayList<>(mesa.getlistaCartas());

        for (Jugador j : jugadores) {
            victorias.put(j, 0);
            empates.put(j, 0);
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
                List<Carta> combinacion = new ArrayList<>();
                combinacion.addAll(board);
                combinacion.add((Carta) j.getCartas().getFirst());
                combinacion.add((Carta) j.getCartas().getSecond());
                Mano mano = new Mano(combinacion);
                mano.mejorJugada();
                mejoresManos.put(j, mano);
            }

            // Determinar ganadores
            List<Jugador> ganadores = determinarGanadores(mejoresManos);

            if (ganadores.size() == 1)
                victorias.put(ganadores.get(0), victorias.get(ganadores.get(0)) + 1);
            else
                for (Jugador j : ganadores)
                    empates.put(j, empates.get(j) + 1);
        }

        // Calcular equity en porcentaje
        Map<Jugador, Double> equity = new HashMap<>();
        for (Jugador j : jugadores) {
            double eq = (victorias.get(j) + 0.5 * empates.get(j)) / NUM_SIMULACIONES * 100;
            equity.put(j, eq);
        }

        return equity;
    }

    private static List<Jugador> determinarGanadores(Map<Jugador, Mano> manos) {
        List<Jugador> ganadores = new ArrayList<>();
        int mejorRanking = -1;

        for (Map.Entry<Jugador, Mano> e : manos.entrySet()) {
            int rank = rankingJugada(e.getValue().mejorJugada());
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

    private static int rankingJugada(String jugada) {
        switch (jugada) {
            case "High Card": return 1;
            case "Pair": return 2;
            case "Two Pair": return 3;
            case "Three of a Kind": return 4;
            case "Straight": return 5;
            case "Flush": return 6;
            case "Full House": return 7;
            case "Four of a Kind": return 8;
            case "Straight Flush": return 9;
            default: return 0;
        }
    }
}
