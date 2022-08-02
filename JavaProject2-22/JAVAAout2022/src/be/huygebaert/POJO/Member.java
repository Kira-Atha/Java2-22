package be.huygebaert.POJO;

import java.util.ArrayList;
import java.util.List;
import be.huygebaert.DAO.DAO;


public class Member extends Person{
	private static final long serialVersionUID = 5312718583319369493L;

	private double balance;
	private List<Category> memberCategories;
	private List <Velo> memberVelos;
	private Vehicle memberVehicle;
	protected static DAO<Velo>veloDAO = adf.getVeloDAO();
	protected static DAO<Vehicle>vehicleDAO = adf.getVehicleDAO();
	
	public Member() {}
	public Member(String pseudo,String password) {
		this.pseudo = pseudo;
		this.password = password;
	}
	public Member(String firstname, String lastname, String password, String tel, String pseudo, Category category,String type,double lenght,double weight) {
		try {
			this.id = 0;
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
			this.balance=0;
			this.memberVehicle = null;
			memberCategories = new ArrayList<Category>();
			this.memberCategories.add(category);
			memberVelos = new ArrayList<Velo>();
			this.memberVelos.add(new Velo(weight,type,lenght,this));

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Member(String firstname, String lastname, String password, String tel, String pseudo) {
		this.firstname = firstname;
		this.lastname=lastname;
		this.password=password;
		this.tel=tel;
		this.pseudo=pseudo;
		memberCategories = new ArrayList<Category>();
		memberVelos = new ArrayList<Velo>();
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<Category> getMemberCategories() {
		return memberCategories;
	}
	public void setMemberCategories(List<Category> memberCategories) {
		this.memberCategories = memberCategories;
	}
	public static List<Member> getAllMembers(){
		DAO<Member> memberDAO = adf.getMemberDAO();
		return memberDAO.findAll();
	}
	
	public static List<Member> getMemberRegister(Register register){
		List<Member> memberRegisters = null;
		
		return memberRegisters;
	}

	public List <Velo> getMemberVelos() {
		return memberVelos;
	}

	public void setMemberVelos(List <Velo> memberVelos) {
		this.memberVelos = memberVelos;
	}

	public Vehicle getMemberVehicle() {
		return memberVehicle;
	}

	public void setMemberVehicle(Vehicle memberVehicle) {
		this.memberVehicle = memberVehicle;
	}
	@Override
	public String toString() {
		return this.getFirstname()+" "+this.getLastname();
	}

	public boolean updateBalance(double amount) {
		double newBalance;
	// If balance is negative => pending refund
		newBalance = this.getBalance()-amount;
		this.setBalance(newBalance);
		if(memberDAO.update(this)) {
			return true;
		}
		return false;
	}
	public boolean createVelo(Velo velo) {
		// Ne pas rajouter une deuxième fois le vélo créé à l'inscription
		if(this.getMemberVelos().size() > 1) {
			this.memberVelos.add(velo);
		}
		if(veloDAO.create(velo)) {
			return true;
		}
		return false;
	}
	public boolean createVehicle(Vehicle vehicle) {
		this.memberVehicle=vehicle;
		if(vehicleDAO.create(vehicle)) {
			return true;
		}
		return false;
	}
	public boolean joinCategory(Category category) {
		// If member just create -> list.get(0) = first category ( constructor ), don't need to add
		if(this.getMemberCategories().get(0).getClass() != category.getClass()) {
			System.out.println("It's not first category choosen, add !");
			this.getMemberCategories().add(category);
		}
		if(memberDAO.create(this)){
			return true;
		}
		return false;
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
