package be.huygebaert.program;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;

public class MonitorPayments {

	public JFrame monitorPayments;
	public JPanel inMonitorPayments;
	private static Person person;
	private JLabel lb_hello;
	static JTable tablePayments;
	private JScrollPane scrollTable;
	private JButton btn_send; 
	private List <Person>allMembers;
	
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

	public MonitorPayments() {
		initialize();
	}
	
	public MonitorPayments(Person person) {
		this();
		MonitorPayments.person = person;
		lb_hello = new JLabel("Hello "+person.getPseudo()+" a "+person.getClass().getSimpleName());
		lb_hello.setBounds(0,0,283,50);
		inMonitorPayments.add(lb_hello);
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
		
		String[] columns = {"Firstname","Lastname","Pseudo","Balance","Action",""};
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
		scrollTable.setBounds(300,30,400,300);
		monitorPayments.getContentPane().add(scrollTable);
		
		Object[] row = new Object[6];
		allMembers = Person.getAllPersons();
		for(Person member : allMembers) {
			if(member instanceof Member) {
				row[0] = member.getFirstname();
				row[1] = member.getLastname();
				row[2] = member.getPseudo();
				row[3] = ((Member) member).getBalance();

				btn_send = new JButton("calculate");
				tablePayments.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
				tablePayments.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JTextField()));
				row[5] = btn_send ;
				model.addRow(row);
				System.out.println(member.getFirstname()+((Member) member).getBalance());
			}
		}
	}
}

/*
 * Permettre l'ajout de bouton dans les cellules, posssible d'y mettre la value d'un label de cette manière
 */
class ButtonRenderer extends JButton implements TableCellRenderer{
	private static final long serialVersionUID = 1900236278481356389L;
	public ButtonRenderer() {
		setOpaque(true);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		setText((value==null)? "" : "send");
		
		return this;
	}
}

class ButtonEditor extends DefaultCellEditor{
	private static final long serialVersionUID = 5674226962605510346L;
	protected JButton btn;
	private String label;
	private boolean clicked;
	
	
	public ButtonEditor(JTextField txt){
		super(txt);
		this.btn = new JButton();
		btn.setOpaque(true);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}
	@Override
	public Component getTableCellEditorComponent(JTable table, Object obj, boolean selected, int row, int column) {
		label =(obj == null)? "":"send";
		btn.setText(label);
		clicked = true;
		
		return btn;
	}
	@Override
	public Object getCellEditorValue() {
		if(clicked) {
			JTable table = MonitorPayments.tablePayments;
			System.out.println(table.getSelectedRow()+1);
			Member member = new Member();
			//TODO : Quand base de données OK,changer valeur ( démarrait à 2 )
			member = member.getMember(table.getSelectedRow()+2);
			System.out.println(member.getFirstname());
			//TODO : Récupérer la valeur pour le calcul
			int valueCalc = 0;
			double calculate = member.getBalance() + valueCalc;
			member.setBalance(calculate);
			member.updateBalance(member);
			table.revalidate();
		}
		return new String(label);
	}
	
	@Override
	public boolean stopCellEditing() {
		clicked = false;
		return super.stopCellEditing();
	}
	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}