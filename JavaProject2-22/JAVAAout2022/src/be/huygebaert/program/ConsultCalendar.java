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
import java.util.ArrayList;
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
import be.huygebaert.POJO.Vehicle;
import be.huygebaert.POJO.Velo;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class ConsultCalendar {

	public JFrame consultCalendar;
	JButton btn_create,btn_delete,btn_update,btn_logout,btn_cat,btn_joinCat;
	private static Person person;
	private JLabel lb_message,lb_legend,lb_startPoint,lb_forfeit,lb_maxMember,lb_maxVelo,lb_titleForm,lb_weight, lb_lenght, lb_type,lb_vehicleTotalMemberSeats,lb_vehicleTotalVeloSeats;
	private JPanel panel_outingInfo,panel_formOuting,panel_buttons,panel_calendar;
	private JTextField tf_startPoint,tf_forfeit,tf_maxMember,tf_maxVelo,tf_weight,tf_type,tf_lenght,tf_vehicleTotalMemberSeats,tf_vehicleTotalVeloSeats;
	private String date;
	private List <Outing> allOutings;
	private List <Category> allCategories;
	private List <Velo> allVelos;
	private List<Vehicle> allVehicles;
	private JDateChooser dateChooser;
	private SimpleDateFormat sdf;
	private JCalendar calendar;
	private Outing outingExist;
	
	
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
		allCategories = Category.getAllCategories();
		allVelos = Velo.getAllVelos();
		allVehicles = Vehicle.getAllVehicles();
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
		panel_outingInfo.setBounds(370,90,350,200);
		panel_outingInfo.setLayout(null);
		consultCalendar.getContentPane().add(panel_outingInfo);
		
	// Manager date chooser (day/month/year ) + consult outing (manager && member ) 
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		// Get current date when person arrive on ConsultCalendar
		date = sdf.format(calendar.getDate());
		
		
		// disp outing info if exist when sign in
		disp_outingInfo();
		
		calendar.getDayChooser().addPropertyChangeListener("day", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				clean_panel(panel_outingInfo);
				disp_outingInfo();
			}
		});
		// Permit to get date when month just selected
		calendar.getMonthChooser().addPropertyChangeListener("month", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				clean_panel(panel_outingInfo);
				disp_outingInfo();
			}
		});
		//  Permit to get date when year just selected
		calendar.getYearChooser().addPropertyChangeListener("year", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				clean_panel(panel_outingInfo);
				disp_outingInfo();
			}
		});
		
		//System.out.println(person.getClass().getSimpleName());
		//System.out.println(((Manager)person));
		
	//MANAGER	
		if(ConsultCalendar.person instanceof Manager) {
			lb_message = new JLabel("Hello " + person.getPseudo()+ ", " + person.getClass().getSimpleName() + " of " + ((Manager) person).getCategory().getClass().getSimpleName());
			consultCalendar.getContentPane().add(lb_message);
			lb_message.setBounds(0,0,400,15);
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
								if(ou.getStartDate().compareTo(sdf.parse(newDate)) == 0 && ou.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
									JOptionPane.showMessageDialog(null,"An outing already exist for this calendar on this date");
									exist = true;
									break;
								}
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
								outing = new Outing(newStartPoint,sdf.parse(newDate),newForfeit,newMaxMember,newMaxVelo,((Manager)person).getCategory().getSingleCalendar());
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
								JOptionPane.showMessageDialog(null,"Update success");
							}else {
								JOptionPane.showMessageDialog(null, "Can't update outing");
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
				Outing selectedOuting = null;
				List <Outing> allOutings = Outing.getAllOutings();
				for(Outing outing : allOutings) {
					try {
						if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
							selectedOuting = outing;
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				if(!Objects.isNull(selectedOuting) && ((Manager)person).manageCalendar(2,selectedOuting)) {
					JOptionPane.showMessageDialog(null,"Delete success");
					ConsultCalendar next = new ConsultCalendar(person);
					JFrame consultCalendar = next.consultCalendar;
					changeFrame(consultCalendar);
				}else {
					JOptionPane.showMessageDialog(null,"Can't delete an outing which doesn't exist");
				}
			});
		}
	//MEMBER
		if(ConsultCalendar.person instanceof Member) {
			lb_message = new JLabel("Hello " + person.getPseudo()+ ", " + person.getClass().getSimpleName() + " of " + ((Member) person).getMemberCategories().get(0).getClass().getSimpleName());
			consultCalendar.getContentPane().add(lb_message);
			lb_message.setBounds(0,0,400,15);
		
			for(Velo velo : allVelos) {
				if(velo.getMemberVelo().getId() == ((Member)person).getId())
					((Member)person).getMemberVelos().add(velo);
			}
			
			for(Vehicle vehicle : allVehicles) {
				if(vehicle.getDriver().getId() == ((Member)person).getId()) {
					((Member)person).setMemberVehicle(vehicle);
				}
			}
		
		//REGISTER ON OUTING
			JPanel panel_register = new JPanel();
			panel_register.setBounds(0,300,150,100);
			panel_register.setLayout(null);
			consultCalendar.getContentPane().add(panel_register);
			JButton btn_register = new JButton("Register");
			btn_register.setBounds(0,0,100,20);
			panel_register.add(btn_register);
			btn_register.addActionListener(e-> {
				System.out.println("register");
			});
		//ADD VELO to member
			JPanel panel_addVelo = new JPanel();
			panel_addVelo.setLayout(null);
			panel_addVelo.setBounds(250,300,200,300);
			consultCalendar.getContentPane().add(panel_addVelo);
			
			JButton btn_addVelo = new JButton("Add velo");
			btn_addVelo.setBounds(0,0,150,20);
			panel_addVelo.add(btn_addVelo);
			
			lb_weight = new JLabel("Weight (kg)");
			lb_weight.setBounds(0,121,80,20);
			lb_lenght = new JLabel("Lenght (m)");
			lb_lenght.setBounds(0,90,80,20);
			lb_type = new JLabel("Type");
			lb_type.setBounds(0,59,60,20);
			tf_weight = new JTextField();
			tf_weight.setBounds(87,121,40,20);
			tf_weight.setText("0");
			tf_lenght = new JTextField();
			tf_lenght.setText("0");
			tf_lenght.setBounds(87,90,40,20);
			tf_type = new JTextField();
			tf_type.setBounds(87,59,100,20);
			panel_addVelo.add(tf_lenght);
			panel_addVelo.add(tf_weight);
			panel_addVelo.add(tf_type);
			panel_addVelo.add(lb_lenght);
			panel_addVelo.add(lb_weight);
			panel_addVelo.add(lb_type);
			
			btn_addVelo.addActionListener(e-> {
				System.out.println("add velo");
				double weightVelo = Double.parseDouble(tf_weight.getText());
				double lenghtVelo = Double.parseDouble(tf_lenght.getText());
				String typeVelo = tf_type.getText();
				String result = formValidationVelo(lenghtVelo,weightVelo,typeVelo);
				
				if(result!="") {
					JOptionPane.showMessageDialog(null, result);
				}else {
					Velo velo = new Velo(weightVelo,typeVelo,weightVelo,((Member)person));
					if(!Objects.isNull(velo)) {
						if(velo.getMemberVelo().createVelo(velo)) {
							JOptionPane.showMessageDialog(null, "Velo created");
							ConsultCalendar next = new ConsultCalendar(person);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
						}else {
							JOptionPane.showMessageDialog(null, "Can't create velo");
						}
					}
				}
			});
		//ADD VEHICLE if member hasn't
			if(Objects.isNull(((Member)person).getMemberVehicle())){
				JPanel panel_addVehicle = new JPanel();
				panel_addVehicle.setLayout(null);
				panel_addVehicle.setBounds(500,300,200,300);
				consultCalendar.getContentPane().add(panel_addVehicle);
				JButton btn_addVehicle = new JButton("Add vehicle");
				btn_addVehicle.setBounds(0,0,150,20);
				panel_addVehicle.add(btn_addVehicle);
				
				lb_vehicleTotalMemberSeats = new JLabel("Total member seat");
				lb_vehicleTotalMemberSeats.setBounds(0,121,200,20);
				lb_vehicleTotalVeloSeats = new JLabel("Total velo seat");
				lb_vehicleTotalVeloSeats.setBounds(0,90,200,20);
				tf_vehicleTotalMemberSeats = new JTextField();
				tf_vehicleTotalMemberSeats.setBounds(150,121,40,20);
				tf_vehicleTotalMemberSeats.setText("0");
				tf_vehicleTotalVeloSeats = new JTextField();
				tf_vehicleTotalVeloSeats.setText("0");
				tf_vehicleTotalVeloSeats.setBounds(150,90,40,20);

				panel_addVehicle.add(lb_vehicleTotalMemberSeats);
				panel_addVehicle.add(lb_vehicleTotalVeloSeats);
				panel_addVehicle.add(tf_vehicleTotalMemberSeats);
				panel_addVehicle.add(tf_vehicleTotalVeloSeats);
				
				btn_addVehicle.addActionListener(e-> {
					System.out.println("Add vehicle");
					
					int vehicleTotalMemberSeats = Integer.parseInt(tf_vehicleTotalMemberSeats.getText());
					int vehicleTotalVeloSeats = Integer.parseInt(tf_vehicleTotalVeloSeats.getText());

					String result = formValidationVehicle(vehicleTotalMemberSeats,vehicleTotalVeloSeats);
					
					if(result!="") {
						JOptionPane.showMessageDialog(null, result);
					}else {
						
						Vehicle vehicle = new Vehicle(vehicleTotalMemberSeats,vehicleTotalVeloSeats,((Member)person));
						System.out.println(vehicle.getDriver().getPseudo());
						
						if(!Objects.isNull(vehicle)) {
							if(((Member)person).createVehicle(vehicle)) {
								JOptionPane.showMessageDialog(null, "Vehicle created");
								ConsultCalendar next = new ConsultCalendar(person);
								JFrame consultCalendar = next.consultCalendar;
								changeFrame(consultCalendar);
							}else {
								JOptionPane.showMessageDialog(null, "Can't create Vehicle");
							}
						}
					}
				});
			}else {
				JPanel panel_addVehicle = new JPanel();
				panel_addVehicle.setLayout(null);
				panel_addVehicle.setBounds(500,300,200,300);
				consultCalendar.getContentPane().add(panel_addVehicle);
				JButton btn_addVehicle = new JButton("Add my vehicle to outing");
				btn_addVehicle.setBounds(0,0,200,20);
				panel_addVehicle.add(btn_addVehicle);
				
				btn_addVehicle.addActionListener(e->{
					if(outingExist.addVehicle(((Member)person).getMemberVehicle())) {
						JOptionPane.showMessageDialog(null,"Success : vehicle add to outing");
					}else {
						JOptionPane.showMessageDialog(null,"You can't add your vehicle to this outing");
					}
				});
			}

		// CHANGER DE CATEGORIES
			JPanel panel_otherCategories = new JPanel();
			panel_otherCategories.setLayout(null);
			panel_otherCategories.setBounds(0,30,800,20);
			consultCalendar.getContentPane().add(panel_otherCategories);
			// MY CATEGORIES = BOUTON
			btn_cat = null;
			btn_joinCat = null;
			
			List<Category> allMembCategories = ((Member)person).getMemberCategories();
			List<Category> allMembCategoriesNoJoin = new ArrayList<Category>();
			String nameMembCat;
			String nameCat;
		
			for(Category cat : allCategories ) {
				if(!allMembCategories.contains(cat)) {
					allMembCategoriesNoJoin.add(cat);
				}
			}
		// MEMBER IN
			// element 0 = calendar consulted, ignore it
			int posButtonX = 100;
			for(Category memCat : allMembCategories) {
				if(allMembCategories.indexOf(memCat) != 0 ) {
					nameMembCat = memCat.getClass().getSimpleName();
					btn_cat = new JButton(nameMembCat);
					btn_cat.setBounds(posButtonX,0,100,20);
					btn_cat.setActionCommand(String.valueOf(memCat.getNum()));
					panel_otherCategories.add(btn_cat);
					posButtonX+=100;
					btn_cat.addActionListener(e->{
						int numButtonCategory = Integer.parseInt(btn_cat.getActionCommand());
						System.out.println("Change category => "+numButtonCategory);
						/*
						for(Category cat : ((Member)person).getMemberCategories()) {
							if(cat.getNum() == numButtonCategory) {
								((Member)person).getMemberCategories().set(0,cat);
								ConsultCalendar next = new ConsultCalendar(person);
								JFrame consultCalendar = next.consultCalendar;
								changeFrame(consultCalendar);
							}
						}*/
					});
				}
			}
		// MEMBER NOT IN
			//TODO FIX ( change cat )
			if(allMembCategoriesNoJoin.size()>0) {
				for(Category memNotCat : allMembCategoriesNoJoin) {
					nameCat = memNotCat.getClass().getSimpleName();
					btn_joinCat = new JButton("Join "+nameCat);
					btn_joinCat.setBounds(posButtonX,0,150,20);
					btn_joinCat.setActionCommand(String.valueOf(memNotCat.getNum()));
					panel_otherCategories.add(btn_joinCat);
					posButtonX+=150;
			
					btn_joinCat.addActionListener(e->{
						int numButtonCategory = Integer.parseInt(btn_joinCat.getActionCommand());
						System.out.println("JOIN CAT => "+numButtonCategory);
						System.out.println(btn_joinCat.getText());
						/*
						int numButtonCategory = Integer.parseInt(btn_joinCat.getText());
						for(Category cat : ((Member)person).getMemberCategories()) {
							if(cat.getNum() == numButtonCategory) {
								((Member)person).getMemberCategories().set(0,cat);
								ConsultCalendar next = new ConsultCalendar(person);
								JFrame consultCalendar = next.consultCalendar;
								changeFrame(consultCalendar);
							}
						}
						*/
						
					});
				}
			}
		}
	}
	
