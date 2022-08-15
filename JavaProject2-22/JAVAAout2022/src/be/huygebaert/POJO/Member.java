package be.huygebaert.POJO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import be.huygebaert.DAO.DAO;


public class Member extends Person{
	private double balance;
	private List<Category> memberCategories;
	private List <Velo> memberVelos;
	private Vehicle memberVehicle;
	private static DAO<Velo>veloDAO = adf.getVeloDAO();
	private static DAO<Vehicle>vehicleDAO = adf.getVehicleDAO();
	private static DAO<Member> memberDAO = adf.getMemberDAO();
	
	public Member() {
		this.memberVelos = new ArrayList<Velo>();
		this.memberCategories = new ArrayList<Category>();
		
	}

	public Member(String firstname, String lastname, String password, String tel, String pseudo) {
		super(firstname,lastname,password,tel,pseudo);
		this.memberCategories = new ArrayList<Category>();
		this.memberVelos = new ArrayList<Velo>();
	}

	public Member(String firstname, String lastname, String password, String tel, String pseudo, Category category,String type,double lenght,double weight) {
		super(firstname,lastname,password,tel,pseudo);
		
		this.balance=0;
		this.memberVehicle = null;
		this.memberCategories = new ArrayList<Category>();
		this.memberCategories.add(category);
		this.memberVelos = new ArrayList<Velo>();
		this.memberVelos.add(new Velo(weight,type,lenght,this));
	}
	// Créer un objet bidon pour tester la connexion
	public Member(String pseudo,String password) {
		this.pseudo = pseudo;
		this.password = password;
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
		return memberDAO.findAll();
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
	public boolean alreadyRegisterInOuting(Register register,Outing outingExist) {
		if(!Objects.isNull(register)) {
			for(Register reg : outingExist.getOutingRegisters()) {
				if(reg.getMember().equals(register.getMember()) ) {
					return true;
				}
			}
		}else {
			for(Register reg : outingExist.getOutingRegisters()) {
				if(reg.getMember().equals(this)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<Object> alreadyRegisterToday(Outing outingExist) {
		List<Object> boolCategory = new ArrayList<Object>();
		List<Outing> allOutings = Outing.getAllOutings();
		
		for(Outing out : allOutings) {
			for(Register reg : out.getOutingRegisters()) {
				if(reg.getMember().equals(this) && out.getStartDate().compareTo(outingExist.getStartDate()) == 0 && !out.getOutingCalendar().equals(this.getMemberCategories().get(0).getSingleCalendar()) ) {
					boolCategory.add((Boolean)true);
					boolCategory.add((Category)Category.getCategory(out.getOutingCalendar().getNum()));
				}
			}
		}
		boolCategory.add((Boolean)false);
		boolCategory.add(null);
		return boolCategory;
	}
	public List<Object> alreadyDriveToday(Outing outingExist) {
		List<Object> boolCategory = new ArrayList<Object>();
		List <Outing> allOutings = Outing.getAllOutings();
		
		for(Outing out : allOutings ) {
			for(Vehicle veh : out.getOutingVehicles()) {
				if(veh.equals(this.getMemberVehicle()) && out.getStartDate().compareTo(outingExist.getStartDate()) == 0 && !out.getOutingCalendar().equals(this.getMemberCategories().get(0).getSingleCalendar()) ) {
					boolCategory.add((Boolean)true);
					boolCategory.add((Category)Category.getCategory(out.getOutingCalendar().getNum()));
					return boolCategory;
				}
			}
		}
		boolCategory.add((Boolean)false);
		boolCategory.add(null);
		return boolCategory;
	}
	
	public boolean alreadyDriveInOuting(Outing outingExist) {
		if(!Objects.isNull(outingExist.getOutingVehicles())) {
			for(Vehicle vehicle : outingExist.getOutingVehicles()) {
				if(vehicle.equals(this.getMemberVehicle())) {
					return true;
				}
			}
		}
		return false;
	}
}
