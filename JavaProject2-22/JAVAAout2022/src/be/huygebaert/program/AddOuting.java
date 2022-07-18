package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class AddOuting {

	public JFrame addOuting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddOuting window = new AddOuting();
					window.addOuting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddOuting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		addOuting = new JFrame();
		addOuting.setBounds(100, 100, 450, 300);
		addOuting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
