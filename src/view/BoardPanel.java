package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.Controller;
import misc.Pair;
import model.Carta;

public class BoardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private List<Carta>cartas;
	private Controller _ctrl;
	private JLabel [] Imagenes;
	private JFrame padre;
	
	
	public BoardPanel (Controller ctrl,JFrame padre){
		this.padre=padre;
		this._ctrl=ctrl;
		this.cartas=_ctrl.getBoard();
		this.cartas=_ctrl.getBoard();
		
		initP();
	}
	private void initP() {
		
		setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
        Imagenes= new JLabel[5];
       

        // Carga y escala las imágenes
        Image img1 = loadImage("0h" + ".png");
        int maxCardHeight = 80;
        ImageIcon icon1 = toScaledIcon(img1, maxCardHeight);
        
        JLabel c1 = new JLabel(icon1);
        JLabel c2 = new JLabel(icon1);
        JLabel c3 = new JLabel(icon1);
        JLabel c4 = new JLabel(icon1);
        JLabel c5 = new JLabel(icon1);
        
        c1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new MatrizCambio(padre,_ctrl,c1).setVisible(true);
                
                String nuevaCarta = (String) c1.getClientProperty("nuevaCarta");
                c1.putClientProperty("nuevaCarta",null);
                
                if(nuevaCarta!=null) {
               	 nuevaCarta.replace(".png", "");
                	Carta newCarta = new Carta(nuevaCarta);
                	
                	_ctrl.modificarBoard(0, newCarta);
	                
	                cartas = _ctrl.getBoard();
	                
	                if(_ctrl.getCartasBoard().size() >= 3) {
	                	((PokerTable) padre).EquityCambioCarta();
	                }    
	                
	                ((PokerTable) padre).resetAll();
                }
            }
        });
        
        c2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	 new MatrizCambio(padre,_ctrl,c2).setVisible(true);
                 
                 String nuevaCarta = (String) c2.getClientProperty("nuevaCarta");
                 c2.putClientProperty("nuevaCarta",null);
                 
                 if(nuevaCarta!=null) {
                	 nuevaCarta.replace(".png", "");
                 	Carta newCarta = new Carta(nuevaCarta);
                 	
                 	_ctrl.modificarBoard(1, newCarta);
 	                
 	                cartas = _ctrl.getBoard();
 	                
 	               if(_ctrl.getCartasBoard().size() >= 3) {
	                	((PokerTable) padre).EquityCambioCarta();
	                }     
 	               
 	              ((PokerTable) padre).resetAll();
                 }
            }
        });
        
        c3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	 new MatrizCambio(padre,_ctrl,c3).setVisible(true);
                 
                 String nuevaCarta = (String) c3.getClientProperty("nuevaCarta");
                 c3.putClientProperty("nuevaCarta",null);
                 
                 if(nuevaCarta!=null) {
                	 nuevaCarta.replace(".png", "");
                 	Carta newCarta = new Carta(nuevaCarta);
                 	
                 	_ctrl.modificarBoard(2, newCarta);
 	                
 	                cartas = _ctrl.getBoard();
 	                
 	               if(_ctrl.getCartasBoard().size() >= 3) {
	                	((PokerTable) padre).EquityCambioCarta();
	                }  
 	               
 	              ((PokerTable) padre).resetAll();
                 }
            }
        });
        
        c4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	 new MatrizCambio(padre,_ctrl,c4).setVisible(true);
                 
                 String nuevaCarta = (String) c4.getClientProperty("nuevaCarta");
                 c1.putClientProperty("nuevaCarta",null);
                 
                 if(nuevaCarta!=null) {
                	 nuevaCarta.replace(".png", "");
                 	Carta newCarta = new Carta(nuevaCarta);
                 	
                 	_ctrl.modificarBoard(3, newCarta);
 	                
 	                cartas = _ctrl.getBoard();
 	                
 	               if(_ctrl.getCartasBoard().size() >= 3) {
	                	((PokerTable) padre).EquityCambioCarta();
	                }  
 	               
 	              ((PokerTable) padre).resetAll();
                 }
            }
        });
        
        c5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
            	 new MatrizCambio(padre,_ctrl,c5).setVisible(true);
                 
                 String nuevaCarta = (String) c5.getClientProperty("nuevaCarta");
                 c1.putClientProperty("nuevaCarta",null);
                 
                 if(nuevaCarta!=null) {
                	 nuevaCarta.replace(".png", "");
                 	Carta newCarta = new Carta(nuevaCarta);
                 	
                 	_ctrl.modificarBoard(4, newCarta);
 	                
 	                cartas = _ctrl.getBoard();
 	                
 	               if(_ctrl.getCartasBoard().size() >= 3) {
	                	((PokerTable) padre).EquityCambioCarta();
	                }     
 	               
 	               	((PokerTable) padre).resetAll();
                 }
            }
        });
       
       Imagenes[0]=c1;
       Imagenes[1]=c2;
       Imagenes[2]=c3;
       Imagenes[3]=c4;
       Imagenes[4]=c5;

       add(Imagenes[0]);
       add(Imagenes[1]);
       add(Imagenes[2]);
       add(Imagenes[3]);
       add(Imagenes[4]);

	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
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
    public void actualizarBoard(List<Carta> board) {
       int i=0;
       
        for (Carta c : board) {
            String imgPath = "resources/icons/" + c.toString() + ".png";
            ImageIcon icon = new ImageIcon(new ImageIcon(imgPath)
                .getImage().getScaledInstance(50, 70, Image.SCALE_SMOOTH));
            Imagenes[i].setIcon(icon);
            i++;
        }
        cartas=board;
    }
}
