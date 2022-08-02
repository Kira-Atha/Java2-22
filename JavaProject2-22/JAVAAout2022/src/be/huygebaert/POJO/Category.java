package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;


abstract public class Category implements Serializable {

	private static final long serialVersionUID = -309839438964916689L;
	protected static DAOFactory adf = new DAOFactory();
	protected int num;
	protected Calendar singleCalendar;
	protected static List <Category> categories = null; 
	protected static DAO<Category> categoryDAO = adf.getCategoryDAO();
	
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
	public Calendar getSingleCalendar() {
		return this.singleCalendar;
	}
	
	public void setSingleCalendar(Calendar singleCalendar) {
		this.singleCalendar = singleCalendar;
	}
	
	public void deleteCategory() {
		//TODO if i have time enough
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