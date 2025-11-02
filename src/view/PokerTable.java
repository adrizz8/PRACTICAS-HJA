package view;
import javax.swing.*;

import control.Controller;
import model.Jugador;
import model.Carta;
import java.util.List;

import java.awt.*;
import java.util.Map;


public class PokerTable extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private PlayerPanel[] players;
	private JPanel table;
	private JPanel boardPanel;
	
    public PokerTable(Controller ctrl) {
    	super("Mesa de Poker");
    	_ctrl = ctrl;
    	initGUI();     
    }
    
    private void actualizarEquities(Map<Jugador, Double> equities) {
        int i = 0;
        for (Jugador j : _ctrl.getMesa().getListaJugadores()) {
            double eq = equities.getOrDefault(j, 0.0);
            players[i].setEquity(eq);
            i++;
        }
    }
    
    private void actualizarBoard(List<Carta> board) {
        boardPanel.removeAll();
        for (Carta c : board) {
            String imgPath = "resources/icons/" + c.toString() + ".png";
            ImageIcon icon = new ImageIcon(new ImageIcon(imgPath)
                .getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH));
            boardPanel.add(new JLabel(icon));
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }


    
    private void initGUI() {
    	//setTitle("Mesa de Póker"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(null); // 🔹 AbsoluteLayout
        getContentPane().setBackground(new Color(0, 100, 0)); // verde tipo mesa

        players = new PlayerPanel[] {
        	    new PlayerPanel(0,"Jugador 1", _ctrl,this),
        	    new PlayerPanel(1,"Jugador 2", _ctrl,this),
        	    new PlayerPanel(2,"Jugador 3", _ctrl,this),
        	    new PlayerPanel(3,"Jugador 4", _ctrl,this),
        	    new PlayerPanel(4,"Hero", _ctrl,this),
        	    new PlayerPanel(5,"Jugador 6", _ctrl,this)
        	};
        // 🔹 Posiciones manuales (rectángulo)
        // Use los tamaños preferidos de cada contenedor para no cortar las cartas
        Dimension d1 = players[0].getPreferredSize();
        Dimension d2 = players[1].getPreferredSize();
        Dimension d3 = players[2].getPreferredSize();
        Dimension d4 = players[3].getPreferredSize();
        Dimension d5 = players[4].getPreferredSize();
        Dimension d6 = players[5].getPreferredSize();
        

        players[0].setBounds(350, 30, d1.width, d1.height);   // arriba centro
        players[1].setBounds(700, 150, d2.width, d2.height);  // derecha arriba
        players[2].setBounds(700, 350, d3.width, d3.height);  // derecha abajo
        players[3].setBounds(350, 450, d4.width, d4.height);  // abajo centro
        players[4].setBounds(50, 350, d5.width, d5.height);   // izquierda abajo
        players[5].setBounds(50, 150, d6.width, d6.height);   // izquierda arriba

        table = new JPanel();
        table.setBackground(new Color(0, 128, 0));
        table.setBorder(BorderFactory.createTitledBorder("Mesa"));
        table.setBounds(280, 180, 300, 200);
        table.setLayout(new BorderLayout());

        // Panel interno para las cartas del board
        boardPanel = new JPanel();
        boardPanel.setOpaque(false);
        boardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        table.add(boardPanel, BorderLayout.CENTER);

        // Añadir todos los elementos
        add(players[0]);
        add(players[1]);
        add(players[2]);
        add(players[3]);
        add(players[4]);
        add(players[5]);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton siguienteFase = new JButton("Siguiente Fase");
        siguienteFase.addActionListener(e -> {
            Map<Jugador, Double> equities = _ctrl.siguienteFase();
            actualizarEquities(equities);
            actualizarBoard(_ctrl.getBoard());
        });
        buttonPanel.add(siguienteFase);

        table.add(boardPanel, BorderLayout.CENTER);
        table.add(buttonPanel, BorderLayout.SOUTH);
        
        add(table);
        
        

        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}