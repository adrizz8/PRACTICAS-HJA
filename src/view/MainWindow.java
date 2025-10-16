package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnRandom;
    private JButton btnUsuario;
    private FondoPanel fondoPanel;
    private PokerTableAbsoluteRandom _pokerTableAbsoluteRandom;

    public MainWindow() {
        // Configuración de la ventana
        setTitle("Mesa Póker");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        // Crear panel de fondo
        fondoPanel = new FondoPanel();
        fondoPanel.setLayout(new GridBagLayout());
        setContentPane(fondoPanel);

        // Crear botones
        btnRandom = new JButton("CARTAS RANDOM");
        btnUsuario = new JButton("CARTAS A ELECCION");

        // Igualar tamaño de los botones
        Dimension buttonSize = new Dimension(160, 40);
        btnRandom.setPreferredSize(buttonSize);
        btnUsuario.setPreferredSize(buttonSize);

        // Crear constraints para colocarlos con espacio
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre botones

        // Agregar botón Random
        gbc.gridx = 0;
        fondoPanel.add(btnRandom, gbc);

        // Agregar botón Usuario
        gbc.gridx = 1;
        fondoPanel.add(btnUsuario, gbc);

        // ActionListeners preparados
        btnRandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	_pokerTableAbsoluteRandom = new PokerTableAbsoluteRandom();
            	setVisible(false);
            }
        });

        btnUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: lógica para jugar con cartas del usuario
                // pedirCartasUsuario();
            }
        });
    }

    /**
     * Método para establecer una imagen de fondo en la ventana.
     * @param rutaImagen Ruta local o URL de la imagen
     */
    public void setFondo(String rutaImagen) {
        fondoPanel.setImagen(rutaImagen);
        fondoPanel.repaint();
    }

    // Clase interna para el panel con fondo
    private static class FondoPanel extends JPanel {
        private Image imagen;

        public void setImagen(String ruta) {
            ImageIcon icon = new ImageIcon(ruta);
            this.imagen = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagen != null) {
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow gui = new MainWindow();
            // Ejemplo de cómo poner fondo (puedes cambiar la ruta)
            // gui.setFondo("src/imagenes/fondo.jpg");
            gui.setVisible(true);
        });
    }
}
