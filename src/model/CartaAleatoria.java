package model;

public class CartaAleatoria {

	/*private CartaAleatoriaImagen _cartaAleatoria;
	private Palo _palo;
	private int _valor;*/
	
	public CartaAleatoria() { }
	
	public static Carta eleccionCartaAleatoria(String nombre) {
		switch(nombre) {
		// - - - - - - 2 - - - - - -
        case "2c.png": return new Carta('2', Palo.c);
        case "2d.png": return new Carta('2', Palo.d);
        case "2h.png": return new Carta('2', Palo.h);
        case "2s.png": return new Carta('2', Palo.s);

        
        // - - - - - - 3 - - - - - -
        case "3c.png": return new Carta('3', Palo.c);
        case "3d.png": return new Carta('3', Palo.d);
        case "3h.png": return new Carta('3', Palo.h);
        case "3s.png": return new Carta('3', Palo.s);

        
        // - - - - - - 4 - - - - - -
        case "4c.png": return new Carta('4', Palo.c);
        case "4d.png": return new Carta('4', Palo.d);
        case "4h.png": return new Carta('4', Palo.h);
        case "4s.png": return new Carta('4', Palo.s);

        
        // - - - - - - 5 - - - - - -
        case "5c.png": return new Carta('5', Palo.c);
        case "5d.png": return new Carta('5', Palo.d);
        case "5h.png":  return new Carta('5', Palo.h);
        case "5s.png":  return new Carta('5', Palo.s);

        
        // - - - - - - 6 - - - - - -
        case "6c.png": return new Carta('6', Palo.c);
        case "6d.png": return new Carta('6', Palo.d);
        case "6h.png": return new Carta('6', Palo.h);
        case "6s.png": return new Carta('6', Palo.s);

        
        // - - - - - - 7 - - - - - -
        case "7c.png": return new Carta('7', Palo.c);
        case "7d.png": return new Carta('7', Palo.d);
        case "7h.png": return new Carta('7', Palo.h);
        case "7s.png": return new Carta('7', Palo.s);

        
        // - - - - - - 8 - - - - - -
        case "8c.png": return new Carta('8', Palo.c);
        case "8d.png": return new Carta('8', Palo.d);
        case "8h.png": return new Carta('8', Palo.h);
        case "8s.png": return new Carta('8', Palo.s);

        
        // - - - - - - 9 - - - - - -
        case "9c.png": return new Carta('9', Palo.c);
        case "9d.png": return new Carta('9', Palo.d);
        case "9h.png": return new Carta('9', Palo.h);
        case "9s.png": return new Carta('9', Palo.s);

        
        // - - - - - - 10 - - - - - -
        case "Tc.png": return new Carta('T', Palo.c);
        case "Td.png": return new Carta('T', Palo.d);
        case "Th.png": return new Carta('T', Palo.h);
        case "Ts.png": return new Carta('T', Palo.s);

        
        // - - - - - - AS - - - - - -
        case "Ac.png": return new Carta('A', Palo.c);
        case "Ad.png": return new Carta('A', Palo.d);
        case "Ah.png": return new Carta('A', Palo.h);
        case "As.png": return new Carta('A', Palo.s);

        
        // - - - - - - JACK - - - - - -
        case "Jc.png": return new Carta('J', Palo.c);
        case "Jd.png": return new Carta('J', Palo.d);
        case "Jh.png": return new Carta('J', Palo.h);
        case "Js.png": return new Carta('J', Palo.s);

        
        // - - - - - - KING - - - - - -
        case "Kc.png": return new Carta('K', Palo.c);
        case "Kd.png": return new Carta('K', Palo.d);
        case "Kh.png": return new Carta('K', Palo.h);
        case "Ks.png": return new Carta('K', Palo.s);

        
        // - - - - - - QUEEN - - - - - -
        case "Qc.png": return new Carta('Q', Palo.c);
        case "Qd.png": return new Carta('Q', Palo.d);
        case "Qh.png": return new Carta('Q', Palo.h);
        case "Qs.png": return new Carta('Q', Palo.s);

        
        // - - - - - - JOKERS - - - - - -
        case "black_joker.png": return null;
        case "red_joker.png": return null;
		}
		return null;
		
	}
}
