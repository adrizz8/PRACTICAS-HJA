package view;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	private  JLabel c1;
	private  JLabel c2;
	private JTextField rangeTextField;
	private JTextField equityField;
	private JTextField equityPercent;
	private JTextField campoApuesta;
	private double _equity;
	private boolean _equitySuperada;
	private boolean _dentroRango;
	
	
	
	public PlayerPanel (int jugador,String name, Controller ctrl,JFrame padre){
		this.padre=padre;
		this._ctrl=ctrl;
		this.jugador=jugador;
		this.name=name;
		this.cartas=_ctrl.getCartasJugador(jugador);
		initP();
	}
	
	public String getname() {
		return name;
	}
	
	public void setEquity(double equity) {
		_equity=equity;
	    equityField.setText(String.format("Equity: %.1f%%", _equity));
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
        c1 = new JLabel(icon1);
        c2 = new JLabel(icon2);
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
	                
	                //((PokerTable) padre).EquityCambioCarta();
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
 	               ((PokerTable) padre).EquityCambioCarta();
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

      
        	
    	_ctrl.cargarRanking(jugador);
    	
        // Subpanel vertical para equity + rangos
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(34, 139, 34));

        // Añadimos el equity al subpanel
        bottomPanel.add(equityField);
        bottomPanel.add(Box.createVerticalStrut(4));

        JPanel panelRangoText= new JPanel();
        JPanel panelRangoPercent= new JPanel();
        JPanel panelEqPercent= new JPanel();
        
        panelRangoText.setLayout(new BoxLayout(panelRangoText, BoxLayout.X_AXIS));
        panelRangoPercent.setLayout(new BoxLayout(panelRangoPercent, BoxLayout.X_AXIS));
        panelEqPercent.setLayout(new BoxLayout(panelEqPercent, BoxLayout.X_AXIS));
        
        JButton botonText= new JButton(toScaledIcon(loadImage("update.png"),20));
        JButton botonPercent= new JButton(toScaledIcon(loadImage("update.png"),20));
        JButton botonEqPercent= new JButton(toScaledIcon(loadImage("update.png"),20));
        
     
        botonText.setMargin(new Insets(0, 0, 0, 0));
        botonPercent.setMargin(new Insets(0, 0, 0, 0));
        botonEqPercent.setMargin(new Insets(0, 0, 0, 0));
        
        // Campo texto de rango manual
        rangeTextField = new JTextField();
        rangeTextField.setToolTipText("Introduce rango (ej: AKs+, 88+, A5s-A2s)");
        rangeTextField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        // Campo texto de porcentaje
        JTextField rangePercentField = new JTextField();
        rangePercentField.setToolTipText("Introduce % del rango (ej: 25)");
        rangePercentField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        
        // Campo texto de porcentaje
        equityPercent = new JTextField();
        equityPercent.setToolTipText("Introduce % del Equity (ej: 25)");
        equityPercent.setFont(new Font("SansSerif", Font.PLAIN, 12));

        campoApuesta= new JTextField();
        campoApuesta.setFont(new Font("SansSerif", Font.BOLD, 12));
        campoApuesta.setBackground(new Color(0, 200, 0));
        
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
        
        botonText.addActionListener(e->{
        	
        	boolean invalido=false;
        	String texto=rangeTextField.getText();	
        	String[] rangos= texto.split(",");
        	List<String> strings= new ArrayList<String>();
        	
        	if(esPorcentajeValido(texto)) {
        		 if(_ctrl.enPorcentaje(jugador,Double.parseDouble(texto)))
        			 rangeTextField.setBackground(Color.GREEN);
        		 else 
        			 rangeTextField.setBackground(Color.RED);
        	}
        	else {
	        	int i = 0;
	        	while (i < rangos.length && !invalido) {
	        	    String rango = rangos[i];
	
	        	    if (esRangoValido(rango)) 
	        	        strings.add(rango);
	        	    else
	        	        invalido = true;
	        	    i++;
	        	}
	        	if(!invalido) 	
	        		
	        		if(_ctrl.enRango(jugador, strings)) 
	        			rangeTextField.setBackground(Color.GREEN);
	        		else 
	        			rangeTextField.setBackground(Color.RED);
	        	
	        	else
	        	    JOptionPane.showMessageDialog(null, "Rango Inválido");
        	}
        });
        
        botonPercent.addActionListener(e->{
        	
        	String porcentaje = rangePercentField.getText();
        	 if(esPorcentajeValido(porcentaje)) {
        		 
        		 if(_ctrl.enPorcentaje(jugador,Double.parseDouble(porcentaje)))
        			 rangePercentField.setBackground(Color.GREEN);
         		else 
         			rangePercentField.setBackground(Color.RED);
        		 
        	 }else 
        		 JOptionPane.showMessageDialog(null, "Porcentaje Inválido");
        });
        
        
        panelRangoText.add(botonText);
        panelRangoText.add(Box.createHorizontalStrut(2));
        panelRangoText.add(rangeTextField);
        
        panelRangoPercent.add(botonPercent);
        panelRangoPercent.add(Box.createHorizontalStrut(2));
        panelRangoPercent.add(rangePercentField);     
        
        panelEqPercent.add(botonEqPercent);
        panelEqPercent.add(Box.createHorizontalStrut(2));
        panelEqPercent.add(equityPercent);     
        panelEqPercent.add(Box.createHorizontalStrut(2));
        panelEqPercent.add(campoApuesta);
        
        // Añadimos los dos campos al subpanel
        bottomPanel.add(panelRangoText);
        bottomPanel.add(Box.createVerticalStrut(2));
        //bottomPanel.add(panelRangoPercent);
        //bottomPanel.add(Box.createVerticalStrut(2));
        bottomPanel.add(panelEqPercent);
        
        // Sustituimos solo la parte inferior (equity + rangos)
        this.remove(equityField);
        this.add(bottomPanel, BorderLayout.SOUTH);
    

        // Tamaño preferido original (no tocamos nada)
        Dimension pPref = playerPanel.getPreferredSize();
        Dimension ePref = equityField.getPreferredSize();
        int pad = 16;
        this.setPreferredSize(new Dimension(pPref.width + pad, pPref.height + ePref.height + pad));   
	}
	
	
	
	private boolean esRangoValido(String rango) {
		// TODO Auto-generated method stub
		boolean valido=true;

		int num_caracteres=rango.length();	
		if(num_caracteres<2) {
			valido =false;
		}
		else if(num_caracteres==2) {	
			if (!rango.matches("([AKQJT2-9])\\1")) 
				valido=false;	
		}
		else if(num_caracteres==3) {
			
			if(!rango.matches("([AKQJT2-9])\\1[+]")&&!rango.matches("[AKQJT2-9][AKQJT2-9][os]")) 
				valido=false;
		}
		else if(num_caracteres==4) {
			if(!rango.matches("[AKQJT2-9][AKQJT2-9][os][+]")) 
				valido=false;
		}
		else if(num_caracteres==5) {
			if(!rango.matches("([AKQJT2-9])\\1[-]([AKQJT2-9])\\2")) 
				valido=false;
		}
		else if(num_caracteres==7) {
			if(!rango.matches("[AKQJT2-9][AKQJT2-9][o][-][AKQJT2-9][AKQJT2-9][o]")&&!rango.matches("[AKQJT2-9][AKQJT2-9][s][-][AKQJT2-9][AKQJT2-9][s]")) 
				valido=false;	
		}else {
			valido=false;
		}
		
		return valido;
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
    
    private boolean esPorcentajeValido(String s) {
        try {
            double num = Double.parseDouble(s);

            // comprobar rango
            return num > 0 && num <= 100;

        } catch (NumberFormatException e) {
            // no es un número
            return false;
        }
    }
    
    public boolean Equity_valido(String texto) {
    	
    	return esPorcentajeValido(texto);
    }
    
    public boolean Comprobar_equity() {
    	String equity_minimo=equityPercent.getText();
    	
    	if(Equity_valido(equity_minimo)){
    		if(_equity>=Double.parseDouble(equity_minimo)) {
        		equityPercent.setBackground(Color.GREEN);
        		_equitySuperada=true;
        	}else {
        		equityPercent.setBackground(Color.RED);
        		_equitySuperada=false;
        	}
    		return false;
    	}else {
    		JOptionPane.showMessageDialog(null, "Equity Inválido");
    		return true;
    	}
    	
    }
    
    public boolean Comprobar_rangos() {
    	boolean invalido=false;
    	String texto=rangeTextField.getText();	
    	String[] rangos= texto.split(",");
    	List<String> strings= new ArrayList<String>();
    	
    	if(esPorcentajeValido(texto)) {
    		 if(_ctrl.enPorcentaje(jugador,Double.parseDouble(texto))) {
    			 rangeTextField.setBackground(Color.GREEN);
    			 _dentroRango=true;
    		 }
    		 else {
    			 rangeTextField.setBackground(Color.RED);
    			 _dentroRango=false;
    		 }
    	}
    	else {
        	int i = 0;
        	while (i < rangos.length && !invalido) {
        	    String rango = rangos[i];

        	    if (esRangoValido(rango)) 
        	        strings.add(rango);
        	    else
        	        invalido = true;
        	    i++;
        	}
        	if(!invalido) 	
        		
        		if(_ctrl.enRango(jugador, strings)) {
        			rangeTextField.setBackground(Color.GREEN);
        			_dentroRango=true;
        		}
        		else {
        			rangeTextField.setBackground(Color.RED);
        			_dentroRango=false;
        		}
        	
        	else
        	    JOptionPane.showMessageDialog(null, "Rango Inválido");
    	}
    	
    	return invalido;
    }

	public void accion() {
		// TODO Auto-generated method stub
		if(_equitySuperada&&_dentroRango) {
			bet_call();
		}
		else {
			//fold
			fold();
		}
		
		
	}
    
    public void bet_call() {
    	//bet/call
		if(_ctrl.ExisteApuesta()) {
			//call
			campoApuesta.setText("     CALL");
		}else {
			_ctrl.NuevaApuesta();
			//bet
			campoApuesta.setText("     BET");
		}
    }
	
    public void fold() {
    	 Carta carta1 = cartas.getFirst();
         Carta carta2 = cartas.getSecond();

         Image img1 = loadImage(carta1.toString() + ".png");
         Image img2 = loadImage(carta2.toString() + ".png");
         int maxCardHeight = 80;
         ImageIcon icon1 = toScaledIcon(img1, maxCardHeight);
         ImageIcon icon2 = toScaledIcon(img2, maxCardHeight);
         
         ImageIcon icon1Semi = makeTransparent(icon1.getImage(), 0.5f);
         ImageIcon icon2Semi = makeTransparent(icon2.getImage(), 0.5f);
         
         c1.setIcon(icon1Semi);
         c2.setIcon(icon2Semi);
         
    	rangeTextField.setText("FOLD");
    	equityPercent.setText("FOLD");
    	equityField.setText("");
    	_ctrl.fold(jugador);
    	
    }
		
    public static ImageIcon makeTransparent(Image img, float alpha) {
        int w = img.getWidth(null);
        int h = img.getHeight(null);

        BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffImg.createGraphics();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.drawImage(img, 0, 0, null);
        g2.dispose();

        return new ImageIcon(buffImg);
    }
	
	
	
	
	
	
	
	
}