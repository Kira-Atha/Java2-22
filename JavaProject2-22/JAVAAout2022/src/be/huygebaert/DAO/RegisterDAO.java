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

public class RegisterDAO extends DAO<Register> {
	public RegisterDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Register register) {
		try(PreparedStatement statement = this.connect.prepareStatement("INSERT INTO Register VALUES (?,?,?,?,?,?)")) {
			statement.setInt(1, 0);
			statement.setInt(2, register.getMember().getId());
			statement.setInt(3, register.getOuting().getNum());
			statement.setInt(4, register.getVelo().getNum());
			statement.setBoolean(5, register.isReg_passenger());
			statement.setBoolean(6, register.isReg_velo());
			if(statement.executeUpdate() > 0) {
				return true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Register obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Register obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Register find(int id) {
		// TODO Auto-generated method stub
		return null;
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
				allRegisters.add(register);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return allRegisters;
	}
}
