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
import java.awt.image.BufferedImage;

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
        // Use los tamaños preferidos de cada contenedor para no cortar las cartas
        Dimension d1 = player1.getPreferredSize();
        Dimension d2 = player2.getPreferredSize();
        Dimension d3 = player3.getPreferredSize();
        Dimension d4 = player4.getPreferredSize();
        Dimension d5 = player5.getPreferredSize();
        Dimension d6 = player6.getPreferredSize();

        player1.setBounds(350, 30, d1.width, d1.height);   // arriba centro
        player2.setBounds(700, 150, d2.width, d2.height);  // derecha arriba
        player3.setBounds(700, 350, d3.width, d3.height);  // derecha abajo
        player4.setBounds(350, 450, d4.width, d4.height);  // abajo centro
        player5.setBounds(50, 350, d5.width, d5.height);   // izquierda abajo
        player6.setBounds(50, 150, d6.width, d6.height);   // izquierda arriba

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
        // Panel interno con las cartas y el borde con el nombre del jugador
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        playerPanel.setBackground(new Color(34, 139, 34));
        playerPanel.setBorder(BorderFactory.createTitledBorder(name));

        // Obtiene las cartas
        Carta carta1 = cartas.getFirst();
        Carta carta2 = cartas.getSecond();

        // Crea las imágenes de las cartas
        // Cargamos las imágenes y las escalamos si son muy altas para evitar cortes
        Image img1 = loadImage(carta1.toString() + ".png");
        Image img2 = loadImage(carta2.toString() + ".png");
        int maxCardHeight = 80; // altura máxima deseada para las cartas
        ImageIcon icon1 = toScaledIcon(img1, maxCardHeight);
        ImageIcon icon2 = toScaledIcon(img2, maxCardHeight);
        JLabel c1 = new JLabel(icon1);
        JLabel c2 = new JLabel(icon2);
         playerPanel.add(c1);
         playerPanel.add(c2);

         // Campo de texto para equity (debajo del panel del jugador)
         JTextField equityField = new JTextField("Equity: 0.0%");
         equityField.setEditable(false);
         equityField.setHorizontalAlignment(JTextField.CENTER);
         equityField.setFont(new Font("SansSerif", Font.BOLD, 12));
         equityField.setBackground(new Color(240, 240, 240));
         equityField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

         // Panel contenedor que coloca el panel del jugador arriba y el equity debajo
         JPanel container = new JPanel();
         container.setLayout(new BorderLayout());
         container.setBackground(new Color(34, 139, 34));

         container.add(playerPanel, BorderLayout.CENTER);
         container.add(equityField, BorderLayout.SOUTH);

         // Ajustar tamaño preferido del contenedor según el tamaño real de las cartas + campo equity
         Dimension pPref = playerPanel.getPreferredSize();
         Dimension ePref = equityField.getPreferredSize();
         int pad = 16; // margen extra para el título/borde
         container.setPreferredSize(new Dimension(pPref.width + pad, pPref.height + ePref.height + pad));

         return container;
     }

    // Helper que convierte una Image en ImageIcon escalada manteniendo proporción.
    private ImageIcon toScaledIcon(Image img, int maxHeight) {
        if (img == null) {
            // placeholder
            BufferedImage placeholder = new BufferedImage(50, maxHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = placeholder.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, placeholder.getWidth(), placeholder.getHeight());
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, placeholder.getWidth()-1, placeholder.getHeight()-1);
            g.dispose();
            return new ImageIcon(placeholder);
        }
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        if (h <= 0 || w <= 0) {
            return new ImageIcon(img);
        }
        if (h <= maxHeight) {
            return new ImageIcon(img);
        }
        double scale = (double) maxHeight / (double) h;
        int newW = (int) Math.round(w * scale);
        int newH = (int) Math.round(h * scale);
        Image scaled = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
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