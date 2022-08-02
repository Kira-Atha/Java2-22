package be.huygebaert.program;


import java.awt.EventQueue;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import be.huygebaert.POJO.Register;
import be.huygebaert.POJO.Vehicle;
import be.huygebaert.POJO.Velo;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

public class ConsultCalendar {

	public JFrame consultCalendar;
	JButton btn_create,btn_delete,btn_update,btn_logout,btn_cat,btn_joinCat;
	private static Person person;
	private JLabel lb_message,lb_legend,lb_startPoint,lb_forfeit,lb_maxMember,lb_maxVelo,lb_titleForm,lb_weight, lb_lenght, lb_type,lb_vehicleTotalMemberSeats,lb_vehicleTotalVeloSeats;
	private JPanel panel_register,panel_outingInfo,panel_formOuting,panel_buttons,panel_calendar;
	private JTextField tf_startPoint,tf_forfeit,tf_maxMember,tf_maxVelo,tf_weight,tf_type,tf_lenght,tf_vehicleTotalMemberSeats,tf_vehicleTotalVeloSeats;
	private String date;
	private List <Outing> allOutings;
	private List <Category> allCategories;
	private List <Velo> allVelos;
	private List <Vehicle> allVehicles;
	private List <Register> allRegisters;
	private JDateChooser dateChooser;
	private SimpleDateFormat sdf;
	private JCalendar calendar;
	private Outing outingExist;
	private JComboBox<Object> selectVelo;
	boolean reg_passenger,reg_velo = false;
	private String my_registers,my_drivings,cat_outings;
	private SimpleDateFormat dayMonth = new SimpleDateFormat("dd/MM");
	
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
		int num_cat =0;
		
		if(person instanceof Member) {
			num_cat = ((Member)person).getMemberCategories().get(0).getNum();
			
			allCategories = Category.getAllCategories();
			allVelos = Velo.getAllVelos();
			allVehicles = Vehicle.getAllVehicles();
			allRegisters = Register.getAllRegisters();
	
			if(!Objects.isNull(allRegisters)) {
				my_registers = "My register for this category :";
				for(Register reg :allRegisters) {
					if(reg.getOuting().getOutingCalendar().getNum() == ((Member)person).getMemberCategories().get(0).getNum() && reg.getMember().equals(person)) {
						my_registers += dayMonth.format(reg.getOuting().getStartDate())+" ";
					}
				}
			}
			
			
			if(!Objects.isNull(allOutings)) {
				my_drivings ="I drive for this category :";
				for(Outing out : allOutings) {
					for(Vehicle vehicle : out.getOutingVehicles()) {
						if(vehicle.getDriver().getId() == ((Member)person).getId() && out.getOutingCalendar().getNum() == ((Member)person).getMemberCategories().get(0).getNum()){
							my_drivings+= dayMonth.format(out.getStartDate())+" ";
						}
					}
				}
			}
			
			if(!Objects.isNull(allOutings) && !Objects.isNull(allRegisters)) {
				for(Outing out:allOutings) {
					for(Register reg : allRegisters) {
						if(reg.getOuting().getNum() == out.getNum()) {
							for(Vehicle veh : out.getOutingVehicles()) {
								if(reg.isReg_passenger()) {
									if(!veh.addPassenger(reg.getMember())) {
										//System.out.println(veh+"veh full member");
										// next vehicle
										continue;
									}
								}
								if(reg.isReg_velo()) {
									if(!veh.addVelo(reg.getVelo())) {
										// next vehicle
										continue;
									}
								}
							}
						}
					}
				}
			}
		}else{
			num_cat = ((Manager)person).getCategory().getNum();
		}
		
