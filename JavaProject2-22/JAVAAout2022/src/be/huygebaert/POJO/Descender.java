package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;

public class Descender extends VTT {
	private static DAO<Descender> descenderDAO = adf.getDescenderDAO();
	
	public Descender() {
		super();
	}

	public static Category getCategory(int num) {		
		return descenderDAO.find(num);
	}
}