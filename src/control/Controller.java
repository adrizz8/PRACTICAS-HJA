package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import misc.Pair;
import model.Carta;
import model.Equity;
import model.Mesa;
import model.Jugador;

public class Controller {
	private Mesa mesa;
	private int fase = 0;
	private List<Carta> board = new ArrayList<>();
	
	public Controller(Mesa mesa) {
		this.mesa = mesa;
	}
	
	public int getNumJugadores() {
		return mesa.getNumJugadores();
	}
	
	public void initJugadores(boolean isRandom) {
		mesa.initJugadores(isRandom);
	}
	
	public Pair getCartasJugador(int index) {
		return mesa.getJugador(index).getCartas();
	}

	public List<Carta> getlistaCartas() {
		// TODO Auto-generated method stub
		return mesa.getlistaCartas();
	}

	public void modificarJugador(int jugador, int i, String nueva_ruta, String vieja_ruta) {
		// TODO Auto-generated method stub
		mesa.modificarJugador(jugador,i,nueva_ruta,vieja_ruta);
		
	}
	
	// 🔹 Avanzar de fase y calcular equity
    public Map<Jugador, Double> siguienteFase() {
        switch (fase) {
            case 0 -> { // Flop
                board.add(mesa.getRandom());
                board.add(mesa.getRandom());
                board.add(mesa.getRandom());
            }
            case 1, 2 -> board.add(mesa.getRandom()); // Turn / River
            case 3 -> System.out.println("Fin de la partida");
        }
        fase++;

        return Equity.calcularEquity(mesa, board);
    }

    public List<Carta> getBoard() {
        return board;
    }

    public int getFase() {
        return fase;
    }
    
    public Mesa getMesa() {
        return mesa;
    }
}
