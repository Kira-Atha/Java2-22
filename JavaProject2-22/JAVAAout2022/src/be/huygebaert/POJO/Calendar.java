package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Calendar implements Serializable {

	private static final long serialVersionUID = -7374868150598752577L;
	private int num;
	private List <Outing> calendarOutings;
	//private Category calendarCategory;
	static DAOFactory adf = new DAOFactory();
	DAO<Outing> outingDAO = adf.getOutingDAO();
	
	public Calendar() {
		this.calendarOutings = new ArrayList<Outing>();
	}
	
	public Calendar(int num,Category category) {
		this.num = num;
		// Composition de Category, attention ! UN CALENDRIER par catégorie ! Constructeur appelé dans l'unique instanciation des catégories
		this.calendarOutings = new ArrayList<Outing>();
		//this.calendarCategory = category;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List <Outing> getCalendarOutings() {
		return this.calendarOutings;
	}

	public void setCalendarOutings(List <Outing> calendarOutings) {
		this.calendarOutings = calendarOutings;
	}

	public boolean addOuting(Outing outing) {
		if(outing!=null) {
			this.getCalendarOutings().add(outing);
			if(outingDAO.create(outing)) {
				return true;
			}
		}
		return false;
	}	
	public boolean updateOuting(Outing outing) {
		if(outing!=null) {
			this.getCalendarOutings().set(outing.getNum(),outing);
			if(outingDAO.update(outing)) {
				return true;
			}
		}
		return false;
	}
	public boolean deleteOuting(Outing outing) {
		if(outing!=null) {
			this.getCalendarOutings().remove(outing.getNum());
			if(outingDAO.delete(outing)) {
				return true;
			}
		}
		return false;
	}
}