package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Calendar implements Serializable {

	private static final long serialVersionUID = -7374868150598752577L;
	private int num;
	private List <Outing> calendarOutings;
	private Category calendarCategory;
	
	public Calendar() {}
	
	public Calendar(int num,Category category) {
		this.num = num;
		// Composition de Category, attention ! UN CALENDRIER par catégorie ! Constructeur appelé dans l'unique instanciation des catégories
		this.calendarOutings = new ArrayList<Outing>();
		this.calendarCategory = category;
	}
	
	// Est inutile, non? Je peux récupérer via les références.
	public Calendar getInstanceOfCalendar(Category category) {
		Calendar calendar = null;
		return calendar;
	}

	public Category getCalendarCategory() {
		return calendarCategory;
	}

	public void setCalendarCategory(Category calendarCategory) {
		this.calendarCategory = calendarCategory;
	}
	
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List <Outing> getCalendarOutings() {
		return calendarOutings;
	}

	public void setCalendarOutings(List <Outing> calendarOutings) {
		this.calendarOutings = calendarOutings;
	}


	public boolean addOuting() {
		// TODO
		Outing outing = new Outing();
		if(outing!=null) {
			this.calendarOutings.add(outing);
			return true;
		}
		return false;
	}	
	public boolean updateOuting(Outing outing) {
		return false;
	}
	public boolean deleteOuting(Outing outing) {
		return false;
	}
}