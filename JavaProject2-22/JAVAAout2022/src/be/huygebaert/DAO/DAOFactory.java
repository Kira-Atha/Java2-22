package be.huygebaert.DAO;

import java.sql.Connection;

import be.huygebaert.POJO.*;

public class DAOFactory extends AbstractDAOFactory{
protected static final Connection conn = DbConnection.getInstance();
	
	public DAO<Calendar> getCalendarDAO(){
		return new CalendarDAO(conn);
	}
	public DAO<Category> getCategoryDAO(){
		return new CategoryDAO(conn);
	}
	public DAO<Cyclo> getCycloDAO(){
		return new CycloDAO(conn);
	}
	public DAO<Descender> getDescenderDAO(){
		return new DescenderDAO(conn);
	}
	public DAO<Manager> getManagerDAO(){
		return new ManagerDAO(conn);
	}
	public DAO<Member> getMemberDAO(){
		return new MemberDAO(conn);
	}
	public DAO<Outing> getOutingDAO(){
		return new OutingDAO(conn);
	}
	public DAO<Person> getPersonDAO(){
		return new PersonDAO(conn);
	}
	public DAO<Register> getRegisterDAO(){
		return new RegisterDAO(conn);
	}
	public DAO<TrailRider> getTrailRiderDAO(){
		return new TrailRiderDAO(conn);
	}
	public DAO<Treasurer> getTreasurerDAO(){
		return new TreasurerDAO(conn);
	}
	public DAO<Trialist> getTrialistDAO(){
		return new TrialistDAO(conn);
	}
	public DAO<Vehicle> getVehicleDAO(){
		return new VehicleDAO(conn);
	}
	public DAO<Velo> getVeloDAO(){
		return new VeloDAO(conn);
	}
	public DAO<VTT> getVTTDAO(){
		return new VTTDAO(conn);
	}
}