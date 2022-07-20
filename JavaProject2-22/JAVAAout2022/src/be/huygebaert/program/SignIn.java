package be.huygebaert.program;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Treasurer;
import java.awt.Font;

public class SignIn {

	public JFrame signIn;
	private JTextField tf_Pseudo;
	private JPasswordField pf_Password;
	private JLabel lb_Error,lb_Pseudo,lb_Password;
	private JButton btn_Send,btn_Back;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn window = new SignIn();
					window.signIn.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SignIn() {
		initialize();
	}

	private void initialize() {
		signIn = new JFrame("SignIn");
		signIn.setBounds(100, 100, 800, 600);
		signIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signIn.setLocationRelativeTo(null);
		signIn.getContentPane().setLayout(null);
		JPanel inSignIn = new JPanel();
		inSignIn.setBounds(235,176,362,156);
		inSignIn.setLayout(null);
		signIn.getContentPane().add(inSignIn);
		
		tf_Pseudo = new JTextField();
		tf_Pseudo.setBounds(100,30,150,20);
		pf_Password = new JPasswordField();
		pf_Password.setBounds(100,50,150,20);
		lb_Pseudo = new JLabel("Pseudo");
		lb_Pseudo.setBounds(0,30,90,20);
		lb_Password = new JLabel("Password");
		lb_Password.setBounds(0,50,90,20);
		btn_Send = new JButton("Send");
		btn_Back = new JButton("Back");
		
		JCheckBox chkb_Pass = new JCheckBox("Show Password");
		chkb_Pass.setBounds(250,120,150,20);
		inSignIn.add(chkb_Pass);
		
		chkb_Pass.addActionListener(e -> {
			if(chkb_Pass.isSelected()) {
				pf_Password.setEchoChar((char)0);
			}else {
				pf_Password.setEchoChar('*');
			}
		});
	
		inSignIn.add(lb_Pseudo);
		inSignIn.add(lb_Password);
		inSignIn.add(tf_Pseudo);
		inSignIn.add(pf_Password);
		
		btn_Send.setBounds(500,500,70,50);
		btn_Back.setBounds(400,500,70,50);
		signIn.getContentPane().add(btn_Send);
		signIn.getContentPane().add(btn_Back);
		
		lblNewLabel = new JLabel("Log in");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(364, 106, 70, 25);
		signIn.getContentPane().add(lblNewLabel);
		
		btn_Send.addActionListener(e->{
			String pseudo = tf_Pseudo.getText();
			String password = String.valueOf(pf_Password.getPassword());
			String result = formValidation(pseudo,password);
			
			lb_Error = new JLabel();
			lb_Error.setBounds(300,250, 400, 300);
			lb_Error.setForeground(Color.red);
			lb_Error.hide();
			signIn.getContentPane().add(lb_Error);
			if(result!="") {
				result="<html>"+result+"</html>";
				lb_Error.setText("");
				lb_Error.setText(result);
				lb_Error.show();
			}else {
				lb_Error.setText("");
				lb_Error.hide();
				Person person = new Member(pseudo,password);
				
				if(!Objects.isNull(person)) {
					person = person.signIn();
					if(!Objects.isNull(person)) {
						if(person instanceof Manager){
							ConsultCalendar next = new ConsultCalendar(person);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);
						}
						if(person instanceof Member){
							ConsultCalendar next = new ConsultCalendar(person);
							JFrame consultCalendar = next.consultCalendar;
							changeFrame(consultCalendar);				
						}	
						if(person instanceof Treasurer){
							MonitorPayments next = new MonitorPayments(person);
							JFrame monitorPayments = next.monitorPayments;
							changeFrame(monitorPayments);
						}
					}else {
						JOptionPane.showMessageDialog(null,"This pseudo or this password is incorrect.");
					}
				}
				
			}
				
		});
		btn_Back.addActionListener(e-> {
			Init previous = new Init();
			JFrame previousFrame = previous.init;
			changeFrame(previousFrame);
		});
	}
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		signIn.dispose();
	}
	public String formValidation(String pseudo,String password) {
		String result="";
		
		if(password.equals("") || password.length() < 4 || password.length()>16) {
			result+="Invalid password. Password must be in 4 to 16 characters";
			result+="<br/>";
		}
		if(pseudo.equals("") || pseudo.length() < 3) {
			result+="Invalid pseudo. Pseudo must be > 3 characters";
			result+="<br/>";
		}
	
		return result;
	}
}