		if(!Objects.isNull(allOutings)) {
			cat_outings="Category outing :";
			for(Outing out : allOutings) {
				//.get(0) = la catégorie(le calendrier) consultée
				if(out.getOutingCalendar().getNum() == num_cat) {
					cat_outings+= dayMonth.format(out.getStartDate())+" ";
				}
			}
		}
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
		// Permit to get date when month change
		calendar.getMonthChooser().addPropertyChangeListener("month", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				clean_panel(panel_outingInfo);
				disp_outingInfo();
			}
		});
		//  Permit to get date when year change
		calendar.getYearChooser().addPropertyChangeListener("year", new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				clean_panel(panel_outingInfo);
				disp_outingInfo();
			}
		});
		
		if(cat_outings!="Category outings :") {
			JLabel lb_catOutings = new JLabel(cat_outings);
			lb_catOutings.setBounds(0,40,300,40);
			consultCalendar.getContentPane().add(lb_catOutings);
			lb_catOutings.setVisible(true);
		}
		
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
			btn_create.setActionCommand("0");
			btn_create.setBounds(0,0,50,50);
			panel_buttons.add(btn_create);
			
			btn_create.addActionListener(e-> {
				String startPoint = tf_startPoint.getText();
				double forfeit=0;
				int maxMember=0;
				int maxVelo=0;
				String result = "";
				try {
					forfeit = Double.parseDouble(tf_forfeit.getText());
					maxMember = Integer.parseInt(tf_maxMember.getText());
					maxVelo = Integer.parseInt(tf_maxVelo.getText());
				}catch(NumberFormatException nfe) {
					result+="Wrong format!";
					result+="\n";
				}
				try {
					result += formValidation(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo);
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
					if(!exist) {
						Outing outing = null;
						try {
							outing = new Outing(startPoint,sdf.parse(date),forfeit,maxMember,maxVelo,((Manager)person).getCategory().getSingleCalendar());
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
						if(((Manager)person).manageCalendar(Integer.parseInt(btn_create.getActionCommand()),outing)) {
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
			btn_update.setActionCommand("1");
			btn_update.setBounds(0,50,50,50);
			panel_buttons.add(btn_update);
			
			btn_update.addActionListener(e-> {
				String newStartPoint = tf_startPoint.getText();
				String result = "";
				double newForfeit = 0;
				int newMaxMember = 0;
				int newMaxVelo = 0;

				try {
					newForfeit = Double.parseDouble(tf_forfeit.getText());
					newMaxMember = Integer.parseInt(tf_maxMember.getText());
					newMaxVelo = Integer.parseInt(tf_maxVelo.getText());
				}catch(NumberFormatException nfe) {
					result +="Wrong format!"; 
					result +="\n";
				}

				String newDate = null;
				if(!Objects.isNull(this.dateChooser) && !Objects.isNull(this.dateChooser.getDate())) {
					newDate = sdf.format(dateChooser.getDate());
					try {
						result += formValidation(newStartPoint,sdf.parse(newDate),newForfeit,newMaxMember,newMaxVelo);
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
									//allow to keep date
									if(newDate.compareTo(date) == 0) {
										idOuting = ou.getNum();
										break;
									}
									JOptionPane.showMessageDialog(null,"An other outing already exist for this calendar on this date");
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
						if(!exist) {
							Outing outing = null;
							try {
								outing = new Outing(newStartPoint,sdf.parse(newDate),newForfeit,newMaxMember,newMaxVelo,((Manager)person).getCategory().getSingleCalendar());
								outing.setNum(idOuting);
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
							if(((Manager)person).manageCalendar(Integer.parseInt(btn_update.getActionCommand()),outing)) {
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
			btn_delete.setActionCommand("2");
			btn_delete.setBounds(0,100,50,50);
			panel_buttons.add(btn_delete);
			
			btn_delete.addActionListener(e-> {
				Outing selectedOuting = null;
				for(Outing outing : allOutings) {
					try {
						if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
							selectedOuting = outing;
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				if(!Objects.isNull(selectedOuting) && ((Manager)person).manageCalendar(Integer.parseInt(btn_delete.getActionCommand()),selectedOuting)) {
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
		
			if(my_registers!="My register for this category :") {
				JLabel lb_myRegisters = new JLabel(my_registers);
				lb_myRegisters.setBounds(0,450,300,40);
				consultCalendar.getContentPane().add(lb_myRegisters);
				lb_myRegisters.setVisible(true);
			}
			if(my_drivings!="I drive for this category :") {
				JLabel lb_myDrivings = new JLabel(my_drivings);
				lb_myDrivings.setBounds(0,500,300,40);
				consultCalendar.getContentPane().add(lb_myDrivings);
				lb_myDrivings.setVisible(true);
			}

			for(Velo velo : allVelos) {
				if(velo.getMemberVelo().getId() == ((Member)person).getId() && !((Member)person).getMemberVelos().contains(velo))
					((Member)person).getMemberVelos().add(velo);
			}
			
			
			
			for(Vehicle vehicle : allVehicles) {
				if(vehicle.getDriver().getId() == ((Member)person).getId()) {
					((Member)person).setMemberVehicle(vehicle);
				}
			}
		
			panel_register = new JPanel();
			panel_register.setBounds(0,300,250,400);
			panel_register.setLayout(null);
			consultCalendar.getContentPane().add(panel_register);
			if(!Objects.isNull(outingExist)) {
				create_panelRegister();
			}
		
		//ADD VELO to member
			JPanel panel_addVelo = new JPanel();
			panel_addVelo.setLayout(null);
			panel_addVelo.setBounds(300,300,200,300);
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
				String result="";
				double weightVelo =0;
				double lenghtVelo = 0;
				try {
					weightVelo = Double.parseDouble(tf_weight.getText());
					lenghtVelo = Double.parseDouble(tf_lenght.getText());
				}catch(NumberFormatException nfe) {
					result+="Wrong format!";
					result+="\n";
				}
				String typeVelo = tf_type.getText();
				result += formValidationVelo(lenghtVelo,weightVelo,typeVelo);
				
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
		//ADD VEHICLE if member hasn't and add vehicle to selected outing
			if(Objects.isNull(((Member)person).getMemberVehicle())){
				JPanel panel_addVehicle = new JPanel();
				panel_addVehicle.setLayout(null);
				panel_addVehicle.setBounds(600,300,200,300);
				consultCalendar.getContentPane().add(panel_addVehicle);
				JButton btn_addVehicle = new JButton("Add vehicle");
				btn_addVehicle.setBounds(0,0,150,20);
				panel_addVehicle.add(btn_addVehicle);
				
				lb_vehicleTotalMemberSeats = new JLabel("Total member seat");
				lb_vehicleTotalMemberSeats.setBounds(0,121,200,20);
				lb_vehicleTotalVeloSeats = new JLabel("Total velo seat");
				lb_vehicleTotalVeloSeats.setBounds(0,90,200,20);
				tf_vehicleTotalMemberSeats = new JTextField();
				tf_vehicleTotalMemberSeats.setBounds(150,121,30,20);
				tf_vehicleTotalMemberSeats.setText("0");
				tf_vehicleTotalVeloSeats = new JTextField();
				tf_vehicleTotalVeloSeats.setText("0");
				tf_vehicleTotalVeloSeats.setBounds(150,90,30,20);

				panel_addVehicle.add(lb_vehicleTotalMemberSeats);
				panel_addVehicle.add(lb_vehicleTotalVeloSeats);
				panel_addVehicle.add(tf_vehicleTotalMemberSeats);
				panel_addVehicle.add(tf_vehicleTotalVeloSeats);
				
				btn_addVehicle.addActionListener(e-> {
					int vehicleTotalMemberSeats = 0;
					int vehicleTotalVeloSeats = 0;
					String result = "";
					try {
						vehicleTotalMemberSeats = Integer.parseInt(tf_vehicleTotalMemberSeats.getText());
						vehicleTotalVeloSeats = Integer.parseInt(tf_vehicleTotalVeloSeats.getText());
					}catch(NumberFormatException nfe) {
						result+="Wrong format!";
						result+="\n";
					}
					result += formValidationVehicle(vehicleTotalMemberSeats,vehicleTotalVeloSeats);
					
					if(result!="") {
						JOptionPane.showMessageDialog(null, result);
					}else {
						Vehicle vehicle = new Vehicle(vehicleTotalMemberSeats,vehicleTotalVeloSeats,((Member)person));
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
					if(!Objects.isNull(outingExist)) {
						if(outingExist.getStartDate().compareTo(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) > 0) {
							boolean alreadyAdd = false;
							boolean alreadyDriveToday = false;
							Category categoryDriving = null;
							for(Vehicle vehicle : outingExist.getOutingVehicles()) {
								if(vehicle.getNum() == ((Member)person).getMemberVehicle().getNum()) {
									alreadyAdd = true;
									break;
								}
							}
							for(Outing out : allOutings ) {
								for(Vehicle veh : out.getOutingVehicles()) {
									if(veh.getNum() == ((Member)person).getMemberVehicle().getNum() && out.getStartDate() == outingExist.getStartDate()) {
										alreadyDriveToday = true;
										categoryDriving = Category.getCategory(out.getOutingCalendar().getNum());
										break;
									}
								}
							}
							if(!alreadyAdd) {
								if(!alreadyDriveToday) {
									if(outingExist.addVehicle(((Member)person).getMemberVehicle())) {
										JOptionPane.showMessageDialog(null,"Success : vehicle add to outing");
										ConsultCalendar next = new ConsultCalendar(person);
										JFrame consultCalendar = next.consultCalendar;
										changeFrame(consultCalendar);
									}else {
										JOptionPane.showMessageDialog(null,"Don't need seats");
									}
								}else {
									JOptionPane.showMessageDialog(null, "You already drive this day to other outing in "+categoryDriving.getClass().getSimpleName());
								}
							}else {
								JOptionPane.showMessageDialog(null, "You already drive for this outing");
							}
						}else {
							JOptionPane.showMessageDialog(null, "You can't add your vehicle to an already outing finished");
						}
					}else {
						JOptionPane.showMessageDialog(null,"Select an outing");
					}
				});
			}

		// CHANGE CALENDAR CATEGORY AND JOIN
			JPanel panel_otherCategories = new JPanel();
			panel_otherCategories.setLayout(null);
			panel_otherCategories.setBounds(0,30,800,20);
			consultCalendar.getContentPane().add(panel_otherCategories);
			
			
			List<Category> allMembCategories = ((Member)person).getMemberCategories();
			List<Category> allMembCategoriesNoJoin = new ArrayList<Category>();
		
			for(Category cat : allCategories ) {
				if(!allMembCategories.contains(cat)) {
					allMembCategoriesNoJoin.add(cat);
				}
			}
			JButton[] btn_cat = new JButton[allCategories.size()];
			JButton[] btn_joinCat = new JButton[allMembCategoriesNoJoin.size()];
		// MEMBER IN
			// element 0 = calendar consulted, ignore it
			int posButtonX = 100;
			
			for(int i = 0; i <= allMembCategories.size()-1;i++) {
				if(allMembCategories.indexOf(allMembCategories.get(i)) !=0) {
					btn_cat[i] = new JButton(allMembCategories.get(i).getClass().getSimpleName());
					btn_cat[i].setBounds(posButtonX,0,100,20);
					btn_cat[i].setActionCommand(String.valueOf(allMembCategories.get(i).getNum()));
					panel_otherCategories.add(btn_cat[i]);
					posButtonX+=100;
					btn_cat[i].addActionListener(e->{
						int numButtonCategory = Integer.parseInt(e.getActionCommand());
						Category categoryToChange = Category.getCategory(numButtonCategory);
					//Swap and reload
						((Member)person).getMemberCategories().add(((Member)person).getMemberCategories().get(0));
						((Member)person).getMemberCategories().remove(categoryToChange);
						((Member)person).getMemberCategories().set(0,categoryToChange);
						ConsultCalendar next = new ConsultCalendar(person);
						JFrame consultCalendar = next.consultCalendar;
						changeFrame(consultCalendar);
					});
				}
			}
		// MEMBER NOT IN
			if(allMembCategoriesNoJoin.size() >0) {
				for(int i = 0; i <= allMembCategoriesNoJoin.size()-1;i++) {
					btn_joinCat[i] = new JButton("Join "+allMembCategoriesNoJoin.get(i).getClass().getSimpleName());
					btn_joinCat[i].setBounds(posButtonX,0,150,20);
					btn_joinCat[i].setActionCommand(String.valueOf(allMembCategoriesNoJoin.get(i).getNum()));
					panel_otherCategories.add(btn_joinCat[i]);
					posButtonX+=150;
					btn_joinCat[i].addActionListener(e->{
						int numButtonCategory = Integer.parseInt(e.getActionCommand());
						Category categoryToJoin = Category.getCategory(numButtonCategory);
						if(((Member)person).joinCategory(categoryToJoin)) {
							JOptionPane.showMessageDialog(null,"You join "+categoryToJoin.getClass().getSimpleName()+" category, +20€ ");
							ConsultCalendar next = new ConsultCalendar(person);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
						}
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
		for(Outing outing : allOutings) {
			try {
				if(person instanceof Manager) {
					if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Manager)person).getCategory().getSingleCalendar().getNum()) {
						outingExist = outing;
					}
				}
				if (person instanceof Member) {
					if(outing.getStartDate().compareTo(sdf.parse(date)) == 0 && outing.getOutingCalendar().getNum() == ((Member)person).getMemberCategories().get(0).getSingleCalendar().getNum()) {
						outingExist = outing;
						
						if(!Objects.isNull(allRegisters)) {
							for(Register reg :allRegisters) {
								if(reg.getOuting().getOutingCalendar().getNum() == outingExist.getOutingCalendar().getNum()) {
									outingExist.getOutingRegisters().add(reg);
								}
							}
						}
					}
					if(!Objects.isNull(outingExist)) {
						if(!Objects.isNull(panel_register)) {
							clean_panel(panel_register);
							create_panelRegister();
						}
					}else {
						if(!Objects.isNull(panel_register)) {
							clean_panel(panel_register);
						}
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
	public void create_panelRegister() {
		//REGISTER ON OUTING
		JButton btn_register = new JButton("Register");
		btn_register.setBounds(0,0,100,20);
		panel_register.add(btn_register);
		
		JCheckBox check_velo = new JCheckBox("I want to put my bike in a car");
		check_velo.setBounds(0,30,200,20);
		panel_register.add(check_velo);
		
		
		selectVelo = new JComboBox<Object>(((Member)person).getMemberVelos().toArray());
		selectVelo.setBounds(5,70,150,20);
		panel_register.add(selectVelo);
		selectVelo.setVisible(true);
		
		check_velo.addActionListener(e->{
			if(check_velo.isSelected()) {
				if(outingExist.getOutingVehicles().size()==0) {
					JOptionPane.showMessageDialog(null,"No car available, try again later");
				}else {
				}
				reg_velo = true;
			}
		});
		check_velo.addActionListener(e->{
			if(!check_velo.isSelected()) {
				reg_velo = false;
			}
		});
		
		JCheckBox check_passenger = new JCheckBox("I want to come by car");
		check_passenger.setBounds(0,120,150,20);
		panel_register.add(check_passenger);
		check_passenger.addActionListener(e->{
			if(check_passenger.isSelected()) {
				reg_passenger = true;
				if(outingExist.getOutingVehicles().size()==0) {
					JOptionPane.showMessageDialog(null,"No car available, try again later");
				}
			}
		});
		check_passenger.addActionListener(e->{
			if(!check_passenger.isSelected()) {
				reg_passenger = false;
			}
		});
		
		btn_register.addActionListener(e-> {
			if(!Objects.isNull(outingExist)) {
				Velo veloSelected  = (Velo) selectVelo.getSelectedItem();
				boolean vehiMAvailable = false;
				boolean vehiVAvailable = false;
				Vehicle vehicleAvailable = null;
				if(reg_velo) {
					for(Vehicle vehicle : outingExist.getOutingVehicles()) {
						if(vehicle.getVelos().size() < vehicle.getTotalVeloSeats()) {
							vehicleAvailable = vehicle;
							vehiVAvailable = true;
							break;
						}
					}
					if(!vehiVAvailable) {
						JOptionPane.showMessageDialog(null, "No Velo seats available");
					}else {
						vehicleAvailable.addVelo(veloSelected);
					}
				}
				if(reg_passenger) {
					for(Vehicle vehicle : outingExist.getOutingVehicles()) {
						if(vehicle.getPassengers().size() < vehicle.getTotalMemberSeats()) {
							vehicleAvailable = vehicle;
							vehiMAvailable = true;
							break;
						}
					}
 
					if(!vehiMAvailable) {
						JOptionPane.showMessageDialog(null, "No Member seats available");
					}else {
						vehicleAvailable.addPassenger((Member)person);
					}
				}
			//VERIF
				// Ajouter vélo dans véhicule ET place Vélo disponible ET ajouter moi dans voiture ET place Membre disponible
				if( (reg_velo && vehiVAvailable) && (reg_passenger && vehiMAvailable)) {
					Register register = new Register(reg_passenger,reg_velo,(Member)person,veloSelected,outingExist);
					create_register(register);
					// Je ne veux pas ajouter mon vélo dans la voiture ni moi
				}else if((!reg_velo) && (!reg_passenger)) {
					Register register = new Register(reg_passenger,reg_velo,(Member)person,veloSelected,outingExist);
					create_register(register);
					// Je ne veux pas ajouter mon vélo. Je veux m'ajouter à un véhicule et il y en a un de disponible
				}else if((!reg_velo) && (reg_passenger && vehiMAvailable)) {
					Register register = new Register(reg_passenger,reg_velo,(Member)person,veloSelected,outingExist);
					create_register(register);
					// Je veux ajouter mon vélo à un véhicule et il y en a un de disponible. Je ne veux pas être passager.
				}else if((reg_velo && vehiVAvailable) && (!reg_passenger)){
					Register register = new Register(reg_passenger,reg_velo,(Member)person,veloSelected,outingExist);
					create_register(register);
					// Autre => Voiture non dispo quand je veux ajouter mon vélo ou moi dedans
				}else {
					JOptionPane.showMessageDialog(null, "You can't register to this outing if you want use car (no seats availables)");
				}
			}else {
				JOptionPane.showMessageDialog(null,"Select an outing");
			}
			
		});
	}
	
	public void create_register(Register register) {
		boolean alreadyParticipate = false;
		if(outingExist.getStartDate().compareTo(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) > 0) {
			if(!Objects.isNull(register)) {
				for(Register reg : outingExist.getOutingRegisters()) {
					if(reg.getMember().getId() == register.getMember().getId()) {
						JOptionPane.showMessageDialog(null,"You are already participating in this outing");
						alreadyParticipate = true;
					}
				}
			}
			if(!alreadyParticipate) {
				if(outingExist.getStartDate().compareTo(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())) > 0) {
					if(outingExist.createRegister(register)) {
						JOptionPane.showMessageDialog(null,"Register success");
						ConsultCalendar next = new ConsultCalendar(person);
						JFrame consultCalendar = next.consultCalendar;
						changeFrame(consultCalendar);
					}else {
						JOptionPane.showMessageDialog(null,"Register impossible > not seats available enough");
					}
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "You can't register to outing already finish");
		}
	}
}
