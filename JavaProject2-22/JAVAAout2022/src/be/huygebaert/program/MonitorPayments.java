package be.huygebaert.program;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Treasurer;

import java.awt.event.FocusAdapter;

public class MonitorPayments {

	public JFrame monitorPayments;
	public JPanel inMonitorPayments;
	private static Person person;
	private JLabel lb_hello;
	static JTable tablePayments;
	private JScrollPane scrollTable;
	private JButton btn_send; 
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
					e.printStackTrace();
				}
			}
		});
	}

	public MonitorPayments(Person person) {
		MonitorPayments.person = person;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//MonitorPayments.person = person;
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
		
		lb_allMembers = new JLabel("All Members");
		lb_allMembers.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lb_allMembers.setBounds(410, 10, 195, 14);
		monitorPayments.getContentPane().add(lb_allMembers);
		
		lb_textSelect = new JLabel("Select balance and put amount to subtract");
		lb_textSelect.setBounds(331, 458, 346, 14);
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
				//System.out.println("- test");
				int row = tablePayments.getSelectedRow();
				int column = 3;
				double currentValue = Double.valueOf(tablePayments.getValueAt(row,column).toString());
				//tf_newBalance.setText(Double.toString(currentValue));
				lb_balanceSelected.setText(Double.toString(currentValue));
				//System.out.println(currentValue);
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
				if(lb_balanceSelected.getText() != "" && MonitorPayments.isNumeric(tf_newBalance.getText())) {
					if(JOptionPane.showInternalConfirmDialog(null, "Are you sure ?") == 0) {
						Member member = new Member();
						//TODO : changer value car en DB démarre à 2
						member = member.getMember(tablePayments.getSelectedRow()+2);
						
						if(((Treasurer) person).managePayments(member,Double.parseDouble(tf_newBalance.getText()))) {
							JOptionPane.showMessageDialog(null,"Success");
							//TODO : trouver un meilleur refresh 
							MonitorPayments next = new MonitorPayments(person);
							JFrame monitorPayments = next.monitorPayments;
							changeFrame(monitorPayments);
						}else {
							JOptionPane.showMessageDialog(null, "Balance of member si < the amount to subtract");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Value to subtract is not a number and select a current balance");
				}
			}
		});
		btn_send.setBounds(352,505,45,20);
		monitorPayments.getContentPane().add(btn_send);
		tf_newBalance = new JTextField();
		tf_newBalance.setBounds(407,505,60,20);
		monitorPayments.getContentPane().add(tf_newBalance);
		
		lb_hello = new JLabel("Hello "+person.getPseudo()+" a "+person.getClass().getSimpleName());
		lb_hello.setBounds(0,0,283,50);
		inMonitorPayments.add(lb_hello);
		
	}
	
	public void changeFrame(JFrame window) {
		window.setVisible(true);
		this.monitorPayments.dispose();
	}
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}