package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Treasurer;
import be.huygebaert.POJO.Velo;

public class VeloDAO extends DAO<Velo> {
	public VeloDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Velo velo) {
		PreparedStatement ps0 = null;
		try{
			ps0 = this.connect.prepareStatement("INSERT INTO Velo VALUES (?,?,?,?,?)");
		    ps0.setInt(1, 0);
	        ps0.setDouble(2, velo.getWeight());
	        ps0.setString(3, velo.getType());
	        ps0.setDouble(4, velo.getLenght());
	        ps0.setInt(5, velo.getMemberVelo().getId());
	        if(ps0.executeUpdate() > 0) {
	        	try (ResultSet generatedKeys = ps0.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                velo.setNum(generatedKeys.getInt(1));
		                return true;
		            }
	        	}
	        }
		}catch(SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps0.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean delete(Velo obj) {
		return false;
	}

	@Override
	public boolean update(Velo obj) {
		return false;
	}

	@Override
	public Velo find(int id) {
		ResultSet result = null;
		Velo velo = null;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Velo where IdVelo ="+id);
			while(result.next()) {
				velo = new Velo(result.getDouble("Weight"),result.getString("Type"),result.getDouble("Lenght"),memberDAO.find(result.getInt("IdMember")));
				velo.setNum(result.getInt("IdVelo"));
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
		return velo;
	}

	@Override
	public List<Velo> findAll() {
		ResultSet result = null;
		List<Velo> allVelos = new ArrayList <Velo>();
		Velo velo;
		MemberDAO memberDAO = new MemberDAO(this.connect);
		
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Velo");
			while(result.next()){
				velo = new Velo(result.getDouble("Weight"),result.getString("Type"),result.getDouble("Lenght"),memberDAO.find(result.getInt("IdMember")));
				velo.setNum(result.getInt("IdVelo"));
				allVelos.add(velo);
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
		return allVelos;
	}
}
