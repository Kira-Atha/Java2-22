package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Vehicle;
import be.huygebaert.POJO.Velo;

public class VehicleDAO extends DAO<Vehicle>{
	public VehicleDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Vehicle vehicle) {
		try(PreparedStatement ps0 = this.connect.prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?)")) {
		    ps0.setInt(1, 0);
	        ps0.setInt(2, vehicle.getTotalMemberSeats());
	        ps0.setInt(3, vehicle.getTotalVeloSeats());
	        ps0.setInt(4, vehicle.getDriver().getId());

	        if(ps0.executeUpdate() > 0) {
	        	return true;
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Vehicle obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Vehicle vehicle) {
		Vehicle vehicleToCompare = Vehicle.getVehicle(vehicle.getNum());
		
		if(vehicleToCompare.getPassengers().size() == vehicle.getPassengers().size()) {
			// Alors c'est l'ajout du dernier vélo de la liste
			try(PreparedStatement statement = this.connect.prepareStatement("INSERT INTO Velo_Vehicle VALUES (?,?)")) {
				statement.setInt(1, vehicle.getNum());
				statement.setInt(2, vehicle.getVelos().get(vehicle.getVelos().size()-1).getNum());
				
				if(statement.executeUpdate() > 0) {
					return true;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(vehicleToCompare.getVelos().size() == vehicle.getVelos().size()) {
			// Alors c'est l'ajout de passager
			
			try(PreparedStatement statement = this.connect.prepareStatement("INSERT INTO Passenger_Vehicle VALUES (?,?)")) {
				statement.setInt(1, vehicle.getPassengers().get(vehicle.getPassengers().size()-1).getId());
				statement.setInt(2, vehicle.getNum());
				
				if(statement.executeUpdate() >0) {
					return true;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public Vehicle find(int id) {
		Vehicle vehicle = null;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Vehicle WHERE IdVehicle ="+id);
			while(result.next()){
				vehicle = new Vehicle(result.getInt("TotalMemberSeats"),result.getInt("TotalVeloSeats"),memberDAO.find(result.getInt("IdDriver")));
				vehicle.setNum(result.getInt("IdVehicle"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return vehicle;
	}

	@Override
	public List<Vehicle> findAll() {
		List<Vehicle> allVehicles = new ArrayList <Vehicle>();
		Vehicle vehicle;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Vehicle");
			while(result.next()){
				vehicle = new Vehicle(result.getInt("TotalMemberSeats"),result.getInt("TotalVeloSeats"),memberDAO.find(result.getInt("IdDriver")));
				vehicle.setNum(result.getInt("IdVehicle"));
				allVehicles.add(vehicle);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return allVehicles;
	}
}
