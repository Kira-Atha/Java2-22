package be.huygebaert.POJO;

import java.io.Serializable;

public class Velo implements Serializable {
	private static final long serialVersionUID = 7274111556630336582L;
	private double weight;
	private String type;
	private double lenght;
	//private Member member;
	// Pas besoin de savoir dans quell voiture se trouve le vélo, ni dans quelle inscription
	
	public Velo() {}
	
	public Velo(String type,double weight, double lenght) {
		try {
			this.weight=weight;
			this.type=type;
			this.lenght=lenght;
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
}
