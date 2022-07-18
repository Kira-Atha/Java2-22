package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ConsultOuting {

	private JFrame consultOuting;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultOuting window = new ConsultOuting();
					window.consultOuting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConsultOuting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		consultOuting = new JFrame();
		consultOuting.setBounds(100, 100, 450, 300);
		consultOuting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
