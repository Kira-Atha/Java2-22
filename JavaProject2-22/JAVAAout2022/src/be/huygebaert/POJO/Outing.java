package be.huygebaert.POJO;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Outing implements Serializable {
	private static final long serialVersionUID = -3227706264588669413L;
	private int num;
	private String startPoint;
	private Date startDate;
	private double forfeit;
	private int maxMemberSeats;
	private int maxVeloSeats;
	private int needMemberSeats;
	private int needVeloSeats;
	private int remainingMemberSeats;
	private int remainingVeloSeats;
	private Calendar outingCalendar;
	private List <Vehicle> outingVehicles = new ArrayList<Vehicle>();
	private List <Register> outingRegisters = new ArrayList<Register>();
	static DAOFactory adf = new DAOFactory();
	protected static DAO<Outing>outingDAO = adf.getOutingDAO();
	protected static List<Outing> allOutings = null;
	
	public Outing() {

	}
	
	public Outing(String startPoint, Date date, double forfeit,int maxMemberSeats, int maxVeloSeats,Calendar calendar) {
		this.startPoint = startPoint;
		this.startDate = date;
		this.forfeit = forfeit;
		this.outingCalendar = calendar;
		this.maxMemberSeats = maxMemberSeats;
		this.maxVeloSeats = maxVeloSeats;
		this.needMemberSeats = maxMemberSeats;
		this.needVeloSeats = maxVeloSeats;
		this.remainingMemberSeats = maxMemberSeats;
		this.remainingVeloSeats = maxVeloSeats;
		
		outingVehicles = new ArrayList<Vehicle>();
		outingRegisters = new ArrayList<Register>();
	}


	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getForfeit() {
		return forfeit;
	}

	public void setForfeit(double forfeit) {
		this.forfeit = forfeit;
	}
	
	
	public int getMaxMemberSeats() {
		return maxMemberSeats;
	}

	public void setMaxMemberSeats(int maxMemberSeats) {
		this.maxMemberSeats = maxMemberSeats;
	}

	public int getMaxVeloSeats() {
		return maxVeloSeats;
	}

	public void setMaxVeloSeats(int maxVeloSeats) {
		this.maxVeloSeats = maxVeloSeats;
	}
	
	public Calendar getOutingCalendar() {
		return outingCalendar;
	}

	public void setOutingCalendar(Calendar outingCalendar) {
		this.outingCalendar = outingCalendar;
	}
	
	public List<Vehicle> getOutingVehicles() {
		return outingVehicles;
	}

	public void setOutingVehicles(List<Vehicle> outingVehicles) {
		this.outingVehicles = outingVehicles;
	}

	public List<Register> getOutingRegisters() {
		return outingRegisters;
	}

	public void setOutingRegisters(List<Register> outingRegisters) {
		this.outingRegisters = outingRegisters;
	}

	public void setNeedMemberSeats(int needMemberSeats) {
		this.needMemberSeats = needMemberSeats;
	}

	public void setNeedVeloSeats(int needVeloSeats) {
		this.needVeloSeats = needVeloSeats;
	}

	public void setRemainingMemberSeats(int remainingMemberSeats) {
		this.remainingMemberSeats = remainingMemberSeats;
	}

	public void setRemainingVeloSeats(int remainingVeloSeats) {
		this.remainingVeloSeats = remainingVeloSeats;
	}

	public boolean addParticipant() {
		return false;
	}
	
	public boolean addVehicle(Vehicle vehicle) {
		if(this.needMemberSeats > 0  && this.needVeloSeats > 0 ) {
			this.outingVehicles.add(vehicle);
			this.needMemberSeats = this.needMemberSeats - vehicle.getTotalMemberSeats();
			this.needVeloSeats = this.needVeloSeats - vehicle.getTotalVeloSeats();
			
			if(outingDAO.update(this)) {
				return true;
			}
		}
		return false;
	}
	public double calculateForfeit() {
		double forfeit_ = 0;
		return forfeit_;
	}
	// TODO : Revoir
	public int getNeedMemberSeats() {
		return this.needMemberSeats;
	}

	public int getNeedVeloSeats() {
		return this.needVeloSeats;
	}

	public int getRemainingMemberSeats() {
		return remainingMemberSeats;
	}

	public int getRemainingVeloSeats() {
		return remainingVeloSeats;
	}
	public static List<Outing> getAllOutings() {
		allOutings = outingDAO.findAll();
		return allOutings;
	}
}
