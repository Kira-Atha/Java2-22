package be.huygebaert.POJO;

import be.huygebaert.DAO.DAO;
public class TrailRider extends VTT{
	public static DAO<TrailRider> trailriderDAO = adf.getTrailRiderDAO();
	

	public TrailRider() {
		super();
	}
	public static Category getCategory(int num) {		
		return trailriderDAO.find(num);
	}
}
