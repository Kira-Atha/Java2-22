package be.huygebaert.POJO;
import java.util.List;
import java.util.Objects;
import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;


abstract public class Category {

	protected static DAOFactory adf = new DAOFactory();
	protected int num;
	protected Calendar singleCalendar;
	protected static List <Category> categories = null; 
	protected static DAO<Category> categoryDAO = adf.getCategoryDAO();
	
	public Category() {
		this.singleCalendar = new Calendar();
	}
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
	
	// can't this = null
	public static boolean deleteCategory(Category category) {
		Calendar calendar = category.getSingleCalendar();
		calendar = null;
		category = null;
		if(Objects.isNull(category)) {
			return true;
		}
		return false;
		// Not destructor in java -> garbage collector
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