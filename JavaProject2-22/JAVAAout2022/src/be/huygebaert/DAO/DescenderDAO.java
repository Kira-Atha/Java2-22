package be.huygebaert.DAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import be.huygebaert.POJO.Descender;


public class DescenderDAO extends DAO<Descender>{
	
	public DescenderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Descender obj) {
		return false;
	}

	@Override
	public boolean delete(Descender obj) {
		return false;
	}

	@Override
	public boolean update(Descender obj) {
		return false;
	}

	@Override
	public Descender find(int id) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				Descender descender = new Descender();
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				descender.setNum(id);
				descender.setSingleCalendar(calendarDAO.find(descender.getNum()));
				return descender;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Descender> findAll() {
		return null;
	}
}
