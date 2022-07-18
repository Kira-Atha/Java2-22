package be.huygebaert.POJO;

import java.io.Serializable;

public class Velo implements Serializable {
	private static final long serialVersionUID = 7274111556630336582L;
	private double weight;
	private String type;
	private double lenght;
	private Member member;
	// Pas besoin de savoir dans quell voiture se trouve le vélo, ni dans quelle inscription
	
	public Velo() {}
	
	public Velo(double weight, String type, double lenght, Member member) {
		this.weight=weight;
		this.type=type;
		this.lenght=lenght;
		this.member=member;
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

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}
