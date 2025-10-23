package control;

import misc.Pair;
import model.Carta;
import model.Mesa;

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
	
	public Pair<Carta> getCartasJugador(int index) {
		return mesa.getJugador(index).getCartas();
	}
}
