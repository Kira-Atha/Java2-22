package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class TrailRider extends VTT{
	private static final long serialVersionUID = 5834673873715943205L;
	public static DAO<TrailRider> trailriderDAO = null;
	
	public TrailRider() {
		trailriderDAO = adf.getTrailRiderDAO();
	}

	public static Category getCategory(int num) {		
		return trailriderDAO.find(num);
	}
}
