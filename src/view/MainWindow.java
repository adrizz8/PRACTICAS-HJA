package view;

import javax.swing.*;
import control.Controller;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private Controller _ctrl;
    private PokerTable _pokerTable;
    private boolean isRandom;

    // GUI
    private JButton btnRandom;
    private JButton btnUsuario;
    private FondoPanel fondoPanel;

    public MainWindow(Controller ctrl) {
        super("Mesa Póker");
        _ctrl = ctrl;
        initGUI();
    }

    public void initGUI() {
        //Ventana
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana

        //Crear panel de fondo
        fondoPanel = new FondoPanel();
        fondoPanel.setLayout(new GridBagLayout());
        fondoPanel.setImagen("resources/icons/fondoInicial.png"); 
        setContentPane(fondoPanel);

        //Crear botones
        btnRandom = new JButton("CARTAS RANDOM");
        btnUsuario = new JButton("CARTAS A ELECCIÓN");

        //Aplicar estilo moderno a los botones
        estilizarBoton(btnRandom);
        estilizarBoton(btnUsuario);

        //Crear constraints para centrar los botones
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20); // Espaciado entre botones

        //Añadir botones al panel
        gbc.gridx = 0;
        fondoPanel.add(btnRandom, gbc);

        gbc.gridx = 1;
        fondoPanel.add(btnUsuario, gbc);

        //Listeners
        btnRandom.addActionListener(e -> {
            isRandom = true;
            _ctrl.initJugadores(isRandom);
            _pokerTable = new PokerTable(_ctrl, MainWindow.this);
            setVisible(false);
        });

        btnUsuario.addActionListener(e -> {
            isRandom = false;
            _ctrl.initJugadores(isRandom);
            _pokerTable = new PokerTable(_ctrl, MainWindow.this);
            setVisible(false);
        });

        setVisible(true);
    }

    private void estilizarBoton(JButton boton) {
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setFont(new Font("SansSerif", Font.BOLD, 16));
        boton.setForeground(Color.WHITE);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        //Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setForeground(new Color(255, 215, 0)); // dorado suave
                boton.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setForeground(Color.WHITE);
                boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            }
        });
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
                // Escalar la imagen para que cubra toda la ventana
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
