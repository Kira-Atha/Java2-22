package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.TrailRider;

public class TrailRiderDAO extends DAO<TrailRider> {
	public TrailRiderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(TrailRider obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(TrailRider obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TrailRider obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TrailRider find(int id) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				TrailRider trailrider = new TrailRider();
				//MemberDAO memberDAO = new MemberDAO(this.connect);
				//ManagerDAO managerDAO = new ManagerDAO(this.connect);
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				
				trailrider.setSingleCalendar(calendarDAO.find(trailrider.getNum()));
				trailrider.setNum(id);
				/*
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Cat_Memb "
						+ "ON Calendar.IdCalendar = Cat_Memb.IdCalendar "
						+ "INNER JOIN Member "
						+ "ON Cat_Memb.IdMember = Member.IdMember "
						+ "WHERE IdCalendar ="+trailrider.getNum());
				
				while(result.next()) {
					trailrider.addPerson(memberDAO.find(result.getInt("IdMember")));
				}
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Manager "
						+ "ON Calendar.IdCalendar = Manager.IdCalendar "
						+ "WHERE IdCalendar ="+trailrider.getNum());
				
				while(result.next()) {
					// Bien qu'il n'y ait qu'un seul manager, faire result.first() pose problème.
					// .next() va tout de même s'arrêter au bout du premier et unique manager rencontré pour cette catégorie.
					trailrider.addPerson(managerDAO.find(result.getInt("IdManager")));
				}
				*/
				return trailrider;
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TrailRider> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
