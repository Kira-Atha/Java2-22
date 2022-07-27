package be.huygebaert.program;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Outing;
import be.huygebaert.POJO.Person;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class ConsultCalendar {

	public JFrame consultCalendar;
	JButton btn_create,btn_delete,btn_update,btn_logout;
	private static Person person;
	private JLabel lb_message,lb_nameCategory,lb_legend,lb_startPoint,lb_forfeit,lb_maxMember,lb_maxVelo,lb_titleForm;
	private JPanel panel_outingInfo,panel_formOuting,panel_buttons,panel_calendar;
	private JTextField tf_startPoint,tf_forfeit,tf_maxMember,tf_maxVelo;
	private String date;
	private List <Outing> allOutings;
	private JDateChooser dateChooser;
	private SimpleDateFormat sdf;
	private JCalendar calendar;
	
	
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
		allOutings = Outing.getAllOutings();
		initialize();
		
	}

	private void initialize() {
		consultCalendar = new JFrame("ConsultCalendar");
		consultCalendar.setBounds(100, 100, 800, 600);
		consultCalendar.getContentPane().setLayout(new FlowLayout());
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
		calendar = new JCalendar();
		calendar.setBounds(0, 0, 265, 161);
		panel_calendar.add(calendar);
		
		panel_outingInfo = new JPanel();
		panel_outingInfo.setBounds(379,87,361,255);
		panel_outingInfo.setLayout(null);
		consultCalendar.getContentPane().add(panel_outingInfo);
		
		
	// Manager date chooser (day/month/year ) + consult outing (manager && member ) 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Get current date when person arrive on ConsultCalendar
		date = sdf.format(calendar.getDate());
		
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				listenerCalendar();
			}
		});
		// Permit to get date when month just selected
		calendar.getMonthChooser().addPropertyChangeListener("month", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				listenerCalendar();
			}
		});
		//  Permit to get date when year just selected
		calendar.getYearChooser().addPropertyChangeListener("year", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				listenerCalendar();
			}
		});
		
		//System.out.println(person.getClass().getSimpleName());
		//System.out.println(((Manager)person));
		
		
		if(ConsultCalendar.person instanceof Manager) {
			lb_message = new JLabel("Hello " + person.getPseudo()+ ", " + person.getClass().getSimpleName() + " of " + ((Manager) person).getCategory().getClass().getSimpleName());
			consultCalendar.getContentPane().add(lb_message);
			lb_message.setBounds(0,0,200,15);
			lb_legend = new JLabel("Select a date and press button : [+] => Add outing && [?] Update outing && [X] Delete outing.");
			lb_legend.setBounds(0,30,700,15);
			Font font = new Font(lb_legend.getFont().getName(),Font.ITALIC,lb_legend.getFont().getSize());
			lb_legend.setFont(font);
			consultCalendar.getContentPane().add(lb_legend);
			//System.out.println(lb_message.getText());
			
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
				
				//System.out.println(startPoint);
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
					boolean exist = false;
					for(Outing ou : allOutings) {
						try {
							if(ou.getStartDate().compareTo(sdf.parse(date)) == 0 && ou.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
								JOptionPane.showMessageDialog(null,"An outing already exist for this calendar on this date");
								exist = true;
								break;
							}
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
					//System.out.println("TEST ADD => " + date);
					if(!exist) {
						Outing outing = null;
						try {
							outing = new Outing(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo,((Manager)person).getCategory().getSingleCalendar());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						System.out.println("CURRENT CALENDAR OBJECT CURRENT => "+((Manager)person).getCategory().getSingleCalendar().getNum());
						//System.out.println("Outing start date de l'objet => " +outing.getStartDate());
						if(((Manager)person).manageCalendar(0,outing)) {
							ConsultCalendar next = new ConsultCalendar(person);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
							JOptionPane.showMessageDialog(null,"Create success");
						}else {
							JOptionPane.showMessageDialog(null, "Can't create outing");
						}
					}
				}
			});
			
			
			// update
			btn_update = new JButton("?");
			btn_update.setBounds(0,50,50,50);
			panel_buttons.add(btn_update);
			
			btn_update.addActionListener(e-> {
				String newStartPoint = tf_startPoint.getText();
				//System.out.println(startPoint);
				double newForfeit = Double.parseDouble(tf_forfeit.getText());
				int newMaxMember = Integer.parseInt(tf_maxMember.getText());
				int newMaxVelo = Integer.parseInt(tf_maxVelo.getText());
				String newDate = null;
				if(!Objects.isNull(this.dateChooser) && !Objects.isNull(this.dateChooser.getDate())) {
					newDate = sdf.format(dateChooser.getDate());
					String result = "";
					
					try {
						result = formValidation(newStartPoint,sdf.parse(newDate),newForfeit,newMaxMember,newMaxVelo);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					if(result!="") {
						JOptionPane.showMessageDialog(null, result);
					}else {
						boolean exist = false;
						int idOuting = 0;
						for(Outing ou : allOutings) {
							try {
								if(ou.getStartDate().compareTo(sdf.parse(date)) == 0 && ou.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
									idOuting = ou.getNum();
									break;
								}
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}
						//System.out.println("TEST ADD => " + date);
						if(!exist) {
							Outing outing = null;
							try {
								outing = new Outing(newStartPoint,sdf.parse(date),newForfeit,newMaxMember,newMaxVelo,((Manager)person).getCategory().getSingleCalendar());
								outing.setNum(idOuting);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							System.out.println("CURRENT CALENDAR OBJECT CURRENT => "+((Manager)person).getCategory().getSingleCalendar().getNum());
							//System.out.println("Outing start date de l'objet => " +outing.getStartDate());
							if(((Manager)person).manageCalendar(1,outing)) {
								ConsultCalendar next = new ConsultCalendar(person);
								JFrame consultCalendar = next.consultCalendar;
								changeFrame(consultCalendar);
								JOptionPane.showMessageDialog(null,"Create success");
							}else {
								JOptionPane.showMessageDialog(null, "Can't create outing");
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(null,"You can't update an outing if you didn't selected it and choose a new date.");
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
	public void listenerCalendar() {
		panel_outingInfo.removeAll();
		panel_outingInfo.revalidate();
		panel_outingInfo.repaint();
		date = sdf.format(calendar.getDate());
		Outing outingExist = null;
		//System.out.println(date);
		for(Outing outing : allOutings) {
			try {
				if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
					outingExist = outing;
					//System.out.println("Outing exist where you selected");
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		if(!Objects.isNull(outingExist)) {
			JLabel lb_text = new JLabel("Outing which exist on the selected date");
			lb_text.setBounds(0,0,300,20);
			JLabel lb_startPoint0 = new JLabel("Start point = "+outingExist.getStartPoint());
			lb_startPoint0.setBounds(0,20,300,20);
			JLabel lb_dateStart0 = new JLabel("Date = " + sdf.format(outingExist.getStartDate()));
			lb_dateStart0.setBounds(0,40,300,20);
			JLabel lb_forfeit0 = new JLabel("Price = "+Double.toString(outingExist.getForfeit())+"€");
			lb_forfeit0.setBounds(0,60,300,20);
			JLabel lb_maxMemberSeat0 = new JLabel("Max member seats = "+Integer.toString(outingExist.getMaxMemberSeats()));
			lb_maxMemberSeat0.setBounds(0,80,300,20);
			JLabel lb_maxVeloSeats0 = new JLabel("Max velo seats = " +Integer.toString(outingExist.getMaxVeloSeats()));
			lb_maxVeloSeats0.setBounds(0,100,300,20);
			JLabel lb_needMemberSeats0 = new JLabel("Need member seats = "+Integer.toString(outingExist.getNeedMemberSeats()));
			lb_needMemberSeats0.setBounds(0,120,300,20);
			JLabel lb_remainingMemberSeats0  = new JLabel("Remaining member seats = "+Integer.toString(outingExist.getRemainingMemberSeats()));
			lb_remainingMemberSeats0.setBounds(0,140,300,20);
			// TODO : gérer en bdd la colonne ici
			//JLabel lb_remainingVeloSeats0 = new JLabel("Remaining velo seats = "+Integer.toString(outingExist.getRemainingVeloSeats()));
			//lb_remainingVeloSeats0.setBounds(0,0,100,20);
			
			panel_outingInfo.add(lb_text);
			panel_outingInfo.add(lb_startPoint0);
			panel_outingInfo.add(lb_dateStart0);
			panel_outingInfo.add(lb_forfeit0);
			panel_outingInfo.add(lb_maxMemberSeat0);
			panel_outingInfo.add(lb_maxVeloSeats0);
			panel_outingInfo.add(lb_needMemberSeats0);
			panel_outingInfo.add(lb_remainingMemberSeats0);
			//panel_outingInfo.add(lb_remainingVeloSeats0);
			if(ConsultCalendar.person instanceof Manager) {
				dateChooser = new JDateChooser();
				dateChooser.setBounds(150,40,150,20);
				panel_outingInfo.add(dateChooser);
			}
		}
	}
}
