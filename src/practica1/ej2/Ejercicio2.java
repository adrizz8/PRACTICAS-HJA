package practica1.ej2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import practica1.model.Carta;
import practica1.model.Mano;
import practica1.model.Palo;

public class Ejercicio2 {

    public static void main(String[] args) {

        if (args.length < 3) {
            System.out.println("$> java –jar nombreProyecto.jar 2 entrada2.txt salida2.txt");
            return;
        }

        String rutaEntrada = args[1];
        String rutaSalida = args[2];

        try (
            BufferedReader br = new BufferedReader(new FileReader(rutaEntrada));
            BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String l = linea.trim();
                if (l.isEmpty()) continue;

                // ---- Parsear ----
                String[] partes = l.split(";");
                String cartasJugadorStr = partes[0];
                int n = Integer.parseInt(partes[1]);
                String cartasComunesStr = partes[2];

                List<Carta> cartas = new ArrayList<>();

                // 2 cartas del jugador
                for (int i = 0; i < cartasJugadorStr.length(); i += 2) {
                    cartas.add(crearCarta(cartasJugadorStr.charAt(i), cartasJugadorStr.charAt(i + 1)));
                }

                // n cartas comunes
                for (int i = 0; i < cartasComunesStr.length(); i += 2) {
                    cartas.add(crearCarta(cartasComunesStr.charAt(i), cartasComunesStr.charAt(i + 1)));
                }

                // evaluamos la mano
                Mano m = new Mano(cartas);
                String mejorJugada = m.mejorJugada();

                // escribimos la salida
                bw.write(l);
                bw.newLine();
                bw.write("- Best hand: " + mejorJugada);
                bw.newLine();

                if (n < 5) {
                    for (String d : m.detectarDraws()) {
                        bw.write("- Draw: " + d);
                        bw.newLine();
                    }
                }

                bw.newLine(); // línea en blanco
            }

            //System.out.println("Resultados escritos en " + rutaSalida);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Carta crearCarta(char valor, char palo) {
        Palo p = null;
        if (palo == 'h') p = Palo.h;
        else if (palo == 'd') p = Palo.d;
        else if (palo == 's') p = Palo.s;
        else if (palo == 'c') p = Palo.c;
        return new Carta(valor, p);
    }
}