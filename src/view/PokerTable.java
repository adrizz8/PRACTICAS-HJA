package view;
import javax.imageio.ImageIO;
import javax.swing.*;

import control.Controller;
import misc.Pair;
import model.Carta;
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
        _ctrl.test();
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

    private JPanel createPlayerPanel(String name, Pair cartas) {
        // Panel interno con las cartas y el borde con el nombre del jugador
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        playerPanel.setBackground(new Color(34, 139, 34));
        playerPanel.setBorder(BorderFactory.createTitledBorder(name));

        // Obtiene las cartas
        Carta carta1 = cartas.getFirst();
        Carta carta2 = cartas.getSecond();

        // Carga y escala las imágenes
        Image img1 = loadImage(carta1.toString() + ".png");
        Image img2 = loadImage(carta2.toString() + ".png");
        int maxCardHeight = 80;
        ImageIcon icon1 = toScaledIcon(img1, maxCardHeight);
        ImageIcon icon2 = toScaledIcon(img2, maxCardHeight);
        JLabel c1 = new JLabel(icon1);
        JLabel c2 = new JLabel(icon2);
        playerPanel.add(c1);
        playerPanel.add(c2);

        // Campo de texto para equity (idéntico al que ya tenías)
        JTextField equityField = new JTextField("Equity: 0.0%");
        equityField.setEditable(false);
        equityField.setHorizontalAlignment(JTextField.CENTER);
        equityField.setFont(new Font("SansSerif", Font.BOLD, 12));
        equityField.setBackground(new Color(240, 240, 240));
        equityField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Contenedor principal igual que antes
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(new Color(34, 139, 34));
        container.add(playerPanel, BorderLayout.CENTER);
        container.add(equityField, BorderLayout.SOUTH);

        // 🔹 Solo si es el Hero, añadimos los cuadros de rango
        if (name.equalsIgnoreCase("Hero")) {
            // Subpanel vertical para equity + rangos
            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
            bottomPanel.setBackground(new Color(34, 139, 34));

            // Añadimos el equity al subpanel
            bottomPanel.add(equityField);
            bottomPanel.add(Box.createVerticalStrut(4));

            // Campo texto de rango manual
            JTextField rangeTextField = new JTextField();
            rangeTextField.setToolTipText("Introduce rango (ej: AKs+, 88+, A5s-A2s)");
            rangeTextField.setFont(new Font("SansSerif", Font.PLAIN, 12));

            
            
            // Campo texto de porcentaje
            JTextField rangePercentField = new JTextField();
            rangePercentField.setToolTipText("Introduce % del rango (ej: 25)");
            rangePercentField.setFont(new Font("SansSerif", Font.PLAIN, 12));

            // Mutual exclusión
            rangeTextField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                void toggle() {
                    boolean hasText = !rangeTextField.getText().trim().isEmpty();
                    rangePercentField.setEnabled(!hasText);
                }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
            });

            rangePercentField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                void toggle() {
                    boolean hasText = !rangePercentField.getText().trim().isEmpty();
                    rangeTextField.setEnabled(!hasText);
                }
                public void insertUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { toggle(); }
            });

            // Añadimos los dos campos al subpanel
            bottomPanel.add(rangeTextField);
            bottomPanel.add(Box.createVerticalStrut(2));
            bottomPanel.add(rangePercentField);

            // Sustituimos solo la parte inferior (equity + rangos)
            container.remove(equityField);
            container.add(bottomPanel, BorderLayout.SOUTH);
        }

        // Tamaño preferido original (no tocamos nada)
        Dimension pPref = playerPanel.getPreferredSize();
        Dimension ePref = equityField.getPreferredSize();
        int pad = 16;
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