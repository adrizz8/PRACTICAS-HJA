package GUI;

import java.awt.*;

import javax.swing.*;

public class Main_gui  {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		JFrame frame=new JFrame("Poker");
		
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);

        frame.setLocationRelativeTo(null); // centrar en pantalla

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        ImageIcon icon = new ImageIcon("cartas/10_of_clubs.png");
        JLabel carta1 = new JLabel(icon);
        
        
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Cargar la imagen
                Image imagen = new ImageIcon("cartas/mesa.png").getImage();
                // Dibujar la imagen escalada al tamaño actual del panel
                g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        };
        
        ImageIcon mesa = new ImageIcon("cartas/mesa.png");
        JLabel mesa1=new JLabel(mesa);
        
        panel.add(mesa1);
        
        frame.setContentPane(panelFondo);
        mesa1.setLayout(new BorderLayout()); // para añadir cosas encima
        
        
       
        frame.setVisible(true);
        
	}

}
