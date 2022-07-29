package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;


abstract public class Category implements Serializable {
	//INFOs JANVIER : PAS DE SINGLETON PAR CATEGORIE && CATEGORIES DEJA EN BDD (PAS CREATE)
	
	private static final long serialVersionUID = -309839438964916689L;
	public static DAOFactory adf = new DAOFactory();
	// Num category = num calendar 
	protected int num;
	//private Manager singleManager = null;
	//private List<Member> categoryMembers = new ArrayList<Member>();
	protected Calendar singleCalendar;
	public static List <Category> categories = null; 
	public static DAO<Category> categoryDAO = adf.getCategoryDAO();
	
	public Category() {}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num=num;
	}
	public static List <Category> getAllCategories() {
		categories = categoryDAO.findAll();
		return categories;
	}
	public static Category getCategory(int num) {
		return categoryDAO.find(num);
	}
	/*
	public List<Member> getCategoryMembers() {
		return categoryMembers;
	}
	public Manager getSingleManager() {
		return singleManager;
	}
	*/
	public Calendar getSingleCalendar() {
		return this.singleCalendar;
	}
	/*
	public void setSingleManager(Manager singleManager) {
		this.singleManager = singleManager;
	}
	*/
	public void setSingleCalendar(Calendar singleCalendar) {
		this.singleCalendar = singleCalendar;
	}
	
	/*
	public boolean addPerson(Person person) {
		if(person instanceof Member) {
			if(!categoryMembers.contains(person)) {
				// ajout de la personne dans la liste des membres de la catégorie
				Member member = (Member) person;
				categoryMembers.add(member);
			}
			// Ajout de la catégorie à la liste du membre 
			if(!((Member) person).getMemberCategories().contains(this)) {
				((Member) person).getMemberCategories().add(this);
			}
		}
		if(person instanceof Manager) {
			if(singleManager==null) {
				Manager manager = (Manager) person;
				this.singleManager = manager;
				manager.setCategory(this);
			}
		}
		return false;
	}
	*/
	

	public void deleteCategory() {
		this.singleCalendar = null;
	}
// Override equals / hashCode to use List.contains(object) how i want ( compare class name between 2 categories )
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
	      return true; // Same object in memory
	    }
	    if (!(obj instanceof Category)) {
	      return false; // Not even the same class
	    }
	    final Category other = (Category) obj;
	    return this.getClass().getSimpleName().equals(other.getClass().getSimpleName());
	  } 
	  @Override
	  public int hashCode() {
	    return this.getClass().getSimpleName().hashCode();
	  }
}