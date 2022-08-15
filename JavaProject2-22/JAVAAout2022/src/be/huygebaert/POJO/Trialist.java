package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;

public class Trialist extends VTT{
	private static DAO<Trialist> trialistDAO = adf.getTrialistDAO();
	
	public Trialist() {
		super();
	}

	public static Category getCategory(int num) {		
		return trialistDAO.find(num);
	}
}
