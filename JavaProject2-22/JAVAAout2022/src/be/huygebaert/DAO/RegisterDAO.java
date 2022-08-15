package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.huygebaert.POJO.Velo;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Outing;

import be.huygebaert.POJO.Register;
import be.huygebaert.POJO.Vehicle;

public class RegisterDAO extends DAO<Register> {
	public RegisterDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Register register) {
		PreparedStatement statement = null;
		PreparedStatement statement0 = null;
		try {
			statement = this.connect.prepareStatement("INSERT INTO Register VALUES (?,?,?,?,?,?)");
			statement.setInt(1, 0);
			statement.setInt(2, register.getMember().getId());
			statement.setInt(3, register.getOuting().getNum());
			statement.setInt(4, register.getVelo().getNum());
			statement.setBoolean(5, register.isReg_passenger());
			statement.setBoolean(6, register.isReg_velo());
			if(statement.executeUpdate() > 0) {
				String update_sql = "";
				if(register.isReg_passenger() || register.isReg_velo()) {
					update_sql = "UPDATE Outing set RemainingMemberSeats = ?,RemainingVeloSeats=? WHERE IdOuting = ?";
					try {
						statement0 = this.connect.prepareStatement(update_sql);
						statement0.setInt(1,register.getOuting().getRemainingMemberSeats());
						statement0.setInt(2,register.getOuting().getRemainingVeloSeats());
						statement0.setInt(3,register.getOuting().getNum());
						statement0.executeUpdate();
						try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
							if (generatedKeys.next()) {
								register.setNum(generatedKeys.getInt(1));
							}
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
					// Il paie sa place dans la voiture
				}
				// Il paie sa place dans la voiture sauf s'il est conducteur
				boolean registeredIsDriver = false;
				for(Vehicle vehicle : register.getOuting().getOutingVehicles()) {
					if(register.getMember().equals(vehicle.getDriver())) {
						registeredIsDriver = true;
					}
				}
				
				if(register.isReg_passenger() && !registeredIsDriver) {
					register.getMember().updateBalance(-(register.getOuting().getForfeit()/4));
				}
				//Il paie la place de son vélo dans la voiture sauf s'il est conducteur
				if(register.isReg_velo() && !registeredIsDriver) {
					register.getMember().updateBalance(-(register.getOuting().getForfeit()/4));
				}
				// Il paie sa réservation même s'il est conducteur
				register.getMember().updateBalance(-(register.getOuting().getForfeit()));
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				statement.close();
				if(!Objects.isNull(statement0)) {
					statement0.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Register obj) {
		return false;
	}

	@Override
	public boolean update(Register obj) {
		return false;
	}

	@Override
	public Register find(int id) {
		ResultSet result = null;
		Register register = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Register WHERE IdOuting="+id);
			while(result.next()){
				register = new Register(result.getBoolean("RegPassenger"),result.getBoolean("RegVelo"),Person.getMember(result.getInt("IdMember")),Velo.getVelo(result.getInt("IdVelo")),Outing.getOuting(result.getInt("IdOuting")));
				register.setNum(result.getInt("IdRegister"));
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
		return register;
	}

	@Override
	public List<Register> findAll() {
		ResultSet result = null;
		List<Register> allRegisters = new ArrayList<Register>();
		Register register;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Register");
			while(result.next()) {
				register = new Register(result.getBoolean("RegPassenger"),result.getBoolean("RegVelo"),Person.getMember(result.getInt("IdMember")),Velo.getVelo(result.getInt("IdVelo")),Outing.getOuting(result.getInt("IdOuting")));
				register.setNum(result.getInt("IdRegister"));
				allRegisters.add(register);
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
		return allRegisters;
	}
}
