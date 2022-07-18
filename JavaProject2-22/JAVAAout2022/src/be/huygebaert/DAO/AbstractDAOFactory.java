package be.huygebaert.DAO;

import be.huygebaert.POJO.*;

public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;
	public static final int XML_DAO_FACTORY = 1;
	
	public abstract DAO<Calendar> getCalendarDAO();
	public abstract DAO<Category> getCategoryDAO();
	public abstract DAO<Cyclo> getCycloDAO();
	public abstract DAO<Descender> getDescenderDAO();
	public abstract DAO<Manager> getManagerDAO();
	public abstract DAO<Member> getMemberDAO();
	public abstract DAO<Outing> getOutingDAO();
	public abstract DAO<Person> getPersonDAO();
	public abstract DAO<Register> getRegisterDAO();
	public abstract DAO<TrailRider> getTrailRiderDAO();
	public abstract DAO<Treasurer> getTreasurerDAO();
	public abstract DAO<Trialist> getTrialistDAO();
	public abstract DAO<Vehicle> getVehicleDAO();
	public abstract DAO<Velo> getVeloDAO();
	public abstract DAO<VTT> getVTTDAO();
	
	public static AbstractDAOFactory getFactory(int type){
		switch(type){
		case DAO_FACTORY:
			return new DAOFactory();
			default:
				return null;
		}
	}
}