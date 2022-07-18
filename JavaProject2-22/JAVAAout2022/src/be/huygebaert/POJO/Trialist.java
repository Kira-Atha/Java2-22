package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Trialist extends VTT{
	private static final long serialVersionUID = -7351725857088101181L;
	public static DAO<Trialist> trialistDAO = null;
	
	public Trialist() {
		trialistDAO = adf.getTrialistDAO();
	}

	public static Category getCategory(int num) {		
		return trialistDAO.find(num);
	}
}
