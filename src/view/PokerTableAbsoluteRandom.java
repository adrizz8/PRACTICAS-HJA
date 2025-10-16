package view;
import javax.imageio.ImageIO;
import javax.swing.*;

import model.Carta;
import model.CartaAleatoria;
import model.CartaAleatoriaImagen;
import model.Mano;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PokerTableAbsoluteRandom extends JFrame {
	
	private CartaAleatoriaImagen _cartaAleatoriaImagen;
	private CartaAleatoria _cartaAleatoria;
	private ArrayList<Carta> _listaCartas;

    public PokerTableAbsoluteRandom() {
    	this._cartaAleatoria = new CartaAleatoria();
    	this._cartaAleatoriaImagen = new CartaAleatoriaImagen();
    	
        setTitle("Mesa de Póker "); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null); // 🔹 AbsoluteLayout
        getContentPane().setBackground(new Color(0, 100, 0)); // verde tipo mesa

        // Crear paneles de jugadores    
        
        JPanel player1 = createPlayerPanel("");
        JPanel player2 = createPlayerPanel("");
        JPanel player3 = createPlayerPanel("");
        JPanel player4 = createPlayerPanel("");
        JPanel player5 = createPlayerPanel("");
        JPanel player6 = createPlayerPanel("");

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
        table.setBorder(BorderFactory.createTitledBorder(""));
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

    private JPanel createPlayerPanel(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(34, 139, 34));
        panel.setBorder(BorderFactory.createTitledBorder(name));

        ArrayList<Carta> cartasJugador1 = new ArrayList<Carta>();
        String nombre = null;
        for(int i = 0; i < 2; i++)  {
        int num = this._cartaAleatoriaImagen.getRandomNumber(); //generamos numero aleatorio
        nombre = this._cartaAleatoriaImagen.eleccionCartaAleatoriaImagen(num); //elige la carta aleatoria
        cartasJugador1.add(this._cartaAleatoria.eleccionCartaAleatoria(nombre));
        
        // Dos cartas (puedes reemplazarlas por imágenes reales)
        JLabel c = new JLabel(new ImageIcon(loadImage(nombre)));
        c.setFont(new Font("SansSerif", Font.PLAIN, 36));
        c.setBounds(0, 0, panel.getWidth(), panel.getHeight());
        panel.add(c);
        }
        
        Mano manoJugador = new Mano(cartasJugador1); //?????????????
     

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
