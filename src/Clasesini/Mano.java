package Clasesini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Mano {
		
	private List<Carta> mano;
	private String mejorjugada;
	private ArrayList<String> Draws;//Lista de Draws, luego se muestran
	
	private int Max_escalera;//Indica donde empieza la escalera para luego mostrarla
	Palo palo_color;//Indica el palo del color para luego mostrarlo

	//luego sirve para mostrar por pantalla
	//valor de carta y numero de veces que aparece, de nuestra mejor repeticion(metodo HighCard_to_Poker())
	private int val_aux1;
	private int cant_aux1;
	
	//valor de carta y numero de veces que aparece, de nuestra segunda mejor repeticion(metodo HighCard_to_Poker())
	private int val_aux2;
	private int cant_aux2;
	
	
	public Mano (List<Carta> m) {
		
		//Ordenamos por numero de carta para ahorrar en un futuro
		m.sort( Comparator.comparingInt((Carta c) -> c.getValorNumerico()));
		                      
		this.mano = m;
		Draws= new ArrayList<>();
		
	}


	public List<Carta> getMano() {
		return mano;
	}


	public void setMano(List<Carta> mano) {
		this.mano = mano;
	}

	
	 public void mostrarMano() {
	        
		 	System.out.print("Cartas del jugador: ");
	        for (Carta c : mano) {
	            System.out.print(c);
	        }
	        System.out.println();
	    }
	 
	 
	 public boolean esColor(List<Carta> m) {
		 
		 boolean escolor = true;
		 /*
		 int count=0;
		 Palo primerPalo = m.get(0).getPalo();
		 */ 
		 
		 Map<Palo, Integer> cuenta = new HashMap<>();
		
		 int num_max=0;
		 int num;
		 Palo pal;
		 
		 //Contamos cuantas cartas hay de cada palo para ver si hay Color
		 //y el palo mas repetido, por si luego hay que mostrar el Color
		 for (Carta c : mano) {
			 pal=c.getPalo();
			 num=cuenta.getOrDefault(pal, 0) + 1;
			 
			 if(num_max<num) {
				 num_max=num;
				 palo_color=pal;
			 }
			 
		     cuenta.put(pal,num);
		 }
		
		 Collection<Integer> valores = cuenta.values();
		 
		 //Vemos si hay Color y si no hay si hay Draw de Color
		 if(!valores.contains(5)&&!valores.contains(6)&&!valores.contains(7)) {
			 escolor=false;
			 if(valores.contains(4))
				 Draws.add("Color");
		 }
		 
		
		 /*
		  * Para 2h3h6s7h9hTh tenemos Color, pero aqui no sale
		  * 
		 for (Carta c : m) {
			 
			 if (c.getPalo() != primerPalo) {
				
				 escolor = false;
			 }
			 count++;
		 }
		 */
		 
		 
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

		    int primero;
		    int segundo;
		    int cont =0;//cuantos elementos seguidos hay
		    int cont_gutshot=0;//cuantos elementos de una escalera que le falta un numero por el medio hay
		    boolean gutshot=false;//ya hay un hueco es la escalera o no
		    for (int i = 0; i < valores.size()-1 ; i++) {
		    	
		    	primero=valores.get(i);
		    	segundo=valores.get(i+1);
		    	
		    	if(segundo-primero==1) {//sigue contando la escalera
		    		cont++;
		    		cont_gutshot++;
		    		
		    		if(cont>=4) { 
		    			esescalera=true;
		    			Max_escalera=segundo;
		    		}
		    		
		    	}else if(segundo-primero==2) {//Hay solo 2 de distancia, se corta la escalera
		    								  //pero puede haber Draw gutshot
		    		//Draw de escalera sin huecos 
		    		if(cont==3) Draws.add("Open-ended");
		    		cont=0;
		    		
		    		if(!gutshot) {//primer hueco en la escalera
			    		gutshot=true;
			    		cont_gutshot++;
		    		}else {//segundo hueco, gutshot no valida
		    			if(cont_gutshot>=3&&gutshot)Draws.add("Gutshot");
		    			gutshot=false;
		    			cont_gutshot=0;
		    		}
		    	}else if(segundo-primero>2) {//Mas de 2 num de distancia
		    		if(cont==3) Draws.add("Open-ended");
		    		cont=0;
		    		if(cont_gutshot>=3&&gutshot)Draws.add("Gutshot");
		    		cont_gutshot=0;
		    	}
		    	
		    }
		    
		    //comprobaciones necesarias
		    if(cont==3) Draws.add("Open-ended");
		    
    		if(cont_gutshot>=3&&gutshot)Draws.add("Gutshot");
    		
		    
		    /*
		     * Si hay parejas en medio de la escalera no funciona bien
		     * 2h3s3h4s5c6h
		    //Aqui se comprueba si hay 5 consecutivos
		    for (int i = 0; i <= valores.size() - 5; i++) {
		        int primero = valores.get(i);
		        int ultimo = valores.get(i + 4);
		        if (ultimo - primero == 4) {
		            esescalera = true;
		        }
		    }
		    */
    		
    		//Falta ver como mostrar este caso
		    // caso especial A,2,3,4,5
		    if (setValores.contains(14) && setValores.contains(2) &&
		        setValores.contains(3) && setValores.contains(4) &&
		        setValores.contains(5)) {
		        esescalera = true;
		        Max_escalera=5;
		    }

		    return esescalera;
		}
	 
	 	public void HighCard_to_Poker() {

		 
		 Map<Integer, Integer> cuenta = new HashMap<>();
	    //Miramos todos los numeros y cuantas veces aparecen
		for (Carta c : mano) {
		    cuenta.put(c.getValorNumerico(),cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		}
	 
	 
        for (Map.Entry<Integer, Integer> entry : cuenta.entrySet()) {
        	
        	//El nuevo numero de carta aparece mas veces que el mejor actual
            if(cant_aux1<entry.getValue()) {   
            	
            	//el mejor actual pasa al segundo mejor
            	val_aux2=val_aux1;
            	cant_aux2=cant_aux1;
            	
            	//el nuevo pasa a ser mejor actual
	    		val_aux1=entry.getKey();
	    		cant_aux1=entry.getValue();
	    		
	    	//el nuevo numero aparece el mismo numero de veces que el mejor actual
            }else if(cant_aux1==entry.getValue()) {
            	
            	//el valor de la carta nueva es mayor
            	if(val_aux1<entry.getKey()) {
            		val_aux2=val_aux1;
                	cant_aux2=cant_aux1;
                	
    	    		val_aux1=entry.getKey();
    	    		cant_aux1=entry.getValue();
            	}else {
            		//Comprobamos si hay que sustituir en la segunda mejor
            		if(cant_aux2<entry.getValue()) {     	
        	    		val_aux2=entry.getKey();
        	    		cant_aux2=entry.getValue();
        	    		
                    }else {
                    	if(val_aux2<entry.getKey()) {
            	    		val_aux2=entry.getKey();
            	    		cant_aux2=entry.getValue();
                    	}
                    }
            	}
            }
            //la nueva carta tiene menos apariciones que la mejor actual
            else {
            	//miramos si hay que sustituir en la segunda mejor
            	if(cant_aux2<entry.getValue()) {     	
    	    		val_aux2=entry.getKey();
    	    		cant_aux2=entry.getValue();
    	    		
                }else if(cant_aux2==entry.getValue()) {
                	if(val_aux2<entry.getKey()) {
        	    		val_aux2=entry.getKey();
        	    		cant_aux2=entry.getValue();
                	}
                }
            }
            
        }
	 }

	
	public String mejorJugada() {
		
		boolean escalera = esEscalera(mano);
		boolean color = esColor(mano);
		HighCard_to_Poker();
		
		
		/*
		 * No supe como mostrar los que forman las parejas y demas
		 * 
		Map<Integer, Integer> cuenta = new HashMap<>();
		
		for (Carta c : mano) {
		    cuenta.put(c.getValorNumerico(),cuenta.getOrDefault(c.getValorNumerico(), 0) + 1);
		}
		
		Collection<Integer> valores = cuenta.values();
		*/
		
		//cambiar escalera de color
		if (escalera && color) mejorjugada = "Escalera de color";
		else if (cant_aux1==4) mejorjugada = "Poker";
		else if (cant_aux1==3 && cant_aux2==2) mejorjugada = "Full House";
		else if (color) mejorjugada = "Color";
		else if (escalera) mejorjugada = "Escalera";
		else if (cant_aux1==3) mejorjugada = "Trío";
		else if (cant_aux1==2&&cant_aux2==2) mejorjugada = "Doble par";
		else if (cant_aux1==2) mejorjugada = "Par";
		else mejorjugada = "Carta alta";
		
		
		return mejorjugada;
		
	}


	public void mostrarDraws() {
		
		for(String aux: Draws) {
			System.out.println(" - Draw: "+aux);
		}
		
	}
	
	public String mostrar_Cartas_Jugada() {
		String cartas="";
		 switch (mejorjugada) {
		 	//Muestra las cartas de la escalera de mayor a menor
			 case"Escalera":{
				 
				 int siguiente = Max_escalera - 4;
				 int i = 0;
				 
				 //caso especial
				 if(siguiente==1) {
					 //como esta ordenada la mano en la ultima posicion siempre hay un as en la ultima posicion
					 cartas+=mano.get(mano.size()-1);
					 siguiente++;
				 }

				 while (i < mano.size() && siguiente <= Max_escalera) {
				     Carta aux = mano.get(i);

				     if (aux.getValorNumerico() == siguiente) {
				         cartas += aux.toString();
				         siguiente++;
				     }
				     i++;
				 }
				 break;
			 }
			//Muestra las cartas del Color de mayor a menor
			 case"Color":{
				 for(int i=mano.size()-1;i>=0;i--) {
					 if(mano.get(i).getPalo().ordinal()==palo_color.ordinal())
						 cartas+=mano.get(i).toString();
				 }
				 break;
			 }
			//Muestra las cartas de repeticiones
			 default: {
				 int i=0;
				 Carta aux;
				 
				 while(cant_aux1>0) {
					 aux=mano.get(i);
					 if(aux.getValorNumerico()==val_aux1) {
						 cant_aux1--;
						 cartas+=aux.toString();
					 }
					 
					 i++;
				 }
				 
				 if(cant_aux2>1) {
					 i=0;
					 while(cant_aux2>0) {
						 aux=mano.get(i);
						 if(aux.getValorNumerico()==val_aux2) {
							 cant_aux2--;
							 cartas+=aux.toString();
						 }		 
						 i++;
					 }
				 }	 
				 break;
			 }
		 }
		return cartas;
	}
	
}
