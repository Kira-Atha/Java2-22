package be.huygebaert.POJO;
import be.huygebaert.DAO.DAO;

public class Cyclo extends Category{
	private static DAO<Cyclo> cycloDAO = adf.getCycloDAO();

	public Cyclo() {
		super();
	}
	public static Category getCategory(int num) {		
		return cycloDAO.find(num);
	}

}