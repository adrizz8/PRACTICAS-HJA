package view;
import javax.swing.*;

import control.Controller;
import java.awt.*;


public class PokerTable extends JFrame {
	
	private static final long serialVersionUID = 1L;
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
        JPanel player1 = new PlayerPanel(0,"Jugador 1", _ctrl,this);
        JPanel player2 = new PlayerPanel(1,"Jugador 2", _ctrl,this);
        JPanel player3 = new PlayerPanel(2,"Jugador 3", _ctrl,this);
        JPanel player4 = new PlayerPanel(3,"Jugador 4", _ctrl,this);
        JPanel player5 = new PlayerPanel(4,"Hero", _ctrl,this);
        JPanel player6 = new PlayerPanel(5,"Jugador 6", _ctrl,this);

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
    
}