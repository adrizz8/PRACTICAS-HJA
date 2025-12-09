package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import misc.Pair;
import model.Carta;
import model.Equity;
import model.Mesa;
import model.OutsCalculator;
import model.Jugador;

public class Controller {
	private Mesa mesa;
	
	
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
		return mesa.getlistaCartas();
	}

	public void modificarJugador(int jugador, int i, Carta nuevaCarta) {
		mesa.modificarJugador(jugador, i, nuevaCarta);
		
	}

    
    public Mesa getMesa() {
        return mesa;
    }
    
    public boolean enRango(int jugador,List<String> strings) {
		return mesa.enRango(jugador,strings);
	}

	public void cargarRanking(int jugador) {
		mesa.cargarRanking(jugador);
		
	}

	public boolean enPorcentaje(int jugador,double porcentaje) {
		return mesa.enPorcentaje(jugador,porcentaje);
	}

	public void reset() {
	    this.mesa = new Mesa();     
	}

	public Map<Jugador, Double> actualizarEquity() {
		return Equity.calcularEquity(mesa, mesa.cartasBoard());
	}

	public void modificarBoard(int i, Carta nuevaCarta) {
		// TODO Auto-generated method stub
		mesa.modificarBoard(i, nuevaCarta);
	}

	public List<Carta> getBoard() {
		// TODO Auto-generated method stub
		return mesa.getBoard();
	}

	public List<Carta> getCartasBoard(){
		return mesa.cartasBoard();
	}
	
	public int numJugadoresValidos() {
		return mesa.getNumJugadoresValidos();
	}
	
	public Map<Jugador, Double> siguienteFase() {
		// TODO Auto-generated method stub
		return mesa.siguienteFase();
	}

	public boolean ExisteApuesta() {
		// TODO Auto-generated method stub
		return mesa.ExisteApuesta();
	}

	public void NuevaApuesta() {
		// TODO Auto-generated method stub
		mesa.NuevaApuesta();
	}
	
	public void QuitaApuesta() {
		// TODO Auto-generated method stub
		mesa.QuitaApuesta();
	}

	public void fold(int jugador) {
		// TODO Auto-generated method stub
		mesa.fold(jugador);
	}

	public int actualJugador() {
		// TODO Auto-generated method stub
		return mesa.actualJugador();
	}

	public void siguienteJugador() {
		// TODO Auto-generated method stub
		mesa.siguienteJugador();
	}

	public String getFase() {
		// TODO Auto-generated method stub
		return mesa.getFase();
	}
	
	// Añadir estos métodos a la clase Controller.java

	/**
	 * Calcula la media de outs en el Turn para hero contra el rango del villano
	 * 
	 * @param heroIndex Índice del jugador hero
	 * @param rangoVillano Lista con el rango del villano (ej: ["AA", "QQ", "AKs"])
	 * @return Media de outs
	 */
	public double calcularMediaOutsTurn(int heroIndex, List<String> rangoVillano) {
	    // Verificar que estamos en el Turn
	    if (!"TURN".equals(mesa.getFase())) {
	        throw new IllegalStateException("Este cálculo solo se puede hacer en el Turn");
	    }
	    
	    // Obtener las cartas del hero
	    Pair heroCards = mesa.getJugador(heroIndex).getCartas();
	    
	    // Obtener el board (debe tener 4 cartas en el Turn)
	    List<Carta> board = mesa.getBoard();
	    List<Carta> boardTurn = new ArrayList<>();
	    for (Carta c : board) {
	        if (c.getValor() != '0') {
	            boardTurn.add(c);
	        }
	    }
	    
	    if (boardTurn.size() != 4) {
	        throw new IllegalStateException("En el Turn debe haber exactamente 4 cartas en el board");
	    }
	    
	    // Obtener la baraja disponible
	    List<Carta> barajaDisponible = new ArrayList<>(mesa.getlistaCartas());
	    
	    // Calcular la media de outs
	    return OutsCalculator.calcularMediaOuts(heroCards, rangoVillano, boardTurn, barajaDisponible);
	}

	/**
	 * Decide si hero debe hacer Call o Fold basándose en la media de outs y el EM
	 * 
	 * @param heroIndex Índice del jugador hero
	 * @param rangoVillano Rango del villano
	 * @param emMinimo Equity Mínimo requerido (en porcentaje, ej: 30 para 30%)
	 * @return true si debe hacer Call, false si debe hacer Fold
	 */
	public boolean debeHacerCallTurn(int heroIndex, List<String> rangoVillano, double emMinimo) {
	    // Calcular la media de outs
	    double mediaOuts = calcularMediaOutsTurn(heroIndex, rangoVillano);
	    
	    // Hay 44 cartas desconocidas en el River (52 - 2 hero - 2 villano - 4 board)
	    // El porcentaje de equity es: (mediaOuts / 44) * 100
	    double porcentajeEquity = (mediaOuts / 44.0) * 100.0;
	    
	    // Comparar con el EM mínimo
	    return porcentajeEquity >= emMinimo;
	}

	/**
	 * Obtiene información detallada del cálculo de outs
	 * 
	 * @param heroIndex Índice del jugador hero
	 * @param rangoVillano Rango del villano
	 * @return String con información detallada
	 */
	public String obtenerInfoOutsTurn(int heroIndex, List<String> rangoVillano) {
	    double mediaOuts = calcularMediaOutsTurn(heroIndex, rangoVillano);
	    double porcentajeEquity = (mediaOuts / 44.0) * 100.0;
	    
	    return String.format(
	        "Media de outs: %.2f\n" +
	        "Porcentaje de equity: %.2f%%\n" +
	        "Cartas en el River que nos hacen ganar: %.2f de 44",
	        mediaOuts, porcentajeEquity, mediaOuts
	    );
	}
}
