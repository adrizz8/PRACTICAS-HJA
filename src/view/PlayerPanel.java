package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import control.Controller;
import misc.Pair;
import model.Carta;

public class PlayerPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jugador;
	private Pair cartas;
	private String name;
	private Controller _ctrl;
	private JFrame padre;
	private JTextField equityField;
	
	
	
	public PlayerPanel (int jugador,String name, Controller ctrl,JFrame padre){
		this.padre=padre;
		this._ctrl=ctrl;
		this.jugador=jugador;
		this.name=name;
		this.cartas=_ctrl.getCartasJugador(jugador);
		initP();
		
	}
	
	public void setEquity(double equity) {
	    equityField.setText(String.format("Equity: %.1f%%", equity));
	}
	
	private void initP() {
		
		
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

        c1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String vieja_ruta= cartas.getFirst().toString()+".png";
             
                new MatrizCambio(padre,_ctrl,c1).setVisible(true);
                
                String nueva_ruta=(String) c1.getClientProperty("nuevaCarta");
                c1.putClientProperty("nuevaCarta",null);
                
                if(nueva_ruta!=null) {
	                _ctrl.modificarJugador(jugador,0,nueva_ruta,vieja_ruta);
	                cartas=_ctrl.getCartasJugador(jugador);
                }
            }
        });
        c2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	 String vieja_ruta= cartas.getSecond().toString()+".png";
                 
                 new MatrizCambio(padre,_ctrl,c2).setVisible(true);
                 
                 String nueva_ruta=(String) c2.getClientProperty("nuevaCarta");
                 c2.putClientProperty("nuevaCarta",null);
                 
                 if(nueva_ruta!=null) {
 	                _ctrl.modificarJugador(jugador,1,nueva_ruta,vieja_ruta);
 	                cartas=_ctrl.getCartasJugador(jugador);
                 }
                
                
            }
        });
        
        // Campo de texto para equity (idéntico al que ya tenías)
        equityField = new JTextField("Equity: 0.0%");
        equityField.setEditable(false);
        equityField.setHorizontalAlignment(JTextField.CENTER);
        equityField.setFont(new Font("SansSerif", Font.BOLD, 12));
        equityField.setBackground(new Color(240, 240, 240));
        equityField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(34, 139, 34));
        this.add(playerPanel, BorderLayout.CENTER);
        this.add(equityField, BorderLayout.SOUTH);

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
            this.remove(equityField);
            this.add(bottomPanel, BorderLayout.SOUTH);
        }

        // Tamaño preferido original (no tocamos nada)
        Dimension pPref = playerPanel.getPreferredSize();
        Dimension ePref = equityField.getPreferredSize();
        int pad = 16;
        this.setPreferredSize(new Dimension(pPref.width + pad, pPref.height + ePref.height + pad));   
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
