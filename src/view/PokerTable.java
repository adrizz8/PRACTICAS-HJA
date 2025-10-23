package view;
import javax.imageio.ImageIO;
import javax.swing.*;

import control.Controller;
import misc.Pair;
import model.Carta;
import model.CartaAleatoria;
import model.CartaAleatoriaImagen;
import model.Mano;
import model.Mesa;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PokerTable extends JFrame {
	
	private Controller _ctrl;
	
    public PokerTable(Controller ctrl) {
    	super("Mesa de Poker");
    	_ctrl = ctrl;
    	initGUI();     
    }
    
    private void initGUI() {
    	//setTitle("Mesa de Póker"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null); // 🔹 AbsoluteLayout
        getContentPane().setBackground(new Color(0, 100, 0)); // verde tipo mesa

        // Crear paneles de jugadores    
        JPanel player1 = createPlayerPanel("Jugador 1", _ctrl.getCartasJugador(0));
        JPanel player2 = createPlayerPanel("Jugador 2", _ctrl.getCartasJugador(1));
        JPanel player3 = createPlayerPanel("Jugador 3", _ctrl.getCartasJugador(2));
        JPanel player4 = createPlayerPanel("Jugador 4", _ctrl.getCartasJugador(3));
        JPanel player5 = createPlayerPanel("Hero", _ctrl.getCartasJugador(4));
        JPanel player6 = createPlayerPanel("Jugador 6", _ctrl.getCartasJugador(5));

        // 🔹 Posiciones manuales (rectángulo)
        player1.setBounds(350, 30, 150, 100);   // arriba centro
        player2.setBounds(700, 150, 150, 100);  // derecha arriba
        player3.setBounds(700, 350, 150, 100);  // derecha abajo
        player4.setBounds(350, 450, 150, 100);  // abajo centro
        player5.setBounds(50, 350, 150, 100);   // izquierda abajo
        player6.setBounds(50, 150, 150, 100);   // izquierda arriba

        // 🔹 Panel central (mesa)
        JPanel table = new JPanel();
        table.setBackground(new Color(0, 128, 0));
        table.setBorder(BorderFactory.createTitledBorder("Mesa"));
        table.setBounds(280, 180, 300, 200);

        // Añadir todos los elementos
        add(player1);
        add(player2);
        add(player3);
        add(player4);
        add(player5);
        add(player6);
        add(table);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPlayerPanel(String name, Pair<Carta> cartas) {
    	//Crea mesa con todas las cartas

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(34, 139, 34));
        panel.setBorder(BorderFactory.createTitledBorder(name));      
        	
        //Muestra la primera carta
        Carta carta1 = cartas.getFirst();
        Carta carta2 = cartas.getSecond();
        
        //Muestra al carta
        JLabel c = new JLabel(new ImageIcon(loadImage(carta1.toString() + ".png")));
        JLabel c2 = new JLabel(new ImageIcon(loadImage(carta2.toString() + ".png")));
        c.setFont(new Font("SansSerif", Font.PLAIN, 36));
        c.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        c2.setFont(new Font("SansSerif", Font.PLAIN, 36));
        c2.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(c);
        panel.add(c2);
            

        return panel;
    }
    
    private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
}
