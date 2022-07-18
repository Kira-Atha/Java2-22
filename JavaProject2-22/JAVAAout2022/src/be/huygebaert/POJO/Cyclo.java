package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Cyclo extends Category{
	private static final long serialVersionUID = 7414304179685022563L;
	public static DAO<Cyclo> cycloDAO = null;
	
	public Cyclo() {
		cycloDAO = adf.getCycloDAO();
	}

	public static Category getCategory(int num) {		
		return cycloDAO.find(num);
	}
}