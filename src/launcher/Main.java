package launcher;


import javax.swing.SwingUtilities;

import control.Controller;
import model.Mesa;
import view.MainWindow;

public class Main {

	public static void main(String[] args) {
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	private static void start(){
		Mesa mesa = new Mesa();
		Controller controller = new Controller(mesa);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainWindow(controller);
			}

		});
	}
}
