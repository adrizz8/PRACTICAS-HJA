package model;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.io.*;

import misc.Pair;

public class Jugador {
	
	private final int NUMEROS = 13;
	private Pair holeCards;
	private Mano mano;
	private Mesa mesa;
	private boolean fold;
	private int[][] matRangos = new int[NUMEROS][NUMEROS];
	private int[][] matRanking = new int[NUMEROS][NUMEROS];

	
	public Jugador(Mesa mesa, Pair holeCards) {
		this.mesa = mesa;
		this.holeCards = holeCards;
		//inicializar matriz
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				matRangos[i][j] = 0;
			}
		}
		///
	}
	
	public boolean enRango(List<String> strings) {
		boolean enRango = false;
		Iterator<String> it = strings.iterator();
		
		int c1 = holeCards.getHigh().getValorNumerico() - 2;
		int c2 = holeCards.getLow().getValorNumerico() - 2;
	
		//En la diagonal de la matriz se encuentran las parejas que forman pares,
		//y esta diagonal separa las parejas suited y las offsuit,
		//que su posicion en la matrid es su correspondiente posicion suited pero con la X y la Y invertidas.
		
		while(it.hasNext() && !enRango) {
			String str = new String(it.next());
			int i = charToValor(str.charAt(0)); //eje X
			int j = charToValor(str.charAt(1)); //eje Y
			
			///////////// COMANDO + /////////////
			
			if(str.contains("+")) {			
				
				if(i < j) { // el mayor es la i
					int aux = i;
					i = j;
					//j = i; //no seria j = aux????
					j = aux;
				}
				
				
				if(i == j) {
					while (i < 13) {
						matRangos[i][j] = 1;
						i++;
						j++;
					}
				}
				
				else {
					if(str.contains("s")) {
						while(j < i) {
							matRangos[i][j] = 1;
							j++;
						}	
					}
					
					else if(str.contains("o")){				
						while(j < i) {
							matRangos[j][i] = 1;
							j++;
						}
					}
					
					else {
						int jaux = j;
						int iaux = i;
						while(j < i) {
							matRangos[i][jaux] = 1;
							matRangos[j][iaux] = 1;
							jaux++;
							iaux++;
						}
						
					}
					
				}
				
			}
			
			/////////////////////////////////////
			
			///////////// COMANDO - /////////////
			else if(str.contains("-")) {
				str = str.replace("-", "");
				boolean suited = false;
				boolean offsuit = false;
				
				if(str.contains("o") || str.contains("s")) {
					if(str.contains("o")) {
						offsuit = true;
						str = str.replace("o", "");
					}
					
					else if(str.contains("s")) {
						suited = true;
						str = str.replace("s", "");
					}
				}
				
				int X = charToValor(str.charAt(0));
				int finY = charToValor(str.charAt(1));
				int iniY = charToValor(str.charAt(3));
				
				if(suited) {
						while(iniY <= finY) {
								matRangos[X][iniY] = 1;
								iniY++;
						}
				}
				
				
				else if(offsuit) {
						while(iniY <= finY) {
								matRangos[iniY][X] = 1;
								iniY++;
						}
				}
			}
			
			/////////////////////////////////////
			
			///////////// PAREJAS INDIVIDUALES /////////////
			else {
				if(str.contains("s")) {
					matRangos[i][j] = 1;
				}
				
				else if(str.contains("o")) {
					matRangos[j][i] = 1;
				}		
				
				else {
					matRangos[i][j] = 1;
					matRangos[j][i] = 1;
				}
			}
			
			/////////////////////////////////////
			
			//Comprueba que la posicion de la matriz correspondiente con la mano esta dentro del rango
			if(holeCards.getFirst().getPalo() == holeCards.getSecond().getPalo()) {
				if(matRangos[c1][c2] == 1) {
					enRango = true;
				}
			}
			
			else {
				if(matRangos[c2][c1] == 1) {
					enRango = true;
				}
			}
			
			
		}	
		
		
		//reestablecemos la matriz, yo habia hecho un metodo 
		//limpiar matriz que ponia todos sus valores a 0, supongo
		//que esto tambien valdra
		matRangos= new int[NUMEROS][NUMEROS];
		
		return enRango;
	}
	
	private int charToValor(char c) {
		switch (c) {
        case '2': return 0;
        case '3': return 1;
        case '4': return 2;
        case '5': return 3;
        case '6': return 4;
        case '7': return 5;
        case '8': return 6;
        case '9': return 7;
        case 'T': return 8;
        case 'J': return 9;
        case 'Q': return 10;
        case 'K': return 11;
        case 'A': return 12;
        default: return -1;
	    }
	}
	
	public Pair getCartas() {
		return holeCards;
	}
	
	public void setCarta1(Carta nueva_carta) {
		holeCards= new Pair(nueva_carta, holeCards.getSecond());
	}


	public void setCarta2(Carta nueva_carta) {
		holeCards= new Pair(holeCards.getFirst(),nueva_carta);
	
	}
	
	public void cargarRanking() {
		try (Scanner sc = new Scanner(new File("resources/rankings/Ranking_Sklansky-Chubukov.txt"))) {
		    for (int i = 0; i < NUMEROS; i++) {
		        for (int j = 0; j < NUMEROS; j++) {
		            matRanking[i][j] = sc.nextInt();
		        }
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public boolean enPorcentaje(double porcentaje) {
		Carta i = holeCards.getHigh();
		Carta j = holeCards.getLow();
		double percenMano;
		
		if(i.getPalo() == j.getPalo()) {
			 percenMano = matRanking[i.getValorNumerico()-2][j.getValorNumerico()-2]/1.69;
		}else {
			 percenMano = matRanking[j.getValorNumerico()-2][i.getValorNumerico()-2]/1.69;
		}
		
		if(percenMano <= porcentaje)
			return true;
		else
			return false;
	}

	public boolean getFold() {
		// TODO Auto-generated method stub
		return fold;
	}

	public void Fold() {
		// TODO Auto-generated method stub
		fold=true;
	}
}
