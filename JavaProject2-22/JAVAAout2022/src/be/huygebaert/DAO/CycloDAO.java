package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Descender;
import be.huygebaert.POJO.Manager;

public class CycloDAO extends DAO<Cyclo> {
	
	public CycloDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Cyclo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Cyclo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Cyclo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cyclo find(int id) {	
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Cyclo cyclo = new Cyclo();
				MemberDAO memberDAO = new MemberDAO(this.connect);
				ManagerDAO managerDAO = new ManagerDAO(this.connect);
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				
				cyclo.setSingleCalendar(calendarDAO.find(cyclo.getNum()));
				cyclo.setNum(id);
				
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Cat_Memb "
						+ "ON Calendar.IdCalendar = Cat_Memb.IdCalendar "
						+ "INNER JOIN Member "
						+ "ON Cat_Memb.IdMember = Member.IdMember "
						+ "WHERE IdCalendar ="+id);
				while(result.next()) {
					System.out.println("Membre dans la catégorie => ["+id+"] "+result.getInt("IdMember"));
					cyclo.addPerson(memberDAO.find(result.getInt("IdMember")));
				}
				result = this.connect.createStatement().executeQuery(
						"SELECT * FROM Calendar INNER JOIN Manager "
						+ "ON Calendar.IdCalendar = Manager.IdCalendar "
						+ "WHERE IdCalendar ="+id);
				
				while(result.next()) {
					cyclo.addPerson(managerDAO.find(result.getInt("IdManager")));
				}
				
				return cyclo;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cyclo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
