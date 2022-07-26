package be.huygebaert.program;

import java.awt.EventQueue;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Outing;
import be.huygebaert.POJO.Person;
import com.toedter.calendar.JCalendar;

public class ConsultCalendar {

	public JFrame consultCalendar;
	JButton btn_create,btn_delete,btn_update,btn_logout;
	private static Person person;
	private JLabel lb_message,lb_nameCategory,lb_legend,lb_startPoint,lb_forfeit,lb_maxMember,lb_maxVelo,lb_titleForm;
	private JPanel panel_outingsInfo,panel_formOuting,panel_buttons,panel_calendar;
	private JTextField tf_startPoint,tf_forfeit,tf_maxMember,tf_maxVelo;
	private String date;
	
	
	
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

	public ConsultCalendar(Person person) {
		ConsultCalendar.person=person;
		initialize();
		
	}

	private void initialize() {
		consultCalendar = new JFrame("ConsultCalendar");
		consultCalendar.setBounds(100, 100, 800, 600);
		consultCalendar.setLocationRelativeTo(null);
		consultCalendar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		consultCalendar.getContentPane().setLayout(null);
		panel_calendar = new JPanel();
		panel_calendar.setBounds(10,87,269,180);
		panel_calendar.setLayout(null);
		consultCalendar.getContentPane().add(panel_calendar);
		
		btn_logout = new JButton();
		btn_logout.setText("logout");
		btn_logout.setBounds(685,11,89,20);
		consultCalendar.getContentPane().add(btn_logout);
		
		
		btn_logout.addActionListener(e-> {
			Init previous = new Init();
			JFrame home = previous.init;
			changeFrame(home);
		});
		JCalendar calendar = new JCalendar();
		calendar.setBounds(0, 0, 265, 161);
		panel_calendar.add(calendar);
		
	//GESTION DATE
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Get current date when person arrive on ConsultCalendar
		date = sdf.format(calendar.getDate());
		
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				date = sdf.format(calendar.getDate());
				System.out.println(date);
			}
		});
		// Permit to get date when month just selected
		calendar.getMonthChooser().addPropertyChangeListener("month", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				date = sdf.format(calendar.getDate());
				System.out.println(date);
			}
		});
		//  Permit to get date when year just selected
		calendar.getYearChooser().addPropertyChangeListener("year", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				date = sdf.format(calendar.getDate());
				System.out.println(date);
			}
		});
		
		System.out.println(person.getClass().getSimpleName());
		System.out.println(((Manager)person));
		
		
		if(ConsultCalendar.person instanceof Manager) {
			lb_message = new JLabel("Hello " + person.getPseudo()+ ", " + person.getClass().getSimpleName() + " of " + ((Manager) person).getCategory().getClass().getSimpleName());
			consultCalendar.getContentPane().add(lb_message);
			lb_message.setBounds(0,0,200,15);
			lb_legend = new JLabel("Select a date and press button : [+] => Add outing && [?] Update outing && [X] Delete outing.");
			lb_legend.setBounds(0,30,700,15);
			Font font = new Font(lb_legend.getFont().getName(),Font.ITALIC,lb_legend.getFont().getSize());
			lb_legend.setFont(font);
			consultCalendar.getContentPane().add(lb_legend);
			System.out.println(lb_message.getText());
			
	// Manage outing form ( add / update ) 
			panel_formOuting = new JPanel();
			panel_formOuting.setBounds(10,289,291,239);
			panel_formOuting.setLayout(null);
			consultCalendar.getContentPane().add(panel_formOuting);
			
			lb_titleForm = new JLabel("Form [ADD] / [UPDATE] outing");
			lb_titleForm.setBounds(0,0,200,20);
			panel_formOuting.add(lb_titleForm);
			lb_startPoint = new JLabel("Start point");
			lb_startPoint.setBounds(0,20,100,20);
			panel_formOuting.add(lb_startPoint);
			lb_forfeit = new JLabel("Forfeit");
			lb_forfeit.setBounds(0,40,100,20);
			panel_formOuting.add(lb_forfeit);
			lb_maxMember = new JLabel("Max member");
			lb_maxMember.setBounds(0,60,100,20);
			panel_formOuting.add(lb_maxMember);
			lb_maxVelo = new JLabel("Max velos");
			lb_maxVelo.setBounds(0,80,100,20);
			panel_formOuting.add(lb_maxVelo);
			
			tf_startPoint = new JTextField();
			tf_startPoint.setBounds(100,20,150,20);
			panel_formOuting.add(tf_startPoint);
			tf_forfeit = new JTextField("0");
			tf_forfeit.setBounds(100,40,30,20);
			panel_formOuting.add(tf_forfeit);
			tf_maxMember = new JTextField("0");
			tf_maxMember.setBounds(100,60,30,20);
			panel_formOuting.add(tf_maxMember);
			tf_maxVelo = new JTextField("0");
			tf_maxVelo.setBounds(100,80,30,20);
			panel_formOuting.add(tf_maxVelo);
			
			
	// Manage manager buttons
			panel_buttons = new JPanel();
			panel_buttons.setBounds(300,105,90,150);
			panel_buttons.setLayout(null);
			consultCalendar.getContentPane().add(panel_buttons);
			
			
			
			// ADD
			btn_create = new JButton("+");
			btn_create.setBounds(0,0,50,50);
			panel_buttons.add(btn_create);
			
			btn_create.addActionListener(e-> {
				String startPoint = tf_startPoint.getText();
				System.out.println(startPoint);
				double forfeit = Double.parseDouble(tf_forfeit.getText());
				int maxMember = Integer.parseInt(tf_maxMember.getText());
				int maxVelo = Integer.parseInt(tf_maxVelo.getText());
				String result = "";
				
				try {
					result = formValidation(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if(result!="") {
					JOptionPane.showMessageDialog(null, result);
				}else {
					//System.out.println("TEST ADD => " + date);
					Outing outing = null;
					try {
						outing = new Outing(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo,((Manager)person).getCategory().getSingleCalendar());
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					//System.out.println("Outing start date de l'objet => " +outing.getStartDate());
					if(((Manager)person).manageCalendar(0,outing)) {
						JOptionPane.showConfirmDialog(null,"Create success");
					}
				}
			});
			
			// update
			btn_update = new JButton("?");
			btn_update.setBounds(0,50,50,50);
			panel_buttons.add(btn_update);
			
			btn_update.addActionListener(e-> {
				String startPoint = tf_startPoint.getText();
				System.out.println(startPoint);
				double forfeit = Double.parseDouble(tf_forfeit.getText());
				int maxMember = Integer.parseInt(tf_maxMember.getText());
				int maxVelo = Integer.parseInt(tf_maxVelo.getText());
				String result = "";
				
				try {
					result = formValidation(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if(result!="") {
					JOptionPane.showMessageDialog(null, result);
				}else {
					System.out.println("TEST ADD => " + date);
					//Outing outing = new Outing();
					//((Manager)person).manageCalendar(1,outing);
				}
			});
			
			// delete
			btn_delete = new JButton("X");
			btn_delete.setBounds(0,100,50,50);
			panel_buttons.add(btn_delete);
			
			btn_delete.addActionListener(e-> {
				List <Outing> allOutings = Outing.getAllOutings();
				
				for(Outing outing : allOutings) {
					try {
						if(outing.getStartDate().compareTo(sdf.parse(date)) == 0) {
							Outing selectedOuting = outing;
							((Manager)person).manageCalendar(2,selectedOuting);
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			
		}
		
		///////////////////
		
		
		if(ConsultCalendar.person instanceof Member) {
			lb_message.setText("Member");
			panel_calendar.add(lb_message);
			for(Category category : ((Member) person).getMemberCategories()) {
				lb_nameCategory= new JLabel(category.getClass().getSimpleName());
				panel_calendar.add(lb_nameCategory);
			}
		}
	}
	
	
	public String formValidation(String startPoint, Date startDate, double forfeit, int maxMember, int maxVelo) {
		String result="";
		
		if(startPoint == "" || Objects.isNull(startPoint) || startPoint.length() < 3) {
			result+="Invalid start point";
			result+="\n";
		}
		
		Date in = new Date();
		LocalDateTime now = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
		Date out = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		
		if(Objects.isNull(startDate) || startDate.compareTo(out) < 0) {
			result+="Invalid start date. Can't be before today or today";
			result+="\n";
		}
		
		if(forfeit == (0) ) {
			result+="Enter a forfeit";
			result+="\n";
		}
		if(maxMember == (0) ) {
			result+="Enter a max member can be register";
			result+="\n";
		}
		if(maxVelo == (0) ) {
			result+="Enter a max velo can be register";
			result+="\n";
		}
		return result;
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		consultCalendar.dispose();
	}

}
