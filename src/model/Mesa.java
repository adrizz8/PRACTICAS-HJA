package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import misc.Pair;


public class Mesa {
	private final int NUM_JUGADORES = 6;
	private List<Carta> listaCartas = new ArrayList<Carta>(52);
	private List<Jugador> listaJugadores = new ArrayList<Jugador>(6);
	private List<Carta> board=new ArrayList<Carta>(5);
	private Fase fase;
	private boolean apuestaMesa;
	private int jugadorActual;

	
	
	public Mesa() {
		initBoard();
		initCartas();
	}
	
	private void initBoard() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 5; i++) {
			board.add(new Carta());
		}
	}

	public Carta getRandom() {
		//Coge una carta aleatoria de la mesa la quita
		Random rand = new Random();
	    Carta c = listaCartas.get(rand.nextInt(listaCartas.size()));
	    listaCartas.remove(c);
	    return c;
	}
	
	public Carta getCarta(String carta) {
		Carta c = new Carta(carta);
		
		for(int i = 0; i < listaCartas.size(); i++) {
			if(listaCartas.get(i).equals(c)) {
				listaCartas.remove(i);
			}
		}
		
		return c;
	}
	
	public Jugador getJugador(int index) {
		return listaJugadores.get(index);
	}
	
	public int getNumJugadores() {
		return NUM_JUGADORES;
	}
	
	
	
	private void addJugadorRandom(boolean random) {
		Pair hCards;
		if(random) {
			hCards = new Pair(getRandom(), getRandom());
			listaJugadores.add(new Jugador(this, hCards));
		}else {
			hCards= new Pair(new Carta(), new Carta());	
			listaJugadores.add(new Jugador(this, hCards));
		}
	}
	
	
	private void initCartas() {
		
		fase=Fase.PREFLOP;
		Palo[] palos = Palo.values();
		jugadorActual=-1;
		for (int p = 0; p < 4; p++) {            
		    for (int v = 2; v <= 14; v++) {     
		        Carta c = new Carta(v, palos[p]);
		        listaCartas.add(c);
		    }
		}
	}
	
	public void initJugadores(boolean isRandom) {
		for(int i = 0; i < NUM_JUGADORES; i++) {
			addJugadorRandom(isRandom);
		}
	}

	public List<Carta> getlistaCartas() {
		return listaCartas;
	}

	
	
    public Map<Jugador, Double> siguienteFase() {
    	Map<Jugador, Double> result = null;
    	

        switch (fase) {
        	case PREFLOP -> { //Pre flop
        		List<Carta>CartasReales=cartasBoard();
        		result = Equity.calcularEquity(this, CartasReales);
        	}
            case FLOP -> { // Flop
                board.set(0,getRandom());
                board.set(1,getRandom());
                board.set(2,getRandom());
                List<Carta>CartasReales=cartasBoard();
                result = Equity.calcularEquity(this, CartasReales);
            }
            case TURN -> {
                board.set(3,getRandom()); // Turn
                List<Carta>CartasReales=cartasBoard();
                result = Equity.calcularEquity(this, CartasReales);
            }
            case RIVER -> {
            	board.set(4,getRandom()); // River
            	
                result = Equity.calcularEquity(this, board);
            }
            case END -> {
                System.out.println("FIN DE LA PARTIDA");
                result = null; // señal de final
            }
        }

        fase=fase.siguiente();
        return result;
  
        
    }

    private List<Carta> cartasBoard() {
		// TODO Auto-generated method stub
    	List<Carta> aux= new  ArrayList<Carta>();
    	
    	for (int i = 0; i < board.size(); i++) 
			if(board.get(i).getValor()!='0') 
				aux.add(board.get(i));
				
    	return aux;
    }

	public List<Carta> getBoard() {
        return board;
    }

	
	public List<Jugador> getListaJugadores(){
		return listaJugadores;
	}
	
	public boolean enRango(int jugador,List<String> strings) {
		return listaJugadores.get(jugador).enRango(strings);
	}

	public void cargarRanking(int jugador) {
		listaJugadores.get(jugador).cargarRanking();
	}

	public boolean enPorcentaje(int jugador,double porcentaje) {
		return listaJugadores.get(jugador).enPorcentaje(porcentaje);
	}


    public String getFase() {
        return fase.name();
    }
    
    public void modificarJugador(int jugador, int i, Carta nuevaCarta) {;
		Carta cartaVieja;
		if(i==0)
			cartaVieja = listaJugadores.get(jugador).getCartas().getFirst();
		else
			cartaVieja = listaJugadores.get(jugador).getCartas().getSecond();
		
		for(int j=0;j<listaCartas.size();j++) {
			if(listaCartas.get(j).equals(nuevaCarta)) {
				listaCartas.remove(j);
			}			
		}
		
		listaCartas.add(cartaVieja);
		
		if(i==0)
			listaJugadores.get(jugador).setCarta1(nuevaCarta);
		else
			listaJugadores.get(jugador).setCarta2(nuevaCarta);
	}
    
	public void modificarBoard(int i, Carta nuevaCarta) {
		
		for(int j=0;j<listaCartas.size();j++) {
			
			if(listaCartas.get(j).equals(nuevaCarta))
				listaCartas.remove(j);
		}
		
		listaCartas.add(board.get(i));
		
		board.set(i, nuevaCarta);
	}

	public void fold(int jugador) {
		// TODO Auto-generated method stub
		listaJugadores.get(jugador).Fold();
	}

	public void NuevaApuesta() {
		// TODO Auto-generated method stub
		apuestaMesa=true;
	}

	public void QuitaApuesta() {
		apuestaMesa = false;
	}
	
	public boolean ExisteApuesta() {
		// TODO Auto-generated method stub
		return apuestaMesa;
	}

	public int actualJugador() {
		// TODO Auto-generated method stub
		return jugadorActual;
	}

	public void siguienteJugador() {
		// TODO Auto-generated method stub
		int i = jugadorActual + 1;
		boolean encontrado = false;

		while (i < NUM_JUGADORES && !encontrado) {
		    if (!listaJugadores.get(i).getFold()) {
		        jugadorActual = i;
		        encontrado = true;   // hace que el while termine
		    } 
		    i++;
		}
		if(!encontrado)
			jugadorActual=-1;
		
	}
	
	
	
}

