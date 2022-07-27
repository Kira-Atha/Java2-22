package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Calendar;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Outing;

public class OutingDAO extends DAO<Outing> {
	public OutingDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Outing outing) {
		try(PreparedStatement ps0 = this.connect.prepareStatement("INSERT INTO Outing VALUES (?,?,?,?,?,?,?,?,?)")) {
		    ps0.setInt(1, 0);
	        ps0.setString(2, outing.getStartPoint());
	        ps0.setDate(3,new java.sql.Date(outing.getStartDate().getTime()));
	        ps0.setDouble(4, outing.getForfeit());
	        ps0.setInt(5, outing.getMaxMemberSeats());
	        ps0.setInt(6, outing.getMaxVeloSeats());
	        ps0.setInt(7, outing.getMaxMemberSeats());
	        ps0.setInt(8, outing.getMaxMemberSeats());
	        System.out.println("DAO CALENDAR NUM : "+outing.getOutingCalendar().getNum());
	        ps0.setInt(9,outing.getOutingCalendar().getNum());
	        ps0.executeUpdate();
	        return true;
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
			 return false;
		}catch (Exception e) {
           e.printStackTrace();
           return false;
       }
	}

	@Override
	public boolean delete(Outing outing) {
		try {
			PreparedStatement ps = this.connect.prepareStatement("DELETE * FROM OUTING WHERE IdOuting =");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
            e.printStackTrace();
        }
		try {
			PreparedStatement ps = this.connect.prepareStatement("DELETE * FROM Out_Vehicle WHERE IdOuting =");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
           e.printStackTrace();
       }
		return false;
	}

	@Override
	public boolean update(Outing outing) {
		String sql = "UPDATE OUTING set StartPoint,DateStart,Forfeit,MaxMemberSeats,MaxVeloSeats,NeedMemberSeats,RemainingMemberSeats = ?,?,?,?,?,?,? WHERE IdOuting = ?";
		try {
			PreparedStatement statement = this.connect.prepareStatement(sql);
			statement.setString(1,outing.getStartPoint());
			statement.setDate(2,new java.sql.Date(outing.getStartDate().getTime()));
			statement.setDouble(3,outing.getForfeit());
			statement.setInt(4,outing.getMaxMemberSeats());
			statement.setInt(5,outing.getMaxVeloSeats());
			statement.setInt(6,outing.getNeedMemberSeats());
			statement.setInt(7,outing.getRemainingMemberSeats());
			statement.setInt(8,outing.getNum());
			
			statement.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Outing find(int id) {
		Outing outing = null;
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Outing WHERE IdOuting = " +id);
			if(result.first()){
				// Compléter avec info de base
				
				outing = new Outing();
				outing.setNum(result.getInt("IdOuting"));
				outing.setStartPoint(result.getString("StartPoint"));
				outing.setStartDate(result.getDate("DateStart"));
				outing.setForfeit(result.getDouble("Forfeit"));
				outing.setMaxMemberSeats(result.getInt("MaxMemberSeats"));
				outing.setMaxVeloSeats(result.getInt("MaxVeloSeats"));
				outing.setNeedMemberSeats(result.getInt("NeedMemberSeats"));
				outing.setRemainingMemberSeats(result.getInt("RemainingMemberSeats"));
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				outing.setOutingCalendar(calendarDAO.find(result.getInt("IdCalendar")));
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
		return outing;
	}

	@Override
	public List<Outing> findAll() {
		Outing outing = null;
		ResultSet result = null;
		List<Outing> allOutings = new ArrayList<Outing>();
		
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Outing");
			while(result.next()){
				// Compléter avec info de base
				
				outing = new Outing();
				outing.setNum(result.getInt("IdOuting"));
				outing.setStartPoint(result.getString("StartPoint"));
				outing.setStartDate(result.getDate("DateStart"));
				outing.setForfeit(result.getDouble("Forfeit"));
				outing.setMaxMemberSeats(result.getInt("MaxMemberSeats"));
				outing.setMaxVeloSeats(result.getInt("MaxVeloSeats"));
				outing.setNeedMemberSeats(result.getInt("NeedMemberSeats"));
				outing.setRemainingMemberSeats(result.getInt("RemainingMemberSeats"));
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				outing.setOutingCalendar(calendarDAO.find(result.getInt("IdCalendar")));
				allOutings.add(outing);
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
		return allOutings;
	}
}
