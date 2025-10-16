package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import model.CartaAleatoria;
import model.CartaAleatoriaImagen;

public class PokerTableAbsoluteChoise extends JFrame {

    private CartaAleatoriaImagen _cartaAleatoriaImagen;
    private CartaAleatoria _cartaAleatoria;

    public PokerTableAbsoluteChoise() {
        this._cartaAleatoria = new CartaAleatoria();
        this._cartaAleatoriaImagen = new CartaAleatoriaImagen();

        setTitle("Mesa de Póker - Aleatorias y modificables");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(0, 100, 0));

        // Crear paneles de jugadores
        JPanel player1 = createPlayerPanel("Jugador 1");
        JPanel player2 = createPlayerPanel("Jugador 2");
        JPanel player3 = createPlayerPanel("Jugador 3");
        JPanel player4 = createPlayerPanel("Jugador 4");
        JPanel player5 = createPlayerPanel("Jugador 5");
        JPanel player6 = createPlayerPanel("Jugador 6");

        // Posiciones manuales
        player1.setBounds(350, 30, 150, 100);
        player2.setBounds(700, 150, 150, 100);
        player3.setBounds(700, 350, 150, 100);
        player4.setBounds(350, 450, 150, 100);
        player5.setBounds(50, 350, 150, 100);
        player6.setBounds(50, 150, 150, 100);

        // Mesa central
        JPanel table = new JPanel();
        table.setBackground(new Color(0, 128, 0));
        table.setBorder(BorderFactory.createTitledBorder(""));
        table.setBounds(280, 180, 300, 200);

        // Añadir elementos
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

        // Dos cartas aleatorias por jugador
        for (int i = 0; i < 2; i++) {
            String nombre = generarCartaAleatoria(); // carta aleatoria
            JLabel c = new JLabel();
            c.setPreferredSize(new Dimension(60, 90));
            c.setIcon(new ImageIcon(loadImage(nombre)));

            // Clic -> cambiar carta
            c.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirSelectorDeCartas(c);
                }
            });

            panel.add(c);
        }

        return panel;
    }

    private String generarCartaAleatoria() {
        int num = _cartaAleatoriaImagen.getRandomNumber();
        return _cartaAleatoriaImagen.eleccionCartaAleatoriaImagen(num);
    }

    private void abrirSelectorDeCartas(JLabel cartaLabel) {
        JDialog selector = new JDialog(this, "Selecciona una carta", true);
        selector.setSize(800, 500);
        selector.setLayout(new BorderLayout());

        JPanel grid = new JPanel(new GridLayout(5, 11, 5, 5)); // 54 cartas aprox.
        grid.setBackground(new Color(0, 100, 0));
        JScrollPane scroll = new JScrollPane(grid);

        // Agregamos todas las cartas del mazo
        for (int i = 1; i <= 54; i++) {
            String nombre = _cartaAleatoriaImagen.eleccionCartaAleatoriaImagen(i);
            if (nombre == null) continue;
            Image img = loadImage(nombre);
            if (img == null) continue;

            Image scaled = img.getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            JLabel carta = new JLabel(new ImageIcon(scaled));
            carta.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            carta.setCursor(new Cursor(Cursor.HAND_CURSOR));

            carta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cartaLabel.setIcon(new ImageIcon(loadImage(nombre)));
                    selector.dispose();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    carta.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    carta.setBorder(BorderFactory.createLineBorder(Color.WHITE));
                }
            });

            grid.add(carta);
        }

        selector.add(scroll, BorderLayout.CENTER);
        selector.setLocationRelativeTo(this);
        selector.setVisible(true);
    }

    private Image loadImage(String img) {
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
            System.err.println("No se pudo cargar: " + img);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokerTableAbsoluteChoise::new);
    }
}
