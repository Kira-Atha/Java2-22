package be.huygebaert.POJO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Outing{
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
	private List <Vehicle> outingVehicles;
	private List <Register> outingRegisters;
	private static DAOFactory adf = new DAOFactory();
	private static DAO<Outing>outingDAO = adf.getOutingDAO();
	private static List<Outing> allOutings = null;
	private static DAO<Register>registerDAO = adf.getRegisterDAO();
	
	public Outing() {
		this.outingVehicles = new ArrayList<Vehicle>();
		this.outingRegisters = new ArrayList<Register>();
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
		this.remainingMemberSeats = 0;
		this.remainingVeloSeats = 0;
		this.outingVehicles = new ArrayList<Vehicle>();
		this.outingRegisters = new ArrayList<Register>();
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
		List<Register> allRegisters = Register.getAllRegisters();
		for(Register reg : allRegisters) {
			if(reg.getOuting().equals(this) && !this.outingRegisters.contains(reg)) {
				this.outingRegisters.add(reg);
			}
		}
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
	
	public boolean addVehicle(Vehicle vehicle) {
		if(this.needMemberSeats > 0  || this.needVeloSeats > 0 ) {
			this.outingVehicles.add(vehicle);
			this.needMemberSeats = this.needMemberSeats - vehicle.getTotalMemberSeats();
			this.needVeloSeats = this.needVeloSeats - vehicle.getTotalVeloSeats();
			//Compter driver
			this.remainingMemberSeats = (this.remainingMemberSeats + vehicle.getTotalMemberSeats())-1;
			this.remainingVeloSeats = this.remainingVeloSeats+vehicle.getTotalVeloSeats();
			if(outingDAO.update(this)) {
				return true;
			}
		}
		return false;
	}
	
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
	public static Outing getOuting(int id) {
		return outingDAO.find(id);
	}
	
	//addParticipant() in class diagram
	public boolean createRegister(Register register) {
		boolean registeredIsDriver = false;
		if(( this.getRemainingMemberSeats() == 0 && register.isReg_passenger() ) || ( this.getRemainingVeloSeats() == 0 && register.isReg_velo())) {
			return false;
		}
		for(Vehicle vehicle : this.getOutingVehicles()) {
			if(register.getMember().equals(vehicle.getDriver())) {
				registeredIsDriver = true;
			}
		}
		
		if(!this.getOutingRegisters().contains(register) && !Objects.isNull(register)) {
			if(register.isReg_passenger() && !registeredIsDriver) {
				this.remainingMemberSeats-=1;
			}
			if(register.isReg_velo()) {
				this.remainingVeloSeats-=1;
			}
			this.getOutingRegisters().add(register);
			if(registerDAO.create(register)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return String.valueOf(this.getNum());
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
			
		if((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}

		final Outing test = (Outing)o;
		return this.getNum() == test.getNum();
	}
	@Override
	public int hashCode() {
		return this.getNum();
	}
}
