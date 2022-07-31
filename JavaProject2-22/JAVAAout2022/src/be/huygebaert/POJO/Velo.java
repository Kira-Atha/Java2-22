package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.List;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Velo implements Serializable {
	private static final long serialVersionUID = 7274111556630336582L;
	private double weight;
	private String type;
	private double lenght;
	private Member memberVelo;
	// Pas besoin de savoir dans quell voiture se trouve le vélo, ni dans quelle inscription
	private static DAOFactory adf = new DAOFactory();
	private static DAO<Velo> veloDAO = adf.getVeloDAO();
	
	public Velo() {}
	//create
	public Velo(double weight,String type, double lenght,Member member) {
		try {
			this.weight=weight;
			this.type=type;
			this.lenght=lenght;
			this.memberVelo = member;
		}catch(Exception e) {
			e.printStackTrace();
		}
		//this.member=member;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getLenght() {
		return lenght;
	}
	public void setLenght(double lenght) {
		this.lenght = lenght;
	}
	public Member getMemberVelo() {
		return this.memberVelo;
	}
	public String toString() {
		return this.getType();
	}
	
	public static List<Velo> getAllVelos(){
		return veloDAO.findAll();
	}
}
