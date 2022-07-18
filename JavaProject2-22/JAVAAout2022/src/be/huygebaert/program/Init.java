package be.huygebaert.program;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;
import be.huygebaert.POJO.Category;

public class Init {

	public JFrame init;
	private JButton btn_SignUp, btn_SignIn;
	
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
		
		/*TESTS
		DAOFactory adf = new DAOFactory();
		DAO<Category> categoryDAO = adf.getCategoryDAO();
		List <Category> categories = new ArrayList <Category>();
		categories = categoryDAO.findAll();
		for(Category category : categories) {
			System.out.println(category);
		}
		*/
	}

	private void initialize() {
		init = new JFrame("Home");
		init.setBounds(0, 0, 800,600);
		init.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		init.getContentPane().setLayout(null);
		init.setLocationRelativeTo(null);
		
		btn_SignUp = new JButton("Sign Up");
		btn_SignUp.setBounds(200,200,100,30);
		init.getContentPane().add(btn_SignUp);
		
		btn_SignIn = new JButton("Sign In");
		btn_SignIn.setBounds(200,230,100,30);
		init.getContentPane().add(btn_SignIn);
		
	

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