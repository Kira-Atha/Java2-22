package be.huygebaert.DAO;

import java.sql.Connection;
import java.util.List;

import be.huygebaert.POJO.Outing;

public class OutingDAO extends DAO<Outing> {
	public OutingDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Outing obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Outing obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Outing obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Outing find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Outing> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
