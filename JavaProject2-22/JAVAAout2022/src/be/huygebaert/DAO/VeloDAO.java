package be.huygebaert.DAO;

import java.sql.Connection;
import java.util.List;

import be.huygebaert.POJO.Velo;

public class VeloDAO extends DAO<Velo> {
	public VeloDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Velo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Velo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Velo obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Velo find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Velo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
