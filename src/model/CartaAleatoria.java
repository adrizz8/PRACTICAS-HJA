package model;

public class CartaAleatoria {

	/*private CartaAleatoriaImagen _cartaAleatoria;
	private Palo _palo;
	private int _valor;*/
	
	public CartaAleatoria() { }
	
	public Carta eleccionCartaAleatoria(String nombre) {
		switch(nombre) {
		// - - - - - - 2 - - - - - -
        case "2_of_clubs.png": return new Carta('2', Palo.c);
        case "2_of_diamonds.png": return new Carta('2', Palo.d);
        case "2_of_hearts.png": return new Carta('2', Palo.h);
        case "2_of_spades.png": return new Carta('2', Palo.s);

        
        // - - - - - - 3 - - - - - -
        case "3_of_clubs.png": return new Carta('3', Palo.c);
        case "3_of_diamonds.png": return new Carta('3', Palo.d);
        case "3_of_hearts.png": return new Carta('3', Palo.h);
        case "3_of_spades.png": return new Carta('3', Palo.s);

        
        // - - - - - - 4 - - - - - -
        case "4_of_clubs.png": return new Carta('4', Palo.c);
        case "4_of_diamonds.png": return new Carta('4', Palo.d);
        case "4_of_hearts.png": return new Carta('4', Palo.h);
        case "4_of_spades.png": return new Carta('4', Palo.s);

        
        // - - - - - - 5 - - - - - -
        case "5_of_clubs.png": return new Carta('5', Palo.c);
        case "5_of_diamonds.png": return new Carta('5', Palo.d);
        case "5_of_hearts.png":  return new Carta('5', Palo.h);
        case "5_of_spades.png":  return new Carta('5', Palo.s);

        
        // - - - - - - 6 - - - - - -
        case "6_of_clubs.png": return new Carta('6', Palo.c);
        case "6_of_diamonds.png": return new Carta('6', Palo.d);
        case "6_of_hearts.png": return new Carta('6', Palo.h);
        case "6_of_spades.png": return new Carta('6', Palo.s);

        
        // - - - - - - 7 - - - - - -
        case "7_of_clubs.png": return new Carta('7', Palo.c);
        case "7_of_diamonds.png": return new Carta('7', Palo.d);
        case "7_of_hearts.png": return new Carta('7', Palo.h);
        case "7_of_spades.png": return new Carta('7', Palo.s);

        
        // - - - - - - 8 - - - - - -
        case "8_of_clubs.png": return new Carta('8', Palo.c);
        case "8_of_diamonds.png": return new Carta('8', Palo.d);
        case "8_of_hearts.png": return new Carta('8', Palo.h);
        case "8_of_spades.png": return new Carta('8', Palo.s);

        
        // - - - - - - 9 - - - - - -
        case "9_of_clubs.png": return new Carta('9', Palo.c);
        case "9_of_diamonds.png": return new Carta('9', Palo.d);
        case "9_of_hearts.png": return new Carta('9', Palo.h);
        case "9_of_spades.png": return new Carta('9', Palo.s);

        
        // - - - - - - 10 - - - - - -
        case "10_of_clubs.png": return new Carta('T', Palo.c);
        case "10_of_diamonds.png": return new Carta('T', Palo.d);
        case "10_of_hearts.png": return new Carta('T', Palo.h);
        case "10_of_spades.png": return new Carta('T', Palo.s);

        
        // - - - - - - AS - - - - - -
        case "ace_of_clubs.png": return new Carta('A', Palo.c);
        case "ace_of_diamonds.png": return new Carta('A', Palo.d);
        case "ace_of_hearts.png": return new Carta('A', Palo.h);
        case "ace_of_spades.png": return new Carta('A', Palo.s);

        
        // - - - - - - JACK - - - - - -
        case "jack_of_clubs2.png": return new Carta('J', Palo.c);
        case "jack_of_diamonds2.png": return new Carta('J', Palo.d);
        case "jack_of_hearts2.png": return new Carta('J', Palo.h);
        case "jack_of_spades2.png": return new Carta('J', Palo.s);

        
        // - - - - - - KING - - - - - -
        case "king_of_clubs2.png": return new Carta('K', Palo.c);
        case "king_of_diamonds2.png": return new Carta('K', Palo.d);
        case "king_of_hearts2.png": return new Carta('K', Palo.h);
        case "king_of_spades2.png": return new Carta('K', Palo.s);

        
        // - - - - - - QUEEN - - - - - -
        case "queen_of_clubs2.png": return new Carta('Q', Palo.c);
        case "queen_of_diamonds2.png": return new Carta('Q', Palo.d);
        case "queen_of_hearts2.png": return new Carta('Q', Palo.h);
        case "queen_of_spades2.png": return new Carta('Q', Palo.s);

        
        // - - - - - - JOKERS - - - - - -
        case "black_joker.png": return null;
        case "red_joker.png": return null;
		}
		return null;
		
	}
}
