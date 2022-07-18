package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;

import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;

public class ConsultCalendar {

	public JFrame consultCalendar;
	private static Person person;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConsultCalendar window = new ConsultCalendar(person);
					window.consultCalendar.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConsultCalendar(Person person) {
		initialize();
		ConsultCalendar.person=person;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if(ConsultCalendar.person instanceof Manager) {
			// Afficher direct le contenu de sa catégorie, du coup nouvelle page
			
		}
		
		if(ConsultCalendar.person instanceof Member) {
			
		}
		consultCalendar = new JFrame();
		consultCalendar.setBounds(100, 100, 800, 600);
		consultCalendar.setLocationRelativeTo(null);
		consultCalendar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consultCalendar.setLayout(null);
		
		
		consultCalendar.setVisible(true);
	}
}
