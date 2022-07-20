package be.huygebaert.program;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;

public class ConsultCalendar {

	public JFrame consultCalendar;
	private static Person person;
	private JLabel lb_message;
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
	public ConsultCalendar() {
		initialize();
		
	}
	public ConsultCalendar(Person person) {
		this();
		ConsultCalendar.person=person;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		consultCalendar = new JFrame("ConsultCalendar");
		consultCalendar.setBounds(100, 100, 800, 600);
		consultCalendar.setLocationRelativeTo(null);
		consultCalendar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consultCalendar.setLayout(null);
		JPanel inConsultCalendar = new JPanel();
		inConsultCalendar.setBounds(20,20,500,300);
		inConsultCalendar.setLayout(null);
		consultCalendar.add(inConsultCalendar);
		
		lb_message = new JLabel("");
		lb_message.setBounds(20, 20, 400, 400);
		
		if(ConsultCalendar.person instanceof Manager) {
			lb_message.setText("Manager");
			inConsultCalendar.add(lb_message);
		}
		
		if(ConsultCalendar.person instanceof Member) {
			lb_message.setText("Member");
			inConsultCalendar.add(lb_message);
		}
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		consultCalendar.dispose();
	}
}
