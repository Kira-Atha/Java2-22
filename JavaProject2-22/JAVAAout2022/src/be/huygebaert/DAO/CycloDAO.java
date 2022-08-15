package be.huygebaert.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import be.huygebaert.POJO.Cyclo;


public class CycloDAO extends DAO<Cyclo> {
	
	public CycloDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Cyclo obj) {
		return false;
	}

	@Override
	public boolean delete(Cyclo obj) {
		return false;
	}

	@Override
	public boolean update(Cyclo obj) {
		return false;
	}

	@Override
	public Cyclo find(int id) {	
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Cyclo cyclo = new Cyclo();
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				cyclo.setNum(id);
				cyclo.setSingleCalendar(calendarDAO.find(cyclo.getNum()));
				return cyclo;
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
	public List<Cyclo> findAll() {
		return null;
	}
}
