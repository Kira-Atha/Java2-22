package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import be.huygebaert.POJO.Treasurer;

public class TreasurerDAO extends DAO<Treasurer> {
	public TreasurerDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Treasurer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Treasurer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Treasurer obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Treasurer find(int id) {
		Treasurer treasurer;
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Treasurer WHERE IdTreasurer = " +id);
			if(result.first()){
				// Compléter avec info de base
				treasurer = new Treasurer(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				treasurer.setId(result.getInt("IdTreasurer"));
				return treasurer;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Treasurer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
