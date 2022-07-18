package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ConsultCategories {

	public JFrame consultCategories;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultCategories window = new ConsultCategories();
					window.consultCategories.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConsultCategories() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		consultCategories = new JFrame();
		consultCategories.setBounds(100, 100, 800, 600);
		consultCategories.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consultCategories.setLayout(null);
		consultCategories.setLocationRelativeTo(null);
	}
}