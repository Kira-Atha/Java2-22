package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import be.huygebaert.POJO.Trialist;

public class TrialistDAO extends DAO<Trialist>{
	public TrialistDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Trialist obj) {
		return false;
	}

	@Override
	public boolean delete(Trialist obj) {
		return false;
	}

	@Override
	public boolean update(Trialist obj) {
		return false;
	}

	@Override
	public Trialist find(int id) {
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Trialist trialist = new Trialist();
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				trialist.setNum(id);
				trialist.setSingleCalendar(calendarDAO.find(trialist.getNum()));
				return trialist;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List<Trialist> findAll() {
		return null;
	}
}
