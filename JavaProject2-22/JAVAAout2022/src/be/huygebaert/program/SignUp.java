package be.huygebaert.program;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;
import be.huygebaert.POJO.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.awt.event.ActionEvent;

public class SignUp {

	public JFrame signUp;
	private JTextField tf_firstname, tf_lastname, tf_tel, tf_pseudo;
	private JPasswordField pf_password;
	private JLabel lb_error,lb_firstname, lb_lastname, lb_password, lb_tel, lb_pseudo;
	private JButton btn_send, btn_back;
	private ButtonGroup typeAccountGroup, typeCategoryGroup;
	private JRadioButton rbtn_category;
	DAOFactory adf = new DAOFactory();
	private JRadioButton rbtn_manager, rbtn_member, rbtn_treasurer;
	
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
		signUp.setLayout(null);
		JPanel inSignUp = new JPanel();
		inSignUp.setBounds(20,20,500,300);
		inSignUp.setLayout(null);
		signUp.add(inSignUp);
		
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
		
		JCheckBox chkb_Pass = new JCheckBox("Show password");
		chkb_Pass.setBounds(250, 120, 150, 20);
		inSignUp.add(chkb_Pass);

		chkb_Pass.addActionListener(e -> {
			if(chkb_Pass.isSelected()) {
				pf_password.setEchoChar((char)0);
			}else {
				pf_password.setEchoChar('*');
			}
		});
		
		inSignUp.add(lb_firstname);
		inSignUp.add(tf_firstname);
		inSignUp.add(lb_lastname);
		inSignUp.add(tf_lastname);
		inSignUp.add(lb_password);
		inSignUp.add(pf_password);
		inSignUp.add(lb_tel);
		inSignUp.add(tf_tel);
		inSignUp.add(tf_pseudo);
		inSignUp.add(lb_pseudo);
		
		btn_send.setBounds(500,500,70,50);
		btn_back.setBounds(400,500,70,50);
		signUp.getContentPane().add(btn_send);
		signUp.getContentPane().add(btn_back);
		
		JPanel panel_rbtn_TypeAccount = new JPanel();
		panel_rbtn_TypeAccount.setLayout(new GridLayout(3,1));
		panel_rbtn_TypeAccount.setBounds(0,200,100,100);
	
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
		
		JPanel panel_rbtn_TypeCategory = new JPanel();
		panel_rbtn_TypeCategory.setLayout(new GridLayout(4,1));
		panel_rbtn_TypeCategory.setBounds(100,200,100,100);
		typeCategoryGroup = new ButtonGroup();

		List <Category> categories = new ArrayList <Category>();
		DAO<Category> categoryDAO = adf.getCategoryDAO();

		categories = categoryDAO.findAll();
		int count = 0;
		for(Category category : categories) {
			++count;
			rbtn_category = new JRadioButton(category.getClass().getSimpleName());
			rbtn_category.setActionCommand(String.valueOf(count));
			typeCategoryGroup.add(rbtn_category);
			panel_rbtn_TypeCategory.add(rbtn_category);
		}
		/*TESTS
		 */

		for(Category category:categories) {
			System.out.println(category.getCategoryMembers());
		}
		signUp.getContentPane().add(panel_rbtn_TypeCategory);
		
		
		btn_send.addActionListener(e->{
			String firstname = tf_firstname.getText();
			String lastname = tf_lastname.getText();
			String tel = tf_tel.getText();
			String pseudo = tf_pseudo.getText();
			String password = String.valueOf(pf_password.getPassword());
			boolean checkedAccount = typeAccountGroup.getSelection() !=null;
			String account = checkedAccount? typeAccountGroup.getSelection().getActionCommand():"";
			boolean checkedCategory = typeCategoryGroup.getSelection() !=null;
			String category = checkedCategory? typeCategoryGroup.getSelection().getActionCommand():"";
			String result = formValidation(firstname,lastname,password,pseudo,tel,account,category);
			
			lb_error = new JLabel();
			lb_error.setBounds(300,250, 400, 300);
			lb_error.setForeground(Color.red);
			signUp.getContentPane().add(lb_error);
			if(result!="") {
				result="<html>"+result+"</html>";
				lb_error.setText(result);
				lb_error.show();
				System.out.println(result);
			}else {
				//___
				
				lb_error.setText(null);
				lb_error.hide();
				// Alors envoyer résultat pour traitement
				DAOFactory adf = new DAOFactory();
				DAO<Person> personDAO = adf.getPersonDAO();
				
				if(account.equals("Treasurer")) {
					Treasurer treasurer = new Treasurer(firstname,lastname,password,tel,pseudo);
					if(!treasurer.equals(null)) {
						//TODO : NO
						personDAO.create(treasurer);
						MonitorPayments next = new MonitorPayments();
						JFrame monitorPayments = next.monitorPayments;
						changeFrame(monitorPayments);
					}else {
						lb_error.setText("This treasurer already exist in member !");
					}
				}
				if(account.equals("Member")) {
					Member member = new Member(firstname,lastname,password,tel,pseudo,Integer.parseInt(category));
					// Velo mis à null, il sera modifié à la page suivante
						if(!member.equals(null)) {
							if(member.signUp()){
								AddVelo next = new AddVelo();
								JFrame addVelo = next.addVelo;
								changeFrame(addVelo);
							}
						}else {
							lb_error.setText("This member already exist !");
						}
				}
				if(account.equals("Manager")) {
					Manager manager = new Manager(firstname,lastname,password,tel,pseudo,Integer.parseInt(category));
					// TEST
					System.out.println(manager.getFirstname());
					System.out.println(manager.getPseudo());
					
					
					if(!manager.equals(null)) {
						personDAO.create(manager);
						ConsultCalendar next = new ConsultCalendar(manager);
						JFrame consultCalendar = next.consultCalendar;
						changeFrame(consultCalendar);
					}
				}
			}
		});
		btn_back.addActionListener(e-> {
			Init previous = new Init();
			JFrame home = previous.init;
			changeFrame(home);
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
	
	
	public String formValidation(String firstname, String lastname, String password, String pseudo, String tel, String typeAccount, String typeCategory) {
		String result="";
		
		if(firstname.equals("") || firstname.length() < 3) {
			result+="Invalid firstname";
			result+="<br/>";
		}
		if(lastname.equals("") || lastname.length() < 3) {
			result+="Invalid lastname";
			result+="<br/>";
		}
		if(password.equals("") || password.length() < 4 || password.length()>16) {
			result+="Invalid password. Password must be in 4 to 16 characters";
			result+="<br/>";
		}
		if(pseudo.equals("") || pseudo.length() < 3) {
			result+="Invalid pseudo";
			result+="<br/>";
		}
		if(tel.equals("") || tel.length() < 3) {
			result+="Invalid tel";
			result+="<br/>";
		}
		if(typeAccount.equals("") || typeAccount.equals(null)) {
			result+="Invalid account type";
			result+="<br/>";
		}
		if(typeCategory.equals("") || typeCategory.equals(null)) {
			result+="Invalid category type";
			result+="<br/>";
		}
		return result;
	}
}