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
import be.huygebaert.POJO.Vehicle;

public class OutingDAO extends DAO<Outing> {
	public OutingDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Outing outing) {
		try(PreparedStatement ps0 = this.connect.prepareStatement("INSERT INTO Outing VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {
		    ps0.setInt(1, 0);
	        ps0.setString(2, outing.getStartPoint());
	        ps0.setDate(3,new java.sql.Date(outing.getStartDate().getTime()));
	        ps0.setDouble(4, outing.getForfeit());
	        ps0.setInt(5, outing.getMaxMemberSeats());
	        ps0.setInt(6, outing.getMaxVeloSeats());
	        ps0.setInt(7, outing.getNeedMemberSeats());
	        ps0.setInt(8, outing.getNeedVeloSeats());
	        System.out.println("DAO CALENDAR NUM : "+outing.getOutingCalendar().getNum());
	        ps0.setInt(9, outing.getRemainingMemberSeats());
	        ps0.setInt(10, outing.getRemainingVeloSeats());
	        ps0.setInt(11,outing.getOutingCalendar().getNum());
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
			PreparedStatement ps = this.connect.prepareStatement("DELETE * FROM OUTING WHERE IdOuting = ?");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
            e.printStackTrace();
        }
		try {
			PreparedStatement ps = this.connect.prepareStatement("DELETE * FROM Out_Vehicle WHERE IdOuting = ?");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
           e.printStackTrace();
		}
		try {
			PreparedStatement ps = this.connect.prepareStatement("DELETE * FROM Register WHERE IdOuting = ?");
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
	// Manager update outing
		String sql_update = "UPDATE OUTING set StartPoint = ?,DateStart = ?,Forfeit=?,MaxMemberSeats=?,MaxVeloSeats=?,NeedMemberSeats=?,NeedVeloSeats=?,RemainingMemberSeats = ?,RemainingVeloSeats = ? WHERE IdOuting = ?";
		PreparedStatement statement;
		try {
			Outing outingToCompare = find(outing.getNum());

			if(outingToCompare.getOutingVehicles().size()!=outing.getOutingVehicles().size()) {
				//this.connect.setAutoCommit(false);
				
				System.out.println("AJOUT DE VEHICULE");
				statement = this.connect.prepareStatement("INSERT INTO Out_Vehicle VALUES(?,?)");
			// Get the id of the last vehicle add on outing
				int numOfLastVehicle = outing.getOutingVehicles().get(outing.getOutingVehicles().size()-1).getNum();
				Vehicle lastVehicle = Vehicle.getVehicle(numOfLastVehicle);
				//System.out.println(numOfLastVehicle);
				statement.setInt(1, numOfLastVehicle);
				statement.setInt(2, outing.getNum());
				if(statement.executeUpdate()>0) {
					statement.close();
					statement = this.connect.prepareStatement(sql_update);
					statement.setString(1,outing.getStartPoint());
					statement.setDate(2,new java.sql.Date(outing.getStartDate().getTime()));
					statement.setDouble(3,outing.getForfeit());
					statement.setInt(4,outing.getMaxMemberSeats());
					statement.setInt(5,outing.getMaxVeloSeats());
					statement.setInt(6,outing.getNeedMemberSeats());
					statement.setInt(7,outing.getNeedVeloSeats());
					statement.setInt(8, outing.getRemainingMemberSeats());
					statement.setInt(9, outing.getRemainingVeloSeats());
					statement.setInt(10,outing.getNum());
					System.out.println(outing.getNum());
					statement.executeUpdate();
				// Formule à modifier, je suis parti de l'idée que le driver est payé en fonction du nombre de personne qu'il peut prendre ( tant pis si toutes les places ne sont pas
				// occupées, il s'est tout de même déplacé
					lastVehicle.getDriver().updateBalance(outing.getForfeit()*lastVehicle.getTotalMemberSeats());
					return true;
				}
			}else {
				System.out.println("UPDATE MANAGER");
				statement = this.connect.prepareStatement(sql_update);
				statement.setString(1,outing.getStartPoint());
				statement.setDate(2,new java.sql.Date(outing.getStartDate().getTime()));
				statement.setDouble(3,outing.getForfeit());
				statement.setInt(4,outing.getMaxMemberSeats());
				statement.setInt(5,outing.getMaxVeloSeats());
				statement.setInt(6,outing.getNeedMemberSeats());
				statement.setInt(7,outing.getNeedVeloSeats());
				statement.setInt(8, outing.getRemainingMemberSeats());
				statement.setInt(9, outing.getRemainingVeloSeats());
				statement.setInt(10,outing.getNum());
				statement.executeUpdate();
				
				System.out.println(outing.getStartPoint());
				System.out.println(outing.getStartDate());
				statement.closeOnCompletion();
				
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public Outing find(int id) {
		Outing outing = null;
		ResultSet result = null;
		ResultSet result2 = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Outing WHERE IdOuting = "+id);
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
				outing.setNeedVeloSeats(result.getInt("NeedVeloSeats"));
				outing.setRemainingMemberSeats(result.getInt("RemainingMemberSeats"));
				outing.setRemainingVeloSeats(result.getInt("RemainingVeloSeats"));
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				outing.setOutingCalendar(calendarDAO.find(result.getInt("IdCalendar")));
	
				result2 = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Out_Vehicle WHERE IdOuting="+id);
				VehicleDAO vehicleDAO = new VehicleDAO(this.connect);
				while(result2.next()) {
					outing.getOutingVehicles().add(vehicleDAO.find(result2.getInt("IdVehicle")));
				}
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
		ResultSet result2 = null;
		List<Outing> allOutings = new ArrayList<Outing>();
		
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Outing");
			while(result.next()){
				// Compléter avec info de base
				
				outing = new Outing();
				outing.setNum(result.getInt("IdOuting"));
				int idOuting = outing.getNum();
				outing.setStartPoint(result.getString("StartPoint"));
				outing.setStartDate(result.getDate("DateStart"));
				outing.setForfeit(result.getDouble("Forfeit"));
				outing.setMaxMemberSeats(result.getInt("MaxMemberSeats"));
				outing.setMaxVeloSeats(result.getInt("MaxVeloSeats"));
				outing.setNeedMemberSeats(result.getInt("NeedMemberSeats"));
				outing.setNeedVeloSeats(result.getInt("NeedVeloSeats"));
				outing.setRemainingMemberSeats(result.getInt("RemainingMemberSeats"));
				outing.setRemainingVeloSeats(result.getInt("RemainingVeloSeats"));
				
				CalendarDAO calendarDAO = new CalendarDAO(this.connect);
				outing.setOutingCalendar(calendarDAO.find(result.getInt("IdCalendar")));
				
				result2 = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Out_Vehicle WHERE IdOuting="+idOuting);
				
				VehicleDAO vehicleDAO = new VehicleDAO(this.connect);
				while(result2.next()) {
					outing.getOutingVehicles().add(vehicleDAO.find(result2.getInt("IdVehicle")));
				}
				//System.out.println("OUTINGS GET ON DAO => "+outing.getStartPoint());
				allOutings.add(outing);
			}
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
           e.printStackTrace();
       }
		finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allOutings;
	}
}
