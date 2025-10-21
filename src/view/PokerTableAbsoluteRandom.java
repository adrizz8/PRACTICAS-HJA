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

        setTitle("Mesa de Póker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null); // 🔹 AbsoluteLayout
        getContentPane().setBackground(new Color(0, 100, 0)); // verde tipo mesa

        // 🔹 Crear jugadores (cada uno con su panel y cuadro de equity)
        JPanel player1 = createPlayerWithEquity("Jugador 1");
        JPanel player2 = createPlayerWithEquity("Jugador 2");
        JPanel player3 = createPlayerWithEquity("Jugador 3");
        JPanel player4 = createPlayerWithEquity("Jugador 4");
        JPanel player5 = createPlayerWithEquity("Hero");
        JPanel player6 = createPlayerWithEquity("Jugador 6");

        // 🔹 Posiciones (panel completo jugador + equity)
        player1.setBounds(350, 20, 150, 140);   // arriba centro
        player2.setBounds(700, 130, 150, 140);  // derecha arriba
        player3.setBounds(700, 330, 150, 140);  // derecha abajo
        player4.setBounds(350, 440, 150, 140);  // abajo centro
        player5.setBounds(50, 330, 150, 140);   // izquierda abajo
        player6.setBounds(50, 130, 150, 140);   // izquierda arriba

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

    /**
     * Crea un panel que contiene al jugador (cartas) y debajo un cuadro de equity.
     */
    private JPanel createPlayerWithEquity(String name) {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setOpaque(false); // dejar fondo transparente (verde del tapete)

        // 🔹 Panel de jugador con cartas
        JPanel playerPanel = createPlayerPanel(name);
        container.add(playerPanel, BorderLayout.CENTER);

        // 🔹 Cuadro de equity (debajo del panel)
        JTextField equityField = new JTextField("Equity: 0.0%");
        equityField.setEditable(false);
        equityField.setHorizontalAlignment(JTextField.CENTER);
        equityField.setFont(new Font("SansSerif", Font.BOLD, 12));
        equityField.setBackground(new Color(240, 240, 240));
        container.add(equityField, BorderLayout.SOUTH);

        return container;
    }

    /**
     * Crea el panel con las dos cartas aleatorias del jugador.
     */
    private JPanel createPlayerPanel(String name) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(34, 139, 34));
        panel.setBorder(BorderFactory.createTitledBorder(name));

        ArrayList<Carta> cartasJugador = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int num = this._cartaAleatoriaImagen.getRandomNumber();
            String nombre = this._cartaAleatoriaImagen.eleccionCartaAleatoriaImagen(num);
            cartasJugador.add(this._cartaAleatoria.eleccionCartaAleatoria(nombre));

            JLabel c = new JLabel(new ImageIcon(loadImage(nombre)));
            c.setPreferredSize(new Dimension(60, 90));
            panel.add(c);
        }

        // puedes almacenar la mano si luego quieres calcular equity real
        Mano manoJugador = new Mano(cartasJugador);
        return panel;
    }

    private Image loadImage(String img) {
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
            System.err.println("No se pudo cargar imagen: " + img);
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PokerTableAbsoluteRandom::new);
    }
}
