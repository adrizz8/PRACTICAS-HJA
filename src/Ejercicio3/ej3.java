package Ejercicio3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Clasesini.Carta;
import Clasesini.Jugador;
import Clasesini.Mano;
import Clasesini.Palo;

public class ej3 {
	

    public static void main(String[] args) {

        String ruta = "src/Ejercicio3/test3.txt";
        String rutaSalida = "src/Ejercicio3/salida3.txt";

        List<Jugador> Jugadores = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(ruta)); BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
       
            String linea;
            while ((linea = br.readLine()) != null) {

                String l = linea.trim();
                if (l.isEmpty()) continue;

                // Dividir por ;
                String[] partes = l.split(";");
                int N = Integer.parseInt(partes[0]); // nº de jugadores
                String cartasComunes = partes[partes.length - 1]; // siempre la última parte son las cartas comunitarias

                if (cartasComunes.length() != 10) {
                    System.out.println("Formato inválido en línea: " + l);
                    continue;
                }

                // Convertir cartas comunes
                List<Carta> comunes = new ArrayList<>();
                for (int i = 0; i < cartasComunes.length(); i += 2) {
                    char valor = cartasComunes.charAt(i);
                    char palo = cartasComunes.charAt(i + 1);
                    Palo p = Palo.valueOf(palo + "");
                    comunes.add(new Carta(valor, p));
                }

                // Mostrar línea original
                bw.write(l);
                bw.newLine();

                // Iterar sobre los jugadores
                for (int j = 1; j <= N; j++) {
                    String jugadorInfo = partes[j]; // Ejemplo: "J1AhAc"
                    String id = jugadorInfo.substring(0, 2); // "J1"
                    String cartasJugador = jugadorInfo.substring(2); // "AhAc"

                    // Parsear cartas del jugador
                    List<Carta> cartas = new ArrayList<>(comunes); // empezamos con comunes
                    for (int i = 0; i < cartasJugador.length(); i += 2) {
                        char valor = cartasJugador.charAt(i);
                        char palo = cartasJugador.charAt(i + 1);
                        Palo p = Palo.valueOf(palo + "");
                        cartas.add(new Carta(valor, p));
                    }

                    // Crear mano
                    Mano m = new Mano(cartas);
                    Jugador jugador = new Jugador(id, m);
                    
                    Jugadores.add(jugador);
                   
                }
                
                Jugadores.sort(Comparator.comparing(Jugador::getValorMano).reversed());
                
                //Mostrar resultado para el jugador
                for(int i = 0; i < N; i++) {
                	bw.write(Jugadores.get(i).getId() + ":  " + Jugadores.get(i).getMano().getString() + " " + Jugadores.get(i).getMejorJugada() + " with " + Jugadores.get(i).getMano().mostrar_Cartas_Jugada());
                	bw.newLine();
                }
                
                Jugadores.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
