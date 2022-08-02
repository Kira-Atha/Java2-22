package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import be.huygebaert.POJO.TrailRider;

public class TrailRiderDAO extends DAO<TrailRider> {
	public TrailRiderDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(TrailRider obj) {
		return false;
	}

	@Override
	public boolean delete(TrailRider obj) {
		return false;
	}

	@Override
	public boolean update(TrailRider obj) {
		return false;
	}

	@Override
	public TrailRider find(int id) {
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar ="+id);
			if(result.first()) {
				TrailRider trailrider = new TrailRider();
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				
				trailrider.setNum(id);
				trailrider.setSingleCalendar(calendarDAO.find(trailrider.getNum()));
				return trailrider;
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<TrailRider> findAll() {

		return null;
	}
}
