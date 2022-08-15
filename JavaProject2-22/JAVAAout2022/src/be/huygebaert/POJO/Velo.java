package be.huygebaert.POJO;
import java.util.List;
import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

public class Velo {
	private int num;
	private double weight;
	private String type;
	private double lenght;
	private Member memberVelo;
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
	public static Velo getVelo(int id) {
		return veloDAO.find(id);
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
			
		if((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}

		final Velo test = (Velo)o;
		return this.getNum() == test.getNum();
	}
	@Override
	public int hashCode() {
		return this.hashCode();
	}
}
