package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AddVehicle {

	public JFrame addVehicle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddVehicle window = new AddVehicle();
					window.addVehicle.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddVehicle() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addVehicle = new JFrame();
		addVehicle.setBounds(100, 100, 450, 300);
		addVehicle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
