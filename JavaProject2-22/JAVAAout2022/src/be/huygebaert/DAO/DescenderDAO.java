package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Descender;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.TrailRider;

public class DescenderDAO extends DAO<Descender>{
	
	public DescenderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Descender obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Descender obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Descender obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Descender find(int id) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Descender descender = new Descender();
				//MemberDAO memberDAO = new MemberDAO(this.connect);
				//ManagerDAO managerDAO = new ManagerDAO(this.connect);
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				
				descender.setSingleCalendar(calendarDAO.find(descender.getNum()));
				descender.setNum(id);
				/*
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Cat_Memb "
						+ "ON Calendar.IdCalendar = Cat_Memb.IdCalendar "
						+ "INNER JOIN Member "
						+ "ON Cat_Memb.IdMember = Member.IdMember "
						+ "WHERE IdCalendar ="+descender.getNum());
				
				while(result.next()) {
					descender.addPerson(memberDAO.find(result.getInt("IdMember")));
				}
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Manager "
						+ "ON Calendar.IdCalendar = Manager.IdCalendar "
						+ "WHERE IdCalendar ="+descender.getNum());
				
				while(result.next()) {
					// Bien qu'il n'y ait qu'un seul manager, faire result.first() pose problème.
					// .next() va tout de même s'arrêter au bout du premier et unique manager rencontré pour cette catégorie.
					descender.addPerson(managerDAO.find(result.getInt("IdManager")));
				}
				*/
				return descender;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Descender> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
