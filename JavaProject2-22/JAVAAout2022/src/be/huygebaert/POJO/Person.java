package be.huygebaert.POJO;

import java.io.Serializable;

import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

abstract public class Person implements Serializable {
	private static final long serialVersionUID = 8586954274748508608L;
	protected static int idCount = 0;
	protected int id;
	protected String firstname;
	protected String lastname;
	protected String password;
	protected String tel;
	protected String pseudo;
	static DAOFactory adf = new DAOFactory();
	protected DAO<Person>personDAO = adf.getPersonDAO();
	
	public Person() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public boolean signIn() {
		
		return false;
	}
	public boolean signUp() {
		return false;
	}
	
}
