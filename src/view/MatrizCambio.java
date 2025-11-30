package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import control.Controller;
import model.Carta;

public class MatrizCambio extends JDialog {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	
	public MatrizCambio(JFrame parent,Controller ctrl,JLabel label) {
		super(parent, "Cartas en la baraja", true); 
		this._ctrl=ctrl;
		
		initGUI(label);
	}

	private void initGUI(JLabel origen) {
        setLayout(new GridLayout(0, 10, 5, 5)); // espaciado opcional

        for (Carta carta : _ctrl.getlistaCartas()) {
        	String ruta=carta.toString() + ".png";
        	
            // Escalamos la imagen si es necesario
        	Image imagenEscalada = loadImage(ruta);
            JLabel label = new JLabel(new ImageIcon(imagenEscalada));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            label.putClientProperty("nombreArchivo", ruta);
            
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Detectar doble clic
                    if (e.getClickCount() == 1) {
                        JLabel lbl = (JLabel) e.getSource();
                        ImageIcon icon = (ImageIcon) lbl.getIcon();  
                        
                        // Recuperamos el nombre de la carta
                        String nombre = (String) lbl.getClientProperty("nombreArchivo");
                        
                        
                        origen.setIcon(icon);
                        origen.putClientProperty("nuevaCarta", nombre);
                        
                        dispose();
                    }
                }
            });
            
            add(label);
        }

        pack();
        setLocationRelativeTo(null); // centrar
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
