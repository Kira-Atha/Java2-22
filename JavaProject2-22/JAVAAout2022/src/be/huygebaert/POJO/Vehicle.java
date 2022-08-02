package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Vehicle implements Serializable {
	private static final long serialVersionUID = 3076977523777926314L;
	private int num;
	private int totalMemberSeats;
	private int totalVeloSeats;
	//private Member driver; => Est le premier "passager"
	private List<Member> passengersInVehicle;
	private List<Velo> velosInVehicle;
	private static DAOFactory adf = new DAOFactory();
	private static DAO<Vehicle> vehicleDAO = adf.getVehicleDAO();
	
	
	public Vehicle() {}
	
	public Vehicle(int totalMemberSeats, int totalVeloSeats,Member driver) {
		try {
			this.passengersInVehicle = new ArrayList<Member>();
			this.passengersInVehicle.add(driver);
			this.velosInVehicle = new ArrayList<Velo>();
			this.totalMemberSeats = totalMemberSeats;
			this.totalVeloSeats = totalVeloSeats;
		}catch(Exception e) {
			System.out.print("Vehicle doesn't create");
		}
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Member getDriver() {
		return this.passengersInVehicle.get(0);
	}

	public List<Member> getPassengers() {
		return passengersInVehicle;
	}

	public List<Velo> getVelos() {
		return velosInVehicle;
	}
	
	public int getTotalMemberSeats() {
		return totalMemberSeats;
	}
	
	public int getTotalVeloSeats() {
		return totalVeloSeats;
	}
	
	public boolean addPassenger(Member member) {
		if(member.equals(this.getDriver())){
			return true;
		}
		if(!member.equals(null) && !this.passengersInVehicle.contains(member) && this.totalMemberSeats > this.passengersInVehicle.size()){
			this.passengersInVehicle.add(member);
			return true;
		}
		return false;
	}
	public boolean addVelo(Velo velo) {
		if(!velo.equals(null) && !this.velosInVehicle.contains(velo) && this.totalVeloSeats > this.velosInVehicle.size()) {
			this.velosInVehicle.add(velo);
			return true;
		}
		return false;
	}

	public static Vehicle getVehicle(int id) {		
		return vehicleDAO.find(id);
	}
	
	public static List<Vehicle> getAllVehicles(){
		return vehicleDAO.findAll();
	}
	@Override
	public String toString() {
		return "(D) : "+this.getDriver()+" (M) "+ this.getPassengers().size() +"/"+this.getTotalMemberSeats()+" (V) : "+ this.getVelos().size()+"/"+this.getTotalVeloSeats();
	}
}
