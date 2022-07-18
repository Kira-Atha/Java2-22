package be.huygebaert.POJO;

import java.io.Serializable;

public class Register implements Serializable {
	private static final long serialVersionUID = 2958860344136235528L;
	private boolean passenger;
	private boolean velo;
	private Member rPassenger = null;
	private Velo rVelo = null;
	private Outing rOuting;
	
	public Register() {}
	
	public Register(boolean isPassenger, boolean isVelo,Outing rOuting,Member rPassenger, Velo rVelo) {
		if(isPassenger == true) {
			this.rPassenger=rPassenger;
		}
		if(isVelo== true) {
			this.rVelo = rVelo;
		}
		this.rOuting=rOuting;
	}
	public boolean isPassenger() {
		return true;
	}
	public void setPassenger(boolean passenger) {
		this.passenger = passenger;
	}
	public boolean isVelo() {
		return true;
	}
	public void setVelo(boolean velo) {
		this.velo = velo;
	}
	
	
}