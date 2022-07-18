package be.huygebaert.DAO;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.huygebaert.POJO.Calendar;
import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Manager;

public class CalendarDAO extends DAO<Calendar>{
// Num de category = num calendar. Un calendrier par catégorie. Et même table. (liens 1-1) Est-ce nécessaire d'implémenter cette DAO dans notre cas ???
	// J'avais juste besoin de récupérer un objet calendrier pour set le calendrier de la catégorie
	public CalendarDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Calendar obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Calendar obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Calendar obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Calendar find(int id) {
		Calendar calendar = new Calendar();
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar =" +id);
			if(result.first()) {
				calendar.setNum(result.getInt("IdCalendar"));
		
				CategoryDAO categoryDAO = new CategoryDAO(this.connect);
				calendar.setCalendarCategory(categoryDAO.find(id));
				
				//OutingDAO outingDAO = new OutingDAO(this.connect);
				//calendar.addOuting(outingDAO.find(result.getInt("IdOuting");
			}
			
			return calendar;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Calendar> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}