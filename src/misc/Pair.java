package misc;

import model.Carta;

public class Pair {
	private Carta _first;
	private Carta _second;

	public Pair(Carta first, Carta second) {
		this._first = first;
		this._second = second;
	}
	
	public Pair(String string) {
		this._first = new Carta(string.substring(0, 2));
		this._second = new Carta(string.substring(2));
	}
	
	public Carta getFirst() {
		return _first;
	}

	public Carta getSecond() {
		return _second;
	}
	
	
	public Carta getHigh(){
		if(_first.getValorNumerico() > _second.getValorNumerico()) {
			return _first;
		}
		
		else {
			return _second;
		}
		
	}
	
	public Carta getLow(){
		if(_first.getValorNumerico() < _second.getValorNumerico()) {
			return _first;
		}
		
		else {
			return _second;
		}
		
	}
	
}
