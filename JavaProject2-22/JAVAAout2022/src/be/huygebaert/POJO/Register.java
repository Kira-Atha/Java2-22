package be.huygebaert.POJO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Register implements Serializable {
	private static final long serialVersionUID = 2958860344136235528L;
	private boolean reg_passenger;
	private boolean reg_velo;
	private Member member;
	private Velo velo;
	private Outing outing;
	private static DAOFactory adf = new DAOFactory();
	private static DAO<Register>registerDAO = adf.getRegisterDAO();
	
	//Velo peut être null car 0-1
	public Register(boolean reg_passenger,boolean reg_velo,Member member, Velo velo, Outing outing) {
		this.reg_passenger = reg_passenger;
		this.reg_velo = reg_velo;
		this.velo=velo;
		this.member = member;
		this.outing = outing;
	}

	public boolean isReg_passenger() {
		return reg_passenger;
	}

	public void setReg_passenger(boolean reg_passenger) {
		this.reg_passenger = reg_passenger;
	}

	public boolean isReg_velo() {
		return reg_velo;
	}

	public void setReg_velo(boolean reg_velo) {
		this.reg_velo = reg_velo;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Velo getVelo() {
		return velo;
	}

	public void setVelo(Velo velo) {
		this.velo = velo;
	}

	public Outing getOuting() {
		return outing;
	}

	public void setOuting(Outing outing) {
		this.outing = outing;
	}
	@Override
	public String toString() {
		String typeVelo = "";
		if(!Objects.isNull(this.getVelo())){
			typeVelo= " TYPE VELO : "+ this.getVelo().getType();
		}
		return "REGISTER VALUE => PSEUDO MEMBRE " + this.getMember().getPseudo()+typeVelo +" POINT DE DEPART DE LA SORTIE "+ this.getOuting().getStartPoint() +" EST PASSAGER ? "+ this.isReg_passenger() +" MET SON VELO DANS UNE VOITURE ?" + this.isReg_velo();
	}
	
	public static List <Register> getAllRegisters() {
		return registerDAO.findAll();
	}

}