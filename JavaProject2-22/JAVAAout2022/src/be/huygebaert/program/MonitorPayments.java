package be.huygebaert.program;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Treasurer;

public class MonitorPayments {

	public JFrame monitorPayments;
	public JPanel inMonitorPayments;
	private static Person person;
	private JLabel lb_hello;
	static JTable tablePayments;
	private JScrollPane scrollTable;
	private JButton btn_send,btn_logout; 
	private JTextField tf_newBalance;
	private List <Person>allMembers;
	private JLabel lb_allMembers;
	private JLabel lb_textSelect;
	private JLabel lb_balanceSelected;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonitorPayments window = new MonitorPayments(person);
					window.monitorPayments.setVisible(true);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		});
	}

	public MonitorPayments(Person person) {
		MonitorPayments.person = person;
		if(!Objects.isNull(person)) {
			initialize();
		}else {
			Init previous = new Init();
			JFrame home = previous.init;
			changeFrame(home);
		}
	}


	private void initialize() {
		monitorPayments = new JFrame("MonitorPayments");
		monitorPayments.setBounds(100, 100, 800, 600);
		monitorPayments.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		monitorPayments.setLocationRelativeTo(null);
		monitorPayments.getContentPane().setLayout(null);
		monitorPayments.setVisible(true);
		inMonitorPayments = new JPanel();
		inMonitorPayments.setBounds(10,10,159,44);
		inMonitorPayments.setLayout(null);
		monitorPayments.getContentPane().add(inMonitorPayments);
		
		btn_logout = new JButton();
		btn_logout.setText("logout");
		btn_logout.setBounds(685,11,89,20);
		monitorPayments.getContentPane().add(btn_logout);
		btn_logout.addActionListener(e-> {
			Init previous = new Init();
			JFrame home = previous.init;
			changeFrame(home);
		});
		
		lb_allMembers = new JLabel("All Members");
		lb_allMembers.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_allMembers.setBounds(410, 10, 195, 14);
		monitorPayments.getContentPane().add(lb_allMembers);
		
		lb_textSelect = new JLabel("Select balance and put positive value to pay someone. If you put a negative value, this amount is due from that member ");
		lb_textSelect.setBounds(0, 458, 700, 14);
		monitorPayments.getContentPane().add(lb_textSelect);
		
		lb_balanceSelected = new JLabel("");
		lb_balanceSelected.setBounds(286, 505, 60, 14);
		monitorPayments.getContentPane().add(lb_balanceSelected);
		
		String[] columns = {"Firstname","Lastname","Pseudo","Balance"};
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		tablePayments = new JTable() {;
			private static final long serialVersionUID = 1L;
			
			@Override
			public boolean isCellEditable(int row,int column) {
				return column == 4 || column == 5;
			};
		};
		tablePayments.setModel(model);
		tablePayments.getTableHeader().setReorderingAllowed(false);
		tablePayments.setBackground(Color.WHITE);
		tablePayments.setForeground(Color.BLUE);
		scrollTable = new JScrollPane(tablePayments);
		scrollTable.setBounds(300,30,400,417);
		monitorPayments.getContentPane().add(scrollTable);
		
		tablePayments.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tablePayments.getSelectedRow();
				int column = 3;
				double currentValue = Double.valueOf(tablePayments.getValueAt(row,column).toString());
				lb_balanceSelected.setText(Double.toString(currentValue));
			}
		});
		
		Object[] row = new Object[4];
		allMembers = Person.getAllPersons();
		for(Person member : allMembers) {
			if(member instanceof Member) {
				row[0] = member.getFirstname();
				row[1] = member.getLastname();
				row[2] = member.getPseudo();
				row[3] = ((Member) member).getBalance();
				model.addRow(row);
				//System.out.println(member.getFirstname()+((Member) member).getBalance());
			}
		}
		btn_send = new JButton("-");
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lb_balanceSelected.getText() != "" && Member.isNumeric(tf_newBalance.getText())) {
					if(Double.parseDouble(tf_newBalance.getText()) <=10000 && Double.parseDouble(tf_newBalance.getText()) >=-10000) {
						if(JOptionPane.showInternalConfirmDialog(null, "Are you sure ?") == 0) {
							Member member = new Member();
							member = Member.getMember(tablePayments.getSelectedRow()+1);
							if(((Treasurer) person).managePayments(member,Double.parseDouble(tf_newBalance.getText()))) {
								JOptionPane.showMessageDialog(null,"Success");
								MonitorPayments next = new MonitorPayments(person);
								JFrame monitorPayments = next.monitorPayments;
								changeFrame(monitorPayments);
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "Value <= - 10 000 or >= 10 000");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Value to subtract is not a number, select a current balance");
				}
			}
		});
		btn_send.setBounds(352,505,45,20);
		monitorPayments.getContentPane().add(btn_send);
		tf_newBalance = new JTextField();
		tf_newBalance.setBounds(407,505,60,20);
		monitorPayments.getContentPane().add(tf_newBalance);
		
		lb_hello = new JLabel("Hello "+person.getPseudo()+" a "+person.getClass().getSimpleName());
		lb_hello.setBounds(0,0,400,20);
		inMonitorPayments.add(lb_hello);
		
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		try {
			this.monitorPayments.dispose();
		}catch(Exception e){
			
		}
	}
}