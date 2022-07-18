package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AddVelo {

	public JFrame addVelo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddVelo window = new AddVelo();
					window.addVelo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddVelo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addVelo = new JFrame();
		addVelo.setBounds(100, 100, 450, 300);
		addVelo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
