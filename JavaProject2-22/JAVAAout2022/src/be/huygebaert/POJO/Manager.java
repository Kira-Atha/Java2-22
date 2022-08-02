package be.huygebaert.POJO;

import java.util.List;
import be.huygebaert.DAO.DAO;


public class Manager extends Person {
	private static final long serialVersionUID = -1584705078113981090L;
	private Category category = null;
	private static DAO<Manager> managerDAO = adf.getManagerDAO();
	
	public Manager(String firstname, String lastname, String password, String tel, String pseudo, Category category) {
		try {
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
			this.category=category;
		}catch(Exception e) {
			System.out.println("Manager doesn't create");
		}
	}
	public Manager(String firstname, String lastname, String password, String tel, String pseudo) {
		try {
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
		}catch(Exception e) {
			System.out.println("Manager doesn't create");
		}
	}
	public Manager() {}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List <Manager>getAllManagers(){
		return managerDAO.findAll();
	}
	
	public boolean manageCalendar(int choice,Outing outing) {
		switch(choice) {
			case 0:
				if(this.getCategory().getSingleCalendar().addOuting(outing)) {
					return true;
				}
			case 1:
				if(this.getCategory().getSingleCalendar().updateOuting(outing)) {
					return true;
				}
			case 2:
				if(this.getCategory().getSingleCalendar().deleteOuting(outing)) {
					return true;
				}
		}
		return false;
	}
	
	 @Override
	 public String toString() {
		 return this.getFirstname()+" => " +this.getCategory();
	 }	 
}