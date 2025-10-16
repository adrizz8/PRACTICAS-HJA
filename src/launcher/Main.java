package launcher;

import javax.swing.SwingUtilities;

import view.MainWindow;

public class Main {

	 public static void main(String[] args) {
	        // Ejecutar GUI en el hilo de eventos
	        SwingUtilities.invokeLater(() -> {
	            new MainWindow().setVisible(true);
	        });
	    }
}
