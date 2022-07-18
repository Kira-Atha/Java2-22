package be.huygebaert.DAO;

import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {
protected Connection connect = null;

	public DAO(Connection connection) {
		this.connect = connection;
	}
	
	public abstract boolean create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(int id);
	
	public abstract List<T>findAll();
}