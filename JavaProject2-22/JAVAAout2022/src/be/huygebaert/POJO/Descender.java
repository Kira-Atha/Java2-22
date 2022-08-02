package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;

public class Descender extends VTT {
	private static final long serialVersionUID = -2213836681647297285L;
	private static DAO<Descender> descenderDAO = null;
	
	public Descender() {
		descenderDAO = adf.getDescenderDAO();
	}

	public static Category getCategory(int num) {		
		return descenderDAO.find(num);
	}
}