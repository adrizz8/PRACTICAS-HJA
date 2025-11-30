package model;

public enum Fase {
	PREFLOP,FLOP,TURN,RIVER,END;
	
	public Fase siguiente() {
        int pos = this.ordinal();
        Fase[] vals = values();
        return (pos < vals.length - 1) ? vals[pos + 1] : null;
    }
}
