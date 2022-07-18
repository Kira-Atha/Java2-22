package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AddRegister {

	public JFrame addRegister;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRegister window = new AddRegister();
					window.addRegister.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddRegister() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addRegister = new JFrame();
		addRegister.setBounds(100, 100, 450, 300);
		addRegister.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
