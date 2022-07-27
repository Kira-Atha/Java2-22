package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.TrailRider;
import be.huygebaert.POJO.Trialist;

public class TrialistDAO extends DAO<Trialist>{
	public TrialistDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Trialist obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Trialist obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Trialist obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Trialist find(int id) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Trialist trialist = new Trialist();
				//MemberDAO memberDAO = new MemberDAO(this.connect);
				//ManagerDAO managerDAO = new ManagerDAO(this.connect);
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				trialist.setNum(id);
				trialist.setSingleCalendar(calendarDAO.find(trialist.getNum()));
				/*
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Cat_Memb "
						+ "ON Calendar.IdCalendar = Cat_Memb.IdCalendar "
						+ "INNER JOIN Member "
						+ "ON Cat_Memb.IdMember = Member.IdMember "
						+ "WHERE IdCalendar ="+trialist.getNum());
				
				while(result.next()) {
					trialist.addPerson(memberDAO.find(result.getInt("IdMember")));
				}
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Manager "
						+ "ON Calendar.IdCalendar = Manager.IdCalendar "
						+ "WHERE IdCalendar ="+trialist.getNum());
				
				while(result.next()) {
					// Bien qu'il n'y ait qu'un seul manager, faire result.first() pose problème.
					// .next() va tout de même s'arrêter au bout du premier et unique manager rencontré pour cette catégorie.
					trialist.addPerson(managerDAO.find(result.getInt("IdManager")));
				}
				*/
				return trialist;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Trialist> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
