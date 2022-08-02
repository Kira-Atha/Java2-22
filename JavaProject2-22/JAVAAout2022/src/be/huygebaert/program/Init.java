package be.huygebaert.program;

import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;

public class Init {

	public JFrame init;
	private JButton btn_SignUp, btn_SignIn;
	private JLabel lb_welcome;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Init window = new Init();
					window.init.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Init() {
		initialize();
	}

	private void initialize() {
		init = new JFrame("Home");
		init.setBounds(0, 0, 800,600);
		init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init.getContentPane().setLayout(null);
		init.setLocationRelativeTo(null);
		
		btn_SignUp = new JButton("Sign up");
		btn_SignUp.setBounds(330,207,100,30);
		init.getContentPane().add(btn_SignUp);
		
		btn_SignIn = new JButton("Sign in");
		btn_SignIn.setBounds(330,237,100,30);
		init.getContentPane().add(btn_SignIn);
		
		lb_welcome = new JLabel("Welcome to the Vansset cyclis'ts club");
		lb_welcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_welcome.setBounds(213, 68, 341, 54);
		init.getContentPane().add(lb_welcome);
		
	

		btn_SignIn.addActionListener(e-> {
			SignIn next = new SignIn();
			JFrame nextFrame = next.signIn;
			changeFrame(nextFrame);
		});

		btn_SignUp.addActionListener(e-> {
			SignUp next = new SignUp();
			JFrame nextFrame = next.signUp;
			changeFrame(nextFrame);
		});
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		init.dispose();
	}

}