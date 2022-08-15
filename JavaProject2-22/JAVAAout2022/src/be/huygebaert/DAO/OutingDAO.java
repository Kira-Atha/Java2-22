package be.huygebaert.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.huygebaert.POJO.Outing;
import be.huygebaert.POJO.Vehicle;

public class OutingDAO extends DAO<Outing> {
	public OutingDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Outing outing) {
		PreparedStatement ps0 = null;
		try {
			ps0 = this.connect.prepareStatement("INSERT INTO Outing VALUES (?,?,?,?,?,?,?,?,?,?,?)");
		    ps0.setInt(1, 0);
	        ps0.setString(2, outing.getStartPoint());
	        ps0.setDate(3,new java.sql.Date(outing.getStartDate().getTime()));
	        ps0.setDouble(4, outing.getForfeit());
	        ps0.setInt(5, outing.getMaxMemberSeats());
	        ps0.setInt(6, outing.getMaxVeloSeats());
	        ps0.setInt(7, outing.getNeedMemberSeats());
	        ps0.setInt(8, outing.getNeedVeloSeats());
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
       }finally{
			try {
				ps0.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean delete(Outing outing) {
		PreparedStatement ps= null;
		Outing outingToCompare = Outing.getOuting(outing.getNum());
		
		if(outingToCompare.getOutingVehicles().size() >0 || outingToCompare.getOutingRegisters().size() > 0) {
			return false;
		}
		
		try {
			ps = this.connect.prepareStatement("DELETE * FROM OUTING WHERE IdOuting = ?");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
            e.printStackTrace();
        }
		try {
			ps = this.connect.prepareStatement("DELETE * FROM Out_Vehicle WHERE IdOuting = ?");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
           e.printStackTrace();
		}
		try {
			ps = this.connect.prepareStatement("DELETE * FROM Register WHERE IdOuting = ?");
			ps.setInt(1,outing.getNum());
			ps.executeUpdate();
			return true;
		}catch(SQLException e) {
			 System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		}catch (Exception e) {
           e.printStackTrace();
       }finally{
			try {
				if(!Objects.isNull(ps)) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean update(Outing outing) {
		String sql_update = "UPDATE OUTING set StartPoint = ?,DateStart = ?,Forfeit=?,MaxMemberSeats=?,MaxVeloSeats=?,NeedMemberSeats=?,NeedVeloSeats=?,RemainingMemberSeats = ?,RemainingVeloSeats = ? WHERE IdOuting = ?";
		PreparedStatement statement = null;
		try {
			Outing outingToCompare = find(outing.getNum());
		// S'il n'y a pas de véhicule dans l'objet à modifier -> update du manager.
			if(outing.getOutingVehicles().size()==0) {
				//Empêcher la modification d'une sortie qui en db qui possède des véhicules
				if(outingToCompare.getOutingVehicles().size() >0 || outingToCompare.getOutingRegisters().size() >0) {
					return false;
				}
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
				return true;
				// Si l'objet courant contient 1 véhicule, alors il s'agit d'un ajout de véhicule à une sortie
			}else if(outing.getOutingVehicles().size() > 0){
				statement = this.connect.prepareStatement("INSERT INTO Out_Vehicle VALUES(?,?)");
			// Get the id of the last vehicle add on outing
				int numOfLastVehicle = outing.getOutingVehicles().get(outing.getOutingVehicles().size()-1).getNum();
				Vehicle lastVehicle = Vehicle.getVehicle(numOfLastVehicle);
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
					statement.executeUpdate();
				//Formule bidon possible à modifier
					lastVehicle.getDriver().updateBalance(outing.getForfeit()*lastVehicle.getTotalMemberSeats());
					return true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(!Objects.isNull(statement)) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
				result2.close();
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
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Outing ORDER BY cast(DateStart as date)");
			while(result.next()){
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
				result2.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allOutings;
	}
}