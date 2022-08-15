package be.huygebaert.POJO;

import java.util.List;
import be.huygebaert.DAO.DAO;

public class Manager extends Person {
	private Category category;
	private static DAO<Manager> managerDAO = adf.getManagerDAO();
	
	public Manager(String firstname, String lastname, String password, String tel, String pseudo) {
		super(firstname,lastname,password,tel,pseudo);
	}
	public Manager(String firstname, String lastname, String password, String tel, String pseudo,Category category) {
		super(firstname,lastname,password,tel,pseudo);
		this.category = category;
	}
	public Manager() {}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public static List <Manager>getAllManagers(){
		return managerDAO.findAll();
	}
	
	public boolean manageCalendar(int choice,Outing outing) {
		switch(choice) {
			case 0:
				if(this.getCategory().getSingleCalendar().addOuting(outing)) {
					return true;
				}
				break;
			case 1:
				if(this.getCategory().getSingleCalendar().updateOuting(outing)) {
					return true;
				}
				break;
			case 2:
				if(this.getCategory().getSingleCalendar().deleteOuting(outing)) {
					return true;
				}
				break;
		}
		return false;
	}
	
	 @Override
	 public String toString() {
		 return this.getFirstname()+" => " +this.getCategory();
	 }	 
}