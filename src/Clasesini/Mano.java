package Clasesini;

import java.util.*;

public class Mano {
		
	private List<Carta> _mano;
	private List<Carta> _mano5;
	private String _mejorJugada;
	private ArrayList<String> _draws; // Lista de Draws, luego se muestran
	
	private int _max_escalera; // Indica donde empieza la escalera para luego mostrarla
	Palo _palo_color;          // Indica el palo del color para luego mostrarlo

	// luego sirve para mostrar por pantalla
	// valor de carta y numero de veces que aparece, de nuestra mejor repeticion (metodo HighCard_to_Poker())
	private int _val_aux1;
	private int _cant_aux1;
	
	// valor de carta y numero de veces que aparece, de nuestra segunda mejor repeticion (metodo HighCard_to_Poker())
	private int _val_aux2;
	private int _cant_aux2;
	
	
	public Mano(List<Carta> m) {
		// Ordenamos por numero de carta para ahorrar en un futuro
		m.sort(Comparator.comparingInt((Carta c) -> c.getValorNumerico()));
		this._mano = m;
		_draws = new ArrayList<>();
	}

	public List<Carta> getMano() {
		return _mano;
	}

	public void setMano(List<Carta> mano) {
		this._mano = mano;
	}

	public void mostrarMano() {
		System.out.print("Cartas del jugador: ");
		for (Carta c : _mano) {
			System.out.print(c);
		}
		System.out.println();
	}
	
	public String getString() {
		String s = "";
		if(esEscalera(_mano)) {
			s = this.mostrar_Cartas_Jugada();
		}
		else {
			for (Carta c : _mano5) {
				s += c.toString();
			}
		}
		
		return s;
	}

	public boolean esColor(List<Carta> m) {
		boolean escolor = true;
		Map<Palo, Integer> cuenta = new HashMap<>();

		int num_max = 0;
		int num;
		Palo pal;

		for (Carta c : _mano) {
			pal = c.getPalo();
			num = cuenta.getOrDefault(pal, 0) + 1;

			if (num_max < num) {
				num_max = num;
				_palo_color = pal;
			}

			cuenta.put(pal, num);
		}

		Collection<Integer> valores = cuenta.values();

		if (!valores.contains(5) && !valores.contains(6) && !valores.contains(7)) {
			escolor = false;
			if (valores.contains(4))
				_draws.add("Flush");
		}

		return escolor;
	}

	public boolean esEscalera(List<Carta> m) {
		boolean esescalera = false;
		Set<Integer> setValores = new HashSet<>();

		for (Carta c : m) {
			setValores.add(c.getValorNumerico());
		}

		List<Integer> valores = new ArrayList<>(setValores);
		Collections.sort(valores);

		if (setValores.contains(14) && setValores.contains(2) && setValores.contains(3) &&
		    setValores.contains(4) && setValores.contains(5)) {
			esescalera = true;
			_max_escalera = 5;
		} else {
			int primero;
			int segundo;
			int cont = 0;
			int cont_gutshot = 0;
			boolean gutshot = false;

			for (int i = 0; i < valores.size() - 1; i++) {
				primero = valores.get(i);
				segundo = valores.get(i + 1);

				if (segundo - primero == 1) {
					cont++;
					cont_gutshot++;
					if (cont >= 4) {
						esescalera = true;
						_max_escalera = segundo;
					}
				} else if (segundo - primero == 2) {
					if (cont == 3) _draws.add("Straight Open-ended");
					cont = 0;

					if (!gutshot) {
						gutshot = true;
						cont_gutshot++;
					} else {
						if (cont_gutshot >= 3 && gutshot) _draws.add("Straight Gutshot");
						gutshot = false;
						cont_gutshot = 0;
					}
				} else if (segundo - primero > 2) {
					if (cont == 3) _draws.add("Straight Open-ended");
					cont = 0;
					if (cont_gutshot >= 3 && gutshot) _draws.add("Straight Gutshot");
					cont_gutshot = 0;
				}
			}

			if (cont == 3) _draws.add("Straight Open-ended");
			if (cont_gutshot >= 3 && gutshot) _draws.add("Straight Gutshot");
		}

		return esescalera;
	}

