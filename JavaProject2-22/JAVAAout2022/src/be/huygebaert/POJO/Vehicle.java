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
	private int remainingMemberSeats;
	private int remainingVeloSeats;
	private Member vDriver;
	private List<Member> vPassengers;
	private List<Velo> vVelos;
	
	
	public Vehicle() {}
	

	public Vehicle(int totalMemberSeats, int totalVeloSeats, int remainingMemberSeats, int remainingVeloSeats,Member vDriver) {
		try {
			this.totalMemberSeats = totalMemberSeats;
			this.totalVeloSeats=totalVeloSeats;
			this.remainingMemberSeats = remainingMemberSeats-1;
			this.remainingVeloSeats = remainingVeloSeats;
			// -1 car le driver ajoute sa voiture. S'il dit qu'elle fait 4 places, il en reste 3 car il est compris.
			this.vDriver=vDriver;
			// Il est forcément passager de la voiture !
			vPassengers = new ArrayList<Member>();
			if(this.addPassenger(vDriver)) {
				System.out.print("Vehicle created !");
			}
			vVelos = new ArrayList<Velo>();
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


	public int getRemainingMemberSeats() {
		return remainingMemberSeats;
	}


	public void setRemainingMemberSeats(int remainingMemberSeats) {
		this.remainingMemberSeats = remainingMemberSeats;
	}


	public int getRemainingVeloSeats() {
		return remainingVeloSeats;
	}


	public void setRemainingVeloSeats(int remainingVeloSeats) {
		this.remainingVeloSeats = remainingVeloSeats;
	}


	public Member getvDriver() {
		return vDriver;
	}


	public void setvDriver(Member vDriver) {
		this.vDriver = vDriver;
	}


	public List<Member> getvPassengers() {
		return vPassengers;
	}


	public void setvPassengers(List<Member> vPassengers) {
		this.vPassengers = vPassengers;
	}


	public List<Velo> getvVelos() {
		return vVelos;
	}
	
	public void setvVelos(List<Velo> vVelos) {
		this.vVelos = vVelos;
	}
	
	public int getTotalMemberSeats() {
		return totalMemberSeats;
	}
	
	public void setTotalMemberSeats(int totalMemberSeats) {
		this.totalMemberSeats = totalMemberSeats;
	}
	
	public int getTotalVeloSeats() {
		return totalVeloSeats;
	}
	
	public void setTotalVeloSeats(int totalVeloSeats) {
		this.totalVeloSeats = totalVeloSeats;
	}
	
	public boolean addPassenger(Member member) {
		if(!member.equals(null) && !this.vPassengers.contains(member) && this.totalVeloSeats < this.vPassengers.size()){
			this.vPassengers.add(member);
			this.remainingMemberSeats-=1;
			return true;
		}
		return false;
	}
	public boolean addVelo(Velo velo) {
		if(!velo.equals(null) && !this.vVelos.contains(velo) && this.totalVeloSeats<this.vPassengers.size()) {
			this.vVelos.add(velo);
			this.remainingVeloSeats-=1;
			return true;
		}
		return false;
	}
	
	public Vehicle getVehicle(int id) {
		DAOFactory adf = new DAOFactory();
		DAO<Vehicle> vehicleDAO = adf.getVehicleDAO();
		
		return vehicleDAO.find(id);
	}
	
}
