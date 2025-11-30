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
	private BoardPanel bP;
	private MainWindow mainWindow;
	private JButton boton_siguiente;
	private Checkbox manualBet;
	private JButton betButton;
	private JButton NobetButton;
	
	
	
    public PokerTable(Controller ctrl, MainWindow mainWindow) {
    	super("Mesa de Poker");
    	_ctrl = ctrl;
    	this.mainWindow = mainWindow;
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
    


    private void initGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800); 
        setLayout(null);
        getContentPane().setBackground(new Color(0, 100, 0));

        players = new PlayerPanel[]{
            new PlayerPanel(0, "Jugador 1", _ctrl, this),
            new PlayerPanel(1, "Jugador 2", _ctrl, this),
            new PlayerPanel(2, "Jugador 3", _ctrl, this),
            new PlayerPanel(3, "Jugador 4", _ctrl, this),
            new PlayerPanel(4, "Hero", _ctrl, this),
            new PlayerPanel(5, "Jugador 6", _ctrl, this)
        };

        //MISMO TAMAÑO PARA TODOS LOS JUGADORES
        int playerWidth = 180;
        int playerHeight = 180;

        //JUGADORES
        players[0].setBounds(460, 30, playerWidth, playerHeight);   // arriba centro
        players[1].setBounds(880, 180, playerWidth, playerHeight);  // derecha arriba
        players[2].setBounds(880, 420, playerWidth, playerHeight);  // derecha abajo
        players[3].setBounds(460, 500, playerWidth, playerHeight);  // abajo centro
        players[4].setBounds(60, 420, playerWidth, playerHeight + 50); // HERO (más alto para q quepan los JTextField)
        players[5].setBounds(60, 180, playerWidth, playerHeight);   // izquierda arriba

        //MESA
        table = new JPanel();
        table.setBackground(new Color(0, 128, 0));
        table.setBorder(BorderFactory.createTitledBorder("Mesa"));
        table.setBounds(380, 280, 340, 200);
        table.setLayout(new BorderLayout());
        
        JLabel jugadorActual= new JLabel("PREFLOP");
        jugadorActual.setFont(new Font("SansSerif", Font.BOLD, 14));
        jugadorActual.setBounds(915, 20, 200, 25);
        
        //BOTON SIGUIENTE
        boton_siguiente= new JButton("Siguiente");
        boton_siguiente.setBounds(900, 50, 100, 25);
        
        manualBet= new Checkbox("Bet manual");
        manualBet.setFont(new Font("SansSerif", Font.BOLD, 14));
        manualBet.setBounds(900, 85, 200, 25);
        manualBet.setEnabled(false);
        
        betButton=new JButton("Bet/Call");
        betButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        betButton.setBounds(850, 120, 100, 25);

        NobetButton=new JButton("Fold");
        NobetButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        NobetButton.setBounds(950, 120, 100, 25);
        
        betButton.setEnabled(false);
		NobetButton.setEnabled(false);
        
        bP= new BoardPanel( _ctrl, this);
        
        //boardPanel = new JPanel();
        //boardPanel.setOpaque(false);
        //boardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        table.add(bP, BorderLayout.CENTER);

        //BUTTONS
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton siguienteFase = new JButton("Siguiente Fase");
        
        siguienteFase.addActionListener(e -> {
            /*Map<Jugador, Double> equities = _ctrl.siguienteFase();
            actualizarEquities(equities);
            actualizarBoard(_ctrl.getBoard());*/
        	
        	Map<Jugador, Double> equities = _ctrl.siguienteFase();
            if (equities == null) {
                mostrarFinDePartida();
                return;
            }
            actualizarEquities(equities);
            bP.actualizarBoard(_ctrl.getBoard());
        });
        
        boton_siguiente.addActionListener(e ->{
        	       
        	int siguiente=_ctrl.actualJugador();
        	if(siguiente!=-1) {
	    		if(!players[siguiente].Comprobar_equity()&&!players[siguiente].Comprobar_rangos()) {

	    			players[siguiente].accion();
	    			
	    			_ctrl.siguienteJugador();
	        		siguiente=_ctrl.actualJugador();
	        		if(siguiente!=-1)
	        			jugadorActual.setText(players[siguiente].getname());
	        		else
	        			jugadorActual.setText(_ctrl.getFase());
	    		}
        	}else {
        		fase();	
        		_ctrl.siguienteJugador();
        		siguiente=_ctrl.actualJugador();
        		
    			jugadorActual.setText(players[siguiente].getname());
        		manualBet.setEnabled(true);
        	}
        	
        });
        
        manualBet.addItemListener(e -> {
        	if(manualBet.getState()) {
        		betButton.setEnabled(true);
        		NobetButton.setEnabled(true);
        		boton_siguiente.setEnabled(false);
        	}else {
        		betButton.setEnabled(false);
        		NobetButton.setEnabled(false);
        		boton_siguiente.setEnabled(true);
        	}
        });
        
        betButton.addActionListener(e->{
        	
        	int siguiente=_ctrl.actualJugador();
        	if(!players[siguiente].Comprobar_equity()&&!players[siguiente].Comprobar_rangos()) {

    			players[siguiente].bet_call();
    			
    			_ctrl.siguienteJugador();
        		siguiente=_ctrl.actualJugador();
        		if(siguiente!=-1) 
        			jugadorActual.setText(players[siguiente].getname());
        		else{
        			jugadorActual.setText(_ctrl.getFase());
	        		intermedio();
        		}
    		}   	
        });
        
        NobetButton.addActionListener(e->{

        	int siguiente=_ctrl.actualJugador();
        	if(!players[siguiente].Comprobar_equity()&&!players[siguiente].Comprobar_rangos()) {

    			_ctrl.fold(siguiente);
    			players[siguiente].fold();
    						
    			_ctrl.siguienteJugador();
        		siguiente=_ctrl.actualJugador();
        		if(siguiente!=-1) 
        			jugadorActual.setText(players[siguiente].getname());
        		else{
        			jugadorActual.setText(_ctrl.getFase());
	        		intermedio();
        		}
    		}   	
        });
        
        JButton btnCalcularTurn = new JButton("Calculadora Turn");
        btnCalcularTurn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCalcularTurn.setBounds(900, 160, 150, 30);
        btnCalcularTurn.setToolTipText("Calcular si hacer Call o Fold en el Turn");

        btnCalcularTurn.addActionListener(e -> {
            // Verificar que estamos en el Turn
            if (!"TURN".equals(_ctrl.getFase())) {
                JOptionPane.showMessageDialog(this,
                    "La calculadora de Turn solo está disponible en la fase de Turn",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Abrir el diálogo de calculadora
            // Por defecto usamos el jugador 4 (Hero) pero puedes cambiarlo
            new TurnCalculatorDialog(this, _ctrl, 4).setVisible(true);
        });

        // Añadir el botón a la ventana
        add(btnCalcularTurn);
        
        //buttonPanel.add(siguienteFase);
        table.add(buttonPanel, BorderLayout.SOUTH);

        //Añadimos a la ventana todo
        for (PlayerPanel p : players)
            add(p);
        
        add(table);
        add(jugadorActual);
        add(boton_siguiente);
        add(manualBet);
        add(betButton);
        add(NobetButton);
        
    	//Boton para volver
    	JButton btnVolver = new JButton("Volver al menú principal");
    	btnVolver.setFocusPainted(false);
    	btnVolver.setFont(new Font("SansSerif", Font.BOLD, 12));
    	btnVolver.setBorderPainted(false);
    	btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

    	//cerramos mesa y abrimos MainWindow
    	btnVolver.addActionListener(e -> {
    		this._ctrl.reset();
    	    this.dispose();

    	    new MainWindow(_ctrl);
    	});

    	// Tamaño y margen del botón
    	final int btnW = 200;
    	final int btnH = 32;
    	final int margin = 40;

    	// Posicionar inicialmente en la esquina inferior derecha
    	btnVolver.setBounds(getWidth() - btnW - margin, getHeight() - btnH - margin - getInsets().bottom, btnW, btnH);
    	add(btnVolver);

    	// Recolocar el botón si la ventana cambia de tamaño (para que siga abajo-derecha)
    	this.addComponentListener(new java.awt.event.ComponentAdapter() {
    	    @Override
    	    public void componentResized(java.awt.event.ComponentEvent e) {
    	        Insets in = getInsets();
    	        int x = getWidth() - btnW - margin;
    	        int y = getHeight() - btnH - margin - in.bottom; // restar borde inferior
    	        btnVolver.setBounds(x, y, btnW, btnH);
    	    }
    	});
	
        	
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void intermedio() {
		// TODO Auto-generated method stub
    	manualBet.setState(false);
		manualBet.setEnabled(false);
		betButton.setEnabled(false);
		NobetButton.setEnabled(false);
		boton_siguiente.setEnabled(true);
	}

	public void EquityCambioCarta() {
    	  actualizarEquities(_ctrl.actualizarEquity());
          bP.actualizarBoard(_ctrl.getBoard());
    }
    private void mostrarFinDePartida() {
        JOptionPane.showMessageDialog(
            this,
            "FIN DE LA PARTIDA",
            "Juego terminado",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    private void fase() {
    	Map<Jugador, Double> equities = _ctrl.siguienteFase();
        _ctrl.QuitaApuesta();
        if (equities == null) {
            mostrarFinDePartida();
            return;
        }
        actualizarEquities(equities);
        bP.actualizarBoard(_ctrl.getBoard());
        
        for(int i = 0; i < players.length; i++) {
        	players[i].reset();
        }
        
    }
}
