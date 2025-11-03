package control;

import java.util.List;

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

	public boolean enRango(int jugador,List<String> strings) {
		// TODO Auto-generated method stub
		return mesa.enRango(jugador,strings);
	}
}
