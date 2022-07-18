package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class MonitorPayments {

	public JFrame monitorPayments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonitorPayments window = new MonitorPayments();
					window.monitorPayments.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MonitorPayments() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		monitorPayments = new JFrame();
		monitorPayments.setBounds(100, 100, 800, 600);
		monitorPayments.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		monitorPayments.setLocationRelativeTo(null);
		monitorPayments.setLayout(null);
		
		
		monitorPayments.setVisible(true);
	}
}
