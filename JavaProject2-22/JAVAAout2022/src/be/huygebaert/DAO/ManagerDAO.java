package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;

public class ManagerDAO extends DAO<Manager>{
	public ManagerDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Manager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Manager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Manager obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Manager find(int id) {
		Manager manager;
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
					"SELECT * FROM Manager INNER JOIN Calendar "
					+ "ON Calendar.IdCalendar = Manager.IdCalendar "
					+ " WHERE IdManager = " +id);
			if(result.first()){
				// Compléter avec info de base
				manager = new Manager(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				manager.setId(result.getInt("IdManager"));
				// Compléter avec la catégorie
				CycloDAO cycloDAO = new CycloDAO(this.connect);
				DescenderDAO descenderDAO = new DescenderDAO(this.connect);
				TrailRiderDAO trailRiderDAO = new TrailRiderDAO(this.connect);
				TrialistDAO trialistDAO = new TrialistDAO(this.connect);
				
				if(result.getInt("IdCalendar") == 1) {
					manager.setCategory(cycloDAO.find(result.getInt("IdCalendar")));
				}
				if(result.getInt("IdCalendar") == 2) {
					manager.setCategory(descenderDAO.find(result.getInt("IdCalendar")));
				}
				if(result.getInt("IdCalendar") == 3) {
					manager.setCategory(trailRiderDAO.find(result.getInt("IdCalendar")));
				}
				if(result.getInt("IdCalendar") == 4) {
					manager.setCategory(trialistDAO.find(result.getInt("IdCalendar")));
				}
				
				return manager;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Manager> findAll() {
		List <Manager> allManagers = new ArrayList<Manager>();
		Manager manager;
		
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Manager");
			while(result.next()){
				// Compléter avec info de base
				manager = new Manager(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				manager.setId(result.getInt("IdManager"));
				// Compléter avec les catégories
				
				if(result.first()) {
					CycloDAO cycloDAO = new CycloDAO(this.connect);
					DescenderDAO descenderDAO = new DescenderDAO(this.connect);
					TrailRiderDAO trailRiderDAO = new TrailRiderDAO(this.connect);
					TrialistDAO trialistDAO = new TrialistDAO(this.connect);
					
					if(result.getInt("IdCalendar") == 1) {
						manager.setCategory(cycloDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 2) {
						manager.setCategory(descenderDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 3) {
						manager.setCategory(trailRiderDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 4) {
						manager.setCategory(trialistDAO.find(result.getInt("IdCalendar")));
					}
				}
				allManagers.add(manager);
			}
			return allManagers;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
