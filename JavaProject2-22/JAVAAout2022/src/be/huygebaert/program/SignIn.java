package be.huygebaert.program;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignIn {

	public JFrame signIn;
	private JTextField tf_Pseudo;
	private JPasswordField pf_Password;
	private JLabel lb_Error,lb_Pseudo,lb_Password;
	private JButton btn_Send,btn_Back;

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

	/**
	 * Create the application.
	 */
	public SignIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		signIn = new JFrame("SignIn");
		signIn.setBounds(100, 100, 800, 600);
		signIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signIn.setLocationRelativeTo(null);
		signIn.setLayout(null);
		JPanel inSignIn = new JPanel();
		inSignIn.setBounds(20,20,500,300);
		inSignIn.setLayout(null);
		signIn.add(inSignIn);
		
		tf_Pseudo = new JTextField();
		tf_Pseudo.setBounds(100,30,150,20);
		pf_Password = new JPasswordField();
		pf_Password.setBounds(100,50,150,20);
		lb_Pseudo = new JLabel();
		lb_Pseudo.setBounds(0,30,90,20);
		lb_Password = new JLabel();
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
				// Alors envoyer résultat pour traitement
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
			result+="Invalid pseudo";
			result+="<br/>";
		}
	
		return result;
	}
}