// Utilities
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
	public void disp_outingInfo() {
		date = sdf.format(calendar.getDate());
		outingExist = null;
		//System.out.println(date);
		for(Outing outing : allOutings) {
			try {
				if(person instanceof Manager) {
					if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
						outingExist = outing;
						//System.out.println("Outing exist where you selected");
					}
				}
				if (person instanceof Member) {
					if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Member)person).getMemberCategories().get(0).getSingleCalendar().getNum()) {
						outingExist = outing;
						//System.out.println("Outing exist where you selected");
					}
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
			JLabel lb_needVeloSeats0 = new JLabel("Need velo seats = "+Integer.toString(outingExist.getNeedVeloSeats()));
			lb_needVeloSeats0.setBounds(0,140,300,20);
			JLabel lb_remainingMemberSeats0  = new JLabel("Remaining member seats = "+Integer.toString(outingExist.getRemainingMemberSeats()));
			lb_remainingMemberSeats0.setBounds(0,160,300,20);
			JLabel lb_remainingVeloSeats0 = new JLabel("Remaining velo seats = "+Integer.toString(outingExist.getRemainingVeloSeats()));
			lb_remainingVeloSeats0.setBounds(0,180,300,20);

			
			panel_outingInfo.add(lb_text);
			panel_outingInfo.add(lb_startPoint0);
			panel_outingInfo.add(lb_dateStart0);
			panel_outingInfo.add(lb_forfeit0);
			panel_outingInfo.add(lb_maxMemberSeat0);
			panel_outingInfo.add(lb_maxVeloSeats0);
			panel_outingInfo.add(lb_needMemberSeats0);
			panel_outingInfo.add(lb_needVeloSeats0);
			panel_outingInfo.add(lb_remainingMemberSeats0);
			panel_outingInfo.add(lb_remainingVeloSeats0);

			if(ConsultCalendar.person instanceof Manager) {
				dateChooser = new JDateChooser();
				dateChooser.setBounds(150,40,150,20);
				panel_outingInfo.add(dateChooser);
			}
		}
	}
	public void clean_panel(JPanel panel) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
	}
	public String formValidationVelo(double lenghtVelo,double weightVelo,String typeVelo) {
		String result="";
	
		if(weightVelo <= 0 || weightVelo >= 5) {
			result+="Enter weight velo in kg format";
			result+="\n";
		}
		if(lenghtVelo <= 0 || lenghtVelo >=2.5) {
			result+="Enter lenght velo in m format";
			result+="\n";
		}			if(typeVelo.equals("") || Objects.isNull(typeVelo)) {
			result+="Enter type velo";
			result+="\n";
		}
		return result;
	}

	public String formValidationVehicle(int totalMemberSeat,int totalVeloSeat) {
		String result="";
		if(totalMemberSeat <=0 || totalMemberSeat >8) {
			result+="Enter real total member seat (1-8)";
			result+="\n";
		}
		if(totalVeloSeat <=0 || totalVeloSeat >8) {
			result+="Enter real total velo seat (1-8)";
			result+="\n";
		}
		return result;
	}
}
