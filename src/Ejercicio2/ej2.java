package Ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Clasesini.Carta;
import Clasesini.Mano;
import Clasesini.Palo;

public class ej2 {

    public static void main(String[] args) {

        String ruta = "src/Ejercicio2/entrada2.txt";
        String rutaSalida = "src/Ejercicio2/salida2.txt";
        

        try (BufferedReader br = new BufferedReader(new FileReader(ruta)) ; BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
            String linea;
            while ((linea = br.readLine()) != null) {

                String l = linea.trim();
                if (l.isEmpty()) continue;

                // Dividir por ;
                String[] partes = l.split(";");
                if (partes.length != 3) {
                    System.out.println("Línea inválida: " + l);
                    continue;
                }

                String cartasJugador = partes[0];   // AhAc
                int n = Integer.parseInt(partes[1]); // 3 o 5
                String cartasComunes = partes[2];   // QhJhTh

                if (cartasJugador.length() != 4 || cartasComunes.length() != 2 * n) {
                    System.out.println("Formato inválido en línea: " + l);
                    continue;
                }

                // Construir lista de cartas
                List<Carta> cartas = new ArrayList<>();

                // Cartas jugador
                for (int i = 0; i < cartasJugador.length(); i += 2) {
                    char valor = cartasJugador.charAt(i);
                    char palo = cartasJugador.charAt(i + 1);
                    Palo p = Palo.valueOf(palo + "");
                    cartas.add(new Carta(valor, p));
                }

                // Cartas comunes
                for (int i = 0; i < cartasComunes.length(); i += 2) {
                    char valor = cartasComunes.charAt(i);
                    char palo = cartasComunes.charAt(i + 1);
                    Palo p = Palo.valueOf(palo + "");
                    cartas.add(new Carta(valor, p));
                }

                // Crear mano y evaluarla
                Mano m = new Mano(cartas);

                bw.write(l);
                bw.newLine();
                bw.write("- Best hand: " + m.mejorJugada() + " with " + m.mostrar_Cartas_Jugada());
                bw.newLine();

                // En ejercicio 2: si n < 5, mostramos draws
                if (n < 5) {
                    bw.write(m.mostrarDraws());
                    bw.newLine();
                }

                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
