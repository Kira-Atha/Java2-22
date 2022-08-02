package be.huygebaert.program;
import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import be.huygebaert.POJO.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.awt.Font;

public class SignUp {

	public JFrame signUp;
	private JTextField tf_firstname, tf_lastname, tf_tel, tf_pseudo,tf_weight,tf_type,tf_lenght;
	private JPasswordField pf_password;
	private JLabel lb_firstname, lb_lastname, lb_password, lb_tel, lb_pseudo,lb_typeAccount,lb_typeCategory,lb_personalInfo,lb_registration,lb_weight, lb_lenght, lb_type,lb_txtAddVelo;
	private JButton btn_send, btn_back;
	private ButtonGroup typeAccountGroup, typeCategoryGroup;
	private JRadioButton rbtn_manager, rbtn_member, rbtn_treasurer,rbtn_category;
	private JPanel panel_personelInformations,panel_rbtn_TypeCategory,panel_addVelo,panel_rbtn_TypeAccount;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
					window.signUp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SignUp() {
		initialize();
	}

	private void initialize() {
		signUp = new JFrame("SignUp");
		signUp.setBounds(0, 0, 800, 600);
		signUp.setLocationRelativeTo(null);
		signUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUp.getContentPane().setLayout(null);
		panel_personelInformations = new JPanel();
		panel_personelInformations.setBounds(134,147,500,162);
		panel_personelInformations.setLayout(null);
		signUp.getContentPane().add(panel_personelInformations);
		
		panel_addVelo = new JPanel();
		panel_addVelo.setBounds(464, 337, 187, 142);
		panel_addVelo.setLayout(null);
		signUp.getContentPane().add(panel_addVelo);
		
		tf_firstname = new JTextField();
		tf_firstname.setBounds(100,30,150,20);
		tf_lastname = new JTextField();
		tf_lastname.setBounds(100,50,150,20);
		tf_tel = new JTextField();
		tf_tel.setBounds(100,70,150,20);
		tf_pseudo = new JTextField();
		tf_pseudo.setBounds(100,90,150,20);
		pf_password = new JPasswordField();
		pf_password.setBounds(100,120,150,20);
		lb_firstname = new JLabel("Firstname");
		lb_firstname.setBounds(0,30,90,20);
		lb_lastname = new JLabel("Lastname");
		lb_lastname.setBounds(0,50,90,20);
		lb_tel = new JLabel("Tel");
		lb_tel.setBounds(0,70,90,20);
		lb_pseudo = new JLabel("Pseudo");
		lb_pseudo.setBounds(0,90,90,20);
		lb_password = new JLabel("Password");
		lb_password.setBounds(0,120,90,20);
		btn_send = new JButton("Send");
		btn_back= new JButton("Back");
		lb_weight = new JLabel("Weight (kg)");
		lb_weight.setBounds(0,121,80,20);
		lb_lenght = new JLabel("Lenght (m)");
		lb_lenght.setBounds(0,90,80,20);
		lb_type = new JLabel("Type");
		lb_type.setBounds(0,59,60,20);
		tf_weight = new JTextField();
		tf_weight.setBounds(87,121,100,20);
		tf_weight.setText("0");
		tf_lenght = new JTextField();
		tf_lenght.setText("0");
		tf_lenght.setBounds(87,90,100,20);
		tf_type = new JTextField();
		tf_type.setBounds(87,59,100,20);
		lb_txtAddVelo = new JLabel("Add Velo");
		lb_txtAddVelo.setBounds(53,0,100,20);
		
		JCheckBox chkb_Pass = new JCheckBox("Show password");
		chkb_Pass.setBounds(250, 120, 150, 20);
		panel_personelInformations.add(chkb_Pass);

		chkb_Pass.addActionListener(e -> {
			if(chkb_Pass.isSelected()) {
				pf_password.setEchoChar((char)0);
			}else {
				pf_password.setEchoChar('*');
			}
		});
		
		panel_personelInformations.add(lb_firstname);
		panel_personelInformations.add(tf_firstname);
		panel_personelInformations.add(lb_lastname);
		panel_personelInformations.add(tf_lastname);
		panel_personelInformations.add(lb_password);
		panel_personelInformations.add(pf_password);
		panel_personelInformations.add(lb_tel);
		panel_personelInformations.add(tf_tel);
		panel_personelInformations.add(tf_pseudo);
		panel_personelInformations.add(lb_pseudo);
		panel_addVelo.add(tf_lenght);
		panel_addVelo.add(tf_weight);
		panel_addVelo.add(tf_type);
		panel_addVelo.add(lb_lenght);
		panel_addVelo.add(lb_weight);
		panel_addVelo.add(lb_type);
		panel_addVelo.add(lb_txtAddVelo);
		
		lb_personalInfo = new JLabel("Enter your personal information");
		lb_personalInfo.setBounds(100, 5, 220, 14);
		panel_personelInformations.add(lb_personalInfo);
		
		btn_send.setBounds(500,500,70,50);
		btn_back.setBounds(400,500,70,50);
		signUp.getContentPane().add(btn_send);
		signUp.getContentPane().add(btn_back);
		
		panel_rbtn_TypeAccount = new JPanel();
		panel_rbtn_TypeAccount.setLayout(new GridLayout(3,1));
		panel_rbtn_TypeAccount.setBounds(210,363,100,100);
	
		rbtn_manager = new JRadioButton("Manager");
		rbtn_manager.setActionCommand("Manager");
		rbtn_treasurer = new JRadioButton("Treasurer");
		rbtn_treasurer.setActionCommand("Treasurer");
		rbtn_member = new JRadioButton("Member");
		rbtn_member.setActionCommand("Member");
		
		typeAccountGroup = new ButtonGroup();
		typeAccountGroup.add(rbtn_manager);
		typeAccountGroup.add(rbtn_treasurer);
		typeAccountGroup.add(rbtn_member);
		panel_rbtn_TypeAccount.add(rbtn_member);
		panel_rbtn_TypeAccount.add(rbtn_manager);
		panel_rbtn_TypeAccount.add(rbtn_treasurer);
		
		signUp.getContentPane().add(panel_rbtn_TypeAccount);
		
		panel_rbtn_TypeCategory = new JPanel();
		panel_rbtn_TypeCategory.setLayout(new GridLayout(4,1));
		panel_rbtn_TypeCategory.setBounds(331,363,100,100);
		typeCategoryGroup = new ButtonGroup();

		List <Category> categories = new ArrayList <Category>();
		categories = Category.getAllCategories();
		
		int count = 0;
		for(Category category : categories) {
			++count;
			rbtn_category = new JRadioButton(category.getClass().getSimpleName());
			rbtn_category.setActionCommand(String.valueOf(count));
			//System.out.println("BOUTON ++COUNT"+count);
			typeCategoryGroup.add(rbtn_category);
			panel_rbtn_TypeCategory.add(rbtn_category);
		}

		signUp.getContentPane().add(panel_rbtn_TypeCategory);
		lb_registration = new JLabel("Registration");
		lb_registration.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_registration.setBounds(298, 51, 133, 26);
		signUp.getContentPane().add(lb_registration);
		
		lb_typeAccount = new JLabel("Account type");
		lb_typeAccount.setBounds(235, 338, 75, 14);
		signUp.getContentPane().add(lb_typeAccount);
		
		lb_typeCategory = new JLabel("Category");
		lb_typeCategory.setBounds(348, 338, 72, 14);
		signUp.getContentPane().add(lb_typeCategory);
		
		panel_addVelo.setVisible(false);

		/*
		 * 
		 * ACTIONS
		 */
		btn_send.addActionListener(e->{
			double weightVelo = 0;
			double lenghtVelo = 0;
			String result ="";
			String firstname = tf_firstname.getText();
			String lastname = tf_lastname.getText();
			String tel = tf_tel.getText();
			String pseudo = tf_pseudo.getText();
			String password = String.valueOf(pf_password.getPassword());
			boolean checkedAccount = typeAccountGroup.getSelection() !=null;
			String account = checkedAccount? typeAccountGroup.getSelection().getActionCommand():"";
			boolean checkedCategory = typeCategoryGroup.getSelection() !=null;
			String category = checkedCategory? typeCategoryGroup.getSelection().getActionCommand():"";
			try {
				weightVelo = Double.parseDouble(tf_weight.getText());
				lenghtVelo = Double.parseDouble(tf_lenght.getText());
			}catch(NumberFormatException nfe) {
				result+="Wrong format !";
				result+="\n";
			}

			String typeVelo = tf_type.getText();
			result += formValidation(firstname,lastname,password,pseudo,tel,account,category,lenghtVelo,weightVelo,typeVelo);
			
			
			if(result!="") {
				JOptionPane.showMessageDialog(null, result);
			}else {			
				if(account.equals("Treasurer")) {
					Treasurer treasurer = new Treasurer(firstname,lastname,password,tel,pseudo);
					if(!Objects.isNull(treasurer)) {
						if(treasurer.signUp()) {
							MonitorPayments next = new MonitorPayments(treasurer);
							JFrame monitorPayments = next.monitorPayments;
							changeFrame(monitorPayments);
						}else {
							JOptionPane.showMessageDialog(null, "This pseudo is already used");
						}
					}
				}
				if(account.equals("Member")) {
					//System.out.println("Send => "+category);
					Category categoryConstMember = Category.getCategory(Integer.parseInt(category));
					Member member = new Member(firstname,lastname,password,tel,pseudo,categoryConstMember,typeVelo,weightVelo,lenghtVelo);
					if(!Objects.isNull(member)) {
						if(member.signUp()) {
							ConsultCalendar next = new ConsultCalendar(member);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
						}else {
							JOptionPane.showMessageDialog(null,"This pseudo is already used");
						}
					}
				}
				if(account.equals("Manager")) {
					Category categoryConst = Category.getCategory(Integer.parseInt(category));
					Manager manager = new Manager(firstname,lastname,password,tel,pseudo,categoryConst);

					if(!Objects.isNull(manager)) {
						if(manager.signUp()){
							ConsultCalendar next = new ConsultCalendar(manager);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
						}else {
							JOptionPane.showMessageDialog(null, "This category already have a manager or this pseudo is already used.");
						}
					}
				}
			}
		});
		btn_back.addActionListener(e-> {
			Init previous = new Init();
			JFrame home = previous.init;
			changeFrame(home);
		});
		
		// Afficher le formulaire d'ajout de vélo lorsque membre est choisi
		
		rbtn_member.addActionListener(e-> {
			if(rbtn_member.isSelected()) {
					panel_addVelo.setVisible(true);
				}else {
				panel_addVelo.setVisible(false);
			}
		});
		
		rbtn_treasurer.addActionListener(e-> {
			if(rbtn_member.isSelected()) {
					panel_addVelo.setVisible(true);
				}else {
				panel_addVelo.setVisible(false);
			}
		});
		
		rbtn_manager.addActionListener(e-> {
			if(rbtn_member.isSelected()) {
					panel_addVelo.setVisible(true);
				}else {
				panel_addVelo.setVisible(false);
			}
		});
		
		// Rendre les catégories invisibles pour le trésorier
		rbtn_treasurer.addActionListener(e-> {
			if(rbtn_treasurer.isSelected()) {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(false);
				}
			}else {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(true);
				}
			}
		});
		
		rbtn_manager.addActionListener(e-> {
			if(rbtn_treasurer.isSelected()) {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(false);
				}
			}else {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(true);
				}
			}
		});
		rbtn_member.addActionListener(e-> {
			if(rbtn_treasurer.isSelected()) {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(false);
				}
			}else {
				for (Enumeration<AbstractButton> buttons = typeCategoryGroup.getElements(); buttons.hasMoreElements();) {
					AbstractButton button = buttons.nextElement();
					button.setVisible(true);
				}
			}
		});
		
		signUp.setVisible(true);
	}
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		signUp.dispose();
	}
	
	public String formValidation(String firstname, String lastname, String password, String pseudo, String tel, String typeAccount, String typeCategory,double lenghtVelo,double weightVelo,String typeVelo) {
		String result="";
		
		if(firstname.equals("") || firstname.length() < 3 || Member.isNumeric(firstname)) {
			result+="Invalid firstname. Firstname must be > 3 characters and doesn't contain number.";
			result+="\n";
		}
		if(lastname.equals("") || lastname.length() < 3 || Member.isNumeric(lastname)) {
			result+="Invalid lastname. Lastname must be > 3 characters and doesn't contain number.";
			result+="\n";
		}
		if(password.equals("") || password.length() < 4) {
			result+="Invalid password. Password must be > 4 characters.";
			result+="\n";
		}
		if(pseudo.equals("") || pseudo.length() < 3) {
			result+="Invalid pseudo. Pseudo must be > 3 characters.";
			result+="\n";
		}
		if(tel.equals("") || tel.length() < 9 || tel.length()>10 || !Member.isNumeric(tel)) {
			result+="Invalid tel. Enter 071000000 or 0400000000 format";
			result+="\n";
		}
		if(typeAccount.equals("") || typeAccount.equals(null)) {
			result+="Select account type.";
			result+="\n";
		}
		if(typeAccount.equals("Treasurer") == false && typeCategory.equals("") || typeCategory.equals(null)) {
			result+="Select category type.";
			result+="\n";
		}
		if(typeAccount.equals("Member")) {
			if(weightVelo <= 0 || weightVelo >= 5) {
				result+="Enter weight velo in kg format.";
				result+="\n";
			}
			if(lenghtVelo <= 0 || lenghtVelo >=2.5) {
				result+="Enter lenght velo in m format.";
				result+="\n";
			}			if(typeVelo.equals("") || Objects.isNull(typeVelo)) {
				result+="Enter type velo.";
				result+="\n";
			}
		}
		return result;
	}
}