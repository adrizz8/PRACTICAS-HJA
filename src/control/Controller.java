package control;

import java.util.ArrayList;
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
	
	//pu
	public boolean test() {
		
		List<String> list = new ArrayList<String>();
		
		list.add("AA");
		list.add("JJ");
		list.add("ATs-A8s");
		list.add("76o+");
		list.add("54o");
		
		
		return mesa.getJugador(0).enRango(list);
	}
}
