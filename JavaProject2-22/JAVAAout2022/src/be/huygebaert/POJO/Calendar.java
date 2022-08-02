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
	private static DAOFactory adf = new DAOFactory();
	private static DAO<Outing> outingDAO = adf.getOutingDAO();
	
	public Calendar() {
		this.calendarOutings = new ArrayList<Outing>();
	}
	
	public Calendar(int num) {
		this.num = num;
		this.calendarOutings = new ArrayList<Outing>();
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List <Outing> getCalendarOutings() {
		List <Outing> allOutings = Outing.getAllOutings();
		for(Outing out : allOutings) {
			if(out.getOutingCalendar().getNum() == this.getNum()) {
				this.calendarOutings.add(out);
			}
		}
		return this.calendarOutings;
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
			if(outingDAO.update(outing)) {
				getCalendarOutings();
				return true;
			}
		}
		return false;
	}
	public boolean deleteOuting(Outing outing) {
		if(outing!=null) {
			if(outingDAO.delete(outing)) {
				getCalendarOutings();
				return true;
			}
		}
		return false;
	}
}