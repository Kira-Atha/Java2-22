package be.huygebaert.POJO;
import java.util.ArrayList;
import java.util.List;
import be.huygebaert.DAO.DAO;
import be.huygebaert.DAO.DAOFactory;

abstract public class Person {
	protected int id;
	protected String firstname;
	protected String lastname;
	protected String password;
	protected String tel;
	protected String pseudo;
	protected static DAOFactory adf = new DAOFactory();
	protected static List<Person> allPersons = new ArrayList<Person>();
	protected static DAO<Person>personDAO = adf.getPersonDAO();
	protected static DAO<Treasurer>treasurerDAO = adf.getTreasurerDAO();
	protected static DAO<Member>memberDAO = adf.getMemberDAO();
	protected static DAO<Manager>managerDAO = adf.getManagerDAO();
	
	public Person() {}
	
	public Person(String firstname, String lastname, String password, String tel, String pseudo) {
		try {
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
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
	public Person signIn() {	
		allPersons = getAllPersons();
		Person toConnect = null;
		for(Person person : allPersons) {
			if(this.getPseudo().equals(person.getPseudo()) && this.getPassword().equals(person.getPassword())){
				if(person instanceof Treasurer ) {
					toConnect = Person.getTreasurer(person.getId());
				}
				if(person instanceof Member){
					toConnect = Person.getMember(person.getId());
				}
				if(person instanceof Manager) {
					toConnect = Person.getManager(person.getId());
				}
			}
		}
		return toConnect;
	}
	public boolean signUp() {
		allPersons = getAllPersons();
		if(!allPersons.contains(this)) {
			if(this instanceof Manager) {
				List <Manager> allManagers = Manager.getAllManagers();
				for(Manager man : allManagers) {
					if(((Manager)this).getCategory().getNum() == man.getCategory().getNum()) {
						return false;
					}
				}
			}
			if(personDAO.create(this)) {
				return true;
			}
		}
		return false;
	}
	public static List<Person> getAllPersons() {
		return personDAO.findAll();
	}
	public static Treasurer getTreasurer(int id) {
		return treasurerDAO.find(id);
	}
	public static Member getMember(int id) {
		return memberDAO.find(id);
	}
	public static Manager getManager(int id) {
		return managerDAO.find(id);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
			
		if((o == null) || (o.getClass() != this.getClass())) {
			return false;
		}

		final Person test = (Person)o;
		return this.getId() == test.getId() || this.getPseudo().equals(test.getPseudo());
	}
	@Override
	public int hashCode() {
		return this.getId();
	}
}
