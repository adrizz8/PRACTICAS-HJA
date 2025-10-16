package model;

import java.util.Random;

public class CartaAleatoriaImagen {
	
	public CartaAleatoriaImagen() { }
	
	public String eleccionCartaAleatoriaImagen(int numero) {
		switch(numero) {
		// - - - - - - 2 - - - - - -
		case 1: return "2_of_clubs.png";
		case 2: return "2_of_diamonds.png";
		case 3: return "2_of_hearts.png";
		case 4: return "2_of_spades.png";
			
		
		// - - - - - - 3 - - - - - -
		case 5: return "3_of_clubs.png";
		case 6: return "3_of_diamonds.png";
		case 7: return "3_of_hearts.png";
		case 8: return "3_of_spades.png";
			
			
		// - - - - - - 4 - - - - - -
		case 9: return "4_of_clubs.png";
		case 10: return "4_of_diamonds.png";
		case 11: return "4_of_hearts.png";
		case 12: return "4_of_spades.png";
			
		
		// - - - - - - 5 - - - - - -
		case 13: return "5_of_clubs.png";
		case 14: return "5_of_diamonds.png";
		case 15: return "5_of_hearts.png";
		case 16: return "5_of_spades.png";
			
			
		// - - - - - - 6 - - - - - -			
		case 17: return "6_of_clubs.png";
		case 18: return "6_of_diamonds.png";
		case 19: return "6_of_hearts.png";
		case 20: return "6_of_spades.png";
			
			
		// - - - - - - 7 - - - - - -
		case 21: return "7_of_clubs.png";
		case 22: return "7_of_diamonds.png";
		case 23: return "7_of_hearts.png";
		case 24: return "7_of_spades.png";
			
			
		// - - - - - - 8 - - - - - -
		case 25: return "8_of_clubs.png";
		case 26: return "8_of_diamonds.png";
		case 27: return "8_of_hearts.png";
		case 28: return "8_of_spades.png";
			
			
		// - - - - - - 9 - - - - - -
		case 29: return "9_of_clubs.png";
		case 30: return "9_of_diamonds.png";
		case 31: return "9_of_hearts.png";
		case 32: return "9_of_spades.png";
			
			
		// - - - - - - 10 - - - - - -
		case 33: return "10_of_clubs.png";
		case 34: return "10_of_diamonds.png";
		case 35: return "10_of_hearts.png";
		case 36: return "10_of_spades.png";
			
			
		// - - - - - - AS - - - - - -
		case 37: return "ace_of_clubs.png";
		case 38: return "ace_of_diamonds.png";
		case 39: return "ace_of_hearts.png";
		case 40: return "ace_of_spades.png";
			
			
		// - - - - - - JACK - - - - - -
		case 41: return "jack_of_clubs2.png";
		case 42: return "jack_of_diamonds2.png";
		case 43: return "jack_of_hearts2.png";
		case 44: return "jack_of_spades2.png";
			
			
		// - - - - - - KING - - - - - -
		case 45: return "king_of_clubs2.png";
		case 46: return "king_of_diamonds2.png";
		case 47: return "king_of_hearts2.png";
		case 48: return "king_of_spades2.png";
			
			
		// - - - - - - QUEEN - - - - - -
		case 49: return "queen_of_clubs2.png";
		case 50: return "queen_of_diamonds2.png";
		case 51: return "queen_of_hearts2.png";
		case 52: return "queen_of_spades2.png";
			
			
		// - - - - - - JOKER - - - - - -
		case 53: return "black_joker.png";
		case 54: return "red_joker.png";
		}
		
		return null;
	}
	
	public int getRandomNumber() {
		Random random = new Random();
        return random.nextInt(52) + 1;
    }
}
