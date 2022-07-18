package be.huygebaert.DAO;

import java.sql.Connection;
import java.util.List;

import be.huygebaert.POJO.Register;

public class RegisterDAO extends DAO<Register> {
	public RegisterDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Register obj) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}
}
