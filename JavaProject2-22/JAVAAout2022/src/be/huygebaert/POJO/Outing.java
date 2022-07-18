package be.huygebaert.POJO;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Outing implements Serializable {
	private static final long serialVersionUID = -3227706264588669413L;
	private int num;
	private String startPoint;
	private Date dateStart;
	private double forfeit;
	private int maxMemberSeats;
	private int maxVeloSeats;
	private int needMemberSeats;
	private int needVeloSeats;
	private int remainingMemberSeats;
	private int remainingVeloSeats;
	private Calendar outingCalendar;
	private List <Vehicle> outingVehicles;
	private List <Register> outingRegisters;
	
	
	public Outing() {}
	
	public Outing(String startPoint, Date dateStart, double forfeit, Calendar calendar,int maxMemberSeats, int maxVeloSeats) {
		this.startPoint = startPoint;
		this.dateStart = dateStart;
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

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
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
	
	public boolean addVehicle() {
		return false;
	}
	public double calculateForfeit() {
		double forfeit_ = 0;
		return forfeit_;
	}
}