	public void HighCard_to_Poker() {
		Map<Integer, Integer> cuenta = new HashMap<>();

		for (Carta c : _mano) {
			cuenta.put(c.getValorNumerico(), cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		}

		for (Map.Entry<Integer, Integer> entry : cuenta.entrySet()) {
			if (_cant_aux1 < entry.getValue()) {
				_val_aux2 = _val_aux1;
				_cant_aux2 = _cant_aux1;
				_val_aux1 = entry.getKey();
				_cant_aux1 = entry.getValue();
			} else if (_cant_aux1 == entry.getValue()) {
				if (_val_aux1 < entry.getKey()) {
					_val_aux2 = _val_aux1;
					_cant_aux2 = _cant_aux1;
					_val_aux1 = entry.getKey();
					_cant_aux1 = entry.getValue();
				} else {
					if (_cant_aux2 < entry.getValue()) {
						_val_aux2 = entry.getKey();
						_cant_aux2 = entry.getValue();
					} else {
						if (_val_aux2 < entry.getKey()) {
							_val_aux2 = entry.getKey();
							_cant_aux2 = entry.getValue();
						}
					}
				}
			} else {
				if (_cant_aux2 < entry.getValue()) {
					_val_aux2 = entry.getKey();
					_cant_aux2 = entry.getValue();
				} else if (_cant_aux2 == entry.getValue()) {
					if (_val_aux2 < entry.getKey()) {
						_val_aux2 = entry.getKey();
						_cant_aux2 = entry.getValue();
					}
				}
			}
		}
	}

	public String mejorJugada() {
		boolean escalera = esEscalera(_mano);
		boolean color = esColor(_mano);
		HighCard_to_Poker();
		
		List<Carta> listaC = new ArrayList<>();
		
		if(color) {
			for(int i = _mano.size(); listaC.size() < 5; i--) {
				if(_mano.get(i - 1).getPalo() == _palo_color) {
					listaC.add(_mano.get(i - 1));
				}
			}
		}
		
		else {		
			for(int i = 0; i < _cant_aux1 && listaC.size() < 5; i++) {
				if(_mano.get(i).getValorNumerico() == _val_aux1) {
					listaC.add(_mano.get(i));
				}
			}
				
			for(int j = 0; j < _cant_aux2 && listaC.size() < 5; j++) {
				if(_mano.get(j).getValorNumerico() == _val_aux2) {
					listaC.add(_mano.get(j));
				}
			}
				
			for(int k = _mano.size();listaC.size() < 5; k--) {
				listaC.add(_mano.get(k - 1));
			}		
		}
			
		
		_mano5 = listaC;
		
		

		if (escalera && color) _mejorJugada = "Straight Flush";
		else if (_cant_aux1 == 4) _mejorJugada = "Four of a Kind";
		else if (_cant_aux1 == 3 && _cant_aux2 == 2) _mejorJugada = "Full House";
		else if (color) _mejorJugada = "Flush";
		else if (escalera) _mejorJugada = "Straight";
		else if (_cant_aux1 == 3) _mejorJugada = "Three of a Kind";
		else if (_cant_aux1 == 2 && _cant_aux2 == 2) _mejorJugada = "Two Pair";
		else if (_cant_aux1 == 2) _mejorJugada = "Pair";
		else _mejorJugada = "High Card";

		return _mejorJugada;
	}

	public String mostrarDraws() {
	    StringBuilder sb = new StringBuilder();
	    for (String aux : _draws) {
	        sb.append(" - Draw: ").append(aux).append("\n");
	    }
	    return sb.toString();
	}
	public String mostrar_Cartas_Jugada() {
		String cartas = "";
		switch (_mejorJugada) {
			case "Straight Flush": {
				int siguiente = _max_escalera - 4;
				int i = 0;
				if (siguiente == 1) {
					int j = _mano.size() - 1;
					while (siguiente == 1) {
						if (_mano.get(j).getPalo().ordinal() == _palo_color.ordinal()) {
							cartas += _mano.get(j);
							siguiente++;
						}
						j--;
					}
				}
				while (i < _mano.size() && siguiente <= _max_escalera) {
					Carta aux = _mano.get(i);
					if (aux.getValorNumerico() == siguiente && aux.getPalo().ordinal() == _palo_color.ordinal()) {
						cartas += aux.toString();
						siguiente++;
					}
					i++;
				}
				break;
			}
			case "Straight": {
				int siguiente = _max_escalera - 4;
				int i = 0;
				if (siguiente == 1) {
					cartas += _mano.get(_mano.size() - 1);
					siguiente++;
				}
				while (i < _mano.size() && siguiente <= _max_escalera) {
					Carta aux = _mano.get(i);
					if (aux.getValorNumerico() == siguiente) {
						cartas += aux.toString();
						siguiente++;
					}
					i++;
				}
				break;
			}
			case "Flush": {
				for (int i = _mano.size() - 1; i >= 0; i--) {
					if (_mano.get(i).getPalo().ordinal() == _palo_color.ordinal())
						cartas += _mano.get(i).toString();
				}
				break;
			}
			default: {
				int i = 0;
				Carta aux;
				while (_cant_aux1 > 0) {
					aux = _mano.get(i);
					if (aux.getValorNumerico() == _val_aux1) {
						_cant_aux1--;
						cartas += aux.toString();
					}
					i++;
				}
				if (_cant_aux2 > 1) {
					i = 0;
					while (_cant_aux2 > 0) {
						aux = _mano.get(i);
						if (aux.getValorNumerico() == _val_aux2) {
							_cant_aux2--;
							cartas += aux.toString();
						}
						i++;
					}
				}
				break;
			}
		}
		return cartas;
	}
	
	
	public int getValor() {
		int valor = 0;
		switch(_mejorJugada) {
			case "Pair":
				valor = 1000;
				break;
			case "Two Pair":
				valor = 2000;
				break;
			case "Three of a Kind":
				valor = 3000;
				break;
			case "Straight":
				valor = 4000;
				break;
			case "Flush":
				valor = 5000;
				break;
			case "Full House":
				valor = 6000;
				break;
			case "Four of a Kind":
				valor = 7000;
				break;
			case "Straight Flush":
				valor = 8000;
				break;
				
		}
		
		
		if( _mejorJugada == "High Card" || _mejorJugada == "Pair" || _mejorJugada == "Two Pair" || _mejorJugada == "Three of a Kind") {
			valor += (_val_aux1 * 50) * _cant_aux1;
		}
		
		for (int i = 0; i < _mano.size(); i++) {
			valor += _mano.get(i).getValorNumerico();
		}
		
		return valor;
	}
}
