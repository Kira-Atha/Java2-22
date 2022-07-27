package be.huygebaert.DAO;

import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.huygebaert.POJO.Calendar;
import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Manager;

public class CalendarDAO extends DAO<Calendar>{
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
		Calendar calendar = null;
		ResultSet result = null;
		try {
			calendar = new Calendar();
			
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Calendar WHERE IdCalendar =" +id);
			if(result.first()) {
				calendar.setNum(result.getInt("IdCalendar"));
			}
			
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
          e.printStackTrace();
      }finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		/*
		if(calendar.getCalendarOutings().size() == 0) { 
			try {
				calendar = new Calendar();
				
				result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * from Outing WHERE IdCalendar =" +id);
				
				if(result.next()) {
					OutingDAO outingDAO = new OutingDAO(this.connect);
					calendar.getCalendarOutings().add(outingDAO.find(result.getInt("IdOuting")));
				}
				
			}catch(SQLException e) {
				 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			}catch (Exception e) {
	          e.printStackTrace();
	      }finally{
				try {
					result.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	      }
		}	
		*/
		return calendar;
	}

	@Override
	public List<Calendar> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}