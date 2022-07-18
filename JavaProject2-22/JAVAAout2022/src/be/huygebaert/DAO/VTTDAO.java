package be.huygebaert.DAO;

import java.sql.Connection;
import java.util.List;

import be.huygebaert.POJO.VTT;

public class VTTDAO extends DAO<VTT>{
	public VTTDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(VTT obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(VTT obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(VTT obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VTT find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VTT> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
