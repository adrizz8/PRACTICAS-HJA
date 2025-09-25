package practica1.ej1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import practica1.model.Carta;
import practica1.model.Mano;
import practica1.model.Palo;

public class Ejercicio1 {
	
public static void main(String[] args) {
        
        // Argumentos: nº apartado, fichero entrada, fichero salida
     	if (args.length < 3) {
            System.out.println("$> java –jar nombreProyecto.jar 1 entrada1.txt salida1.txt");
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
                List<Carta> cartas = new ArrayList<>();

                // Construir las 5 cartas a partir de la línea
                for (int i = 0; i + 1 < l.length(); i += 2) {
                    char valor = l.charAt(i);
                    char palo = l.charAt(i + 1);
                    Palo p = null;

                    if (palo == 'h') p = Palo.h;
                    else if (palo == 'd') p = Palo.d;
                    else if (palo == 's') p = Palo.s;
                    else if (palo == 'c') p = Palo.c;

                    Carta c = new Carta(valor, p);
                    cartas.add(c);
                }

                Mano m = new Mano(cartas);

                String mejorJugada = m.mejorJugada();

                // Detectar draws
                List<String> draws = m.detectarDraws();

                // Salida
                bw.write(l);
                bw.newLine();
                bw.write("- Best hand: " + mejorJugada);
                bw.newLine();
                for (String d : draws) {
                    bw.write("- Draw: " + d);
                    bw.newLine();
                }
                bw.newLine();
            }

            //System.out.println("Resultados escritos en " + rutaSalida);

        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
		
}
