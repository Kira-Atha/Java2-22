package be.huygebaert.POJO;

import java.util.ArrayList;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Manager extends Person {
	private static final long serialVersionUID = -1584705078113981090L;
	private Category category = null;
	DAO<Category> categoryDAO = adf.getCategoryDAO();
	DAO<Manager> managerDAO = adf.getManagerDAO();
	
	public Manager(String firstname, String lastname, String password, String tel, String pseudo, int num_category) {
		try {
			Person.idCount++;
			this.id = Person.idCount;
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
			Category category = categoryDAO.find(num_category);
			this.category = category;
		}catch(Exception e) {
			System.out.println("Manager doesn't create");
		}
	}
	public Manager(String firstname, String lastname, String password, String tel, String pseudo) {
		this.id = Person.idCount;
		this.firstname = firstname;
		this.lastname=lastname;
		this.password=password;
		this.tel=tel;
		this.pseudo=pseudo;
	}
	public Manager() {
		
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List <Manager>getAllManagers(){
		return managerDAO.findAll();
	}
	
	public void manageCalendar() {
		
	}
}