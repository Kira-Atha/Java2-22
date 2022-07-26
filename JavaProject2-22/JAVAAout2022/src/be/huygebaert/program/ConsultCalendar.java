package be.huygebaert.program;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Outing;
import be.huygebaert.POJO.Person;
import com.toedter.calendar.JCalendar;

public class ConsultCalendar {

	public JFrame consultCalendar;
	JButton create_button,add_button,update_button;
	private static Person person;
	private JLabel lb_message,lb_nameCategory;
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
		consultCalendar.getContentPane().setLayout(null);
		JPanel inConsultCalendar = new JPanel();
		inConsultCalendar.setBounds(10,155,346,300);
		inConsultCalendar.setLayout(null);
		consultCalendar.getContentPane().add(inConsultCalendar);
		
		lb_message = new JLabel("");
		lb_message.setBounds(10,10,400,15);
		consultCalendar.getContentPane().add(lb_message);
	
		//
		lb_message.setText("glouglou");
		JCalendar calendar = new JCalendar();
		calendar.setBounds(0, 0, 205, 153);
		inConsultCalendar.add(calendar);
		
		//GESTION DATE
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(calendar.getDate());
		
		System.out.println(person.getClass().getSimpleName());
		
		
		if(ConsultCalendar.person instanceof Manager) {
			lb_message.setText("Manager");
			inConsultCalendar.add(lb_message);
			lb_nameCategory = new JLabel(((Manager) person).getCategory().getClass().getSimpleName());
			inConsultCalendar.add(lb_nameCategory);
			
			// ADD
			create_button = new JButton("new outing");
			create_button.setBounds(0,0,20,30);
			inConsultCalendar.add(create_button);
			
		}
		
		if(ConsultCalendar.person instanceof Member) {
			lb_message.setText("Member");
			inConsultCalendar.add(lb_message);
			for(Category category : ((Member) person).getMemberCategories()) {
				lb_nameCategory= new JLabel(category.getClass().getSimpleName());
				inConsultCalendar.add(lb_nameCategory);
			}
		}
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		consultCalendar.dispose();
	}
}
