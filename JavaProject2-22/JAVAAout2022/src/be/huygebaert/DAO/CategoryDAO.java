package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Descender;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.TrailRider;
import be.huygebaert.POJO.Trialist;

public class CategoryDAO extends DAO<Category> {
	
	public CategoryDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Category obj) {
		return false;
	}

	@Override
	public boolean delete(Category obj) {
		return false;
	}

	@Override
	public boolean update(Category obj) {
		return false;
	}
	@Override
	public Category find(int id) {
		CycloDAO cycloDAO = new CycloDAO(this.connect);
		TrailRiderDAO trailriderDAO = new TrailRiderDAO(this.connect);
		DescenderDAO descenderDAO = new DescenderDAO(this.connect);
		TrialistDAO trialistDAO = new TrialistDAO(this.connect);
		Category category;
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Calendar WHERE IdCalendar ="+id);
			while(result.next()){
				if (result.getString("NameCategory").equals("Cyclo")){
					category = new Cyclo();
					category = cycloDAO.find(result.getInt("IdCalendar"));
					return category;
				}
				
				if (result.getString("NameCategory").equals("Descender")){
					category = new Descender();
					category = descenderDAO.find(result.getInt("IdCalendar"));
					return category;
				}
				if (result.getString("NameCategory").equals("Trailrider")){
					category = new TrailRider();
					category = trailriderDAO.find(result.getInt("IdCalendar"));
					return category;
				}
				if (result.getString("NameCategory").equals("Trialist")){
					category = new Trialist();
					category = trialistDAO.find(result.getInt("IdCalendar"));
					return category;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public List<Category> findAll(){
		List<Category> categories = new ArrayList<Category>();
		CycloDAO cycloDAO = new CycloDAO(this.connect);
		TrailRiderDAO trailriderDAO = new TrailRiderDAO(this.connect);
		DescenderDAO descenderDAO = new DescenderDAO(this.connect);
		TrialistDAO trialistDAO = new TrialistDAO(this.connect);
		
		Category category;
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Calendar");
			while(result.next()){
				switch(result.getString("NameCategory")) {
					case "Cyclo":
						category = new Cyclo();
						category = cycloDAO.find(result.getInt("IdCalendar"));
						categories.add(category);
						break;
					case "Descender":
						category = new Descender();
						category = descenderDAO.find(result.getInt("IdCalendar"));
						categories.add(category);
						break;
					case "Trailrider":
						category = new TrailRider();
						category = trailriderDAO.find(result.getInt("IdCalendar"));
						categories.add(category);
						break;
					case "Trialist":
						category = new Trialist();
						category = trialistDAO.find(result.getInt("IdCalendar"));
						categories.add(category);
						break;
				}
				/*
				if (result.getString("NameCategory").equals("Cyclo")){
					category = new Cyclo();
					category = cycloDAO.find(result.getInt("IdCalendar"));
					categories.add(category);
				}
				if (result.getString("NameCategory").equals("Descender")){
					category = new Descender();
					category = descenderDAO.find(result.getInt("IdCalendar"));
					categories.add(category);
				}
				if (result.getString("NameCategory").equals("Trailrider")){
					category = new TrailRider();
					category = trailriderDAO.find(result.getInt("IdCalendar"));
					categories.add(category);
				}
				if (result.getString("NameCategory").equals("Trialist")){
					category = new Trialist();
					category = trialistDAO.find(result.getInt("IdCalendar"));
					categories.add(category);
				}*/
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
}