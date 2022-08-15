package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Treasurer;

public class PersonDAO extends DAO<Person> {
	public PersonDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Person obj) {
		PreparedStatement ps = null;
		if(obj instanceof Member) {
			Member member = (Member) obj;
			try {
				ps = this.connect.prepareStatement("INSERT INTO Member VALUES (?,?,?,?,?,?,?)");
			    ps.setInt(1, 0);
		        ps.setString(2, member.getFirstname());
		        ps.setString(3, member.getLastname());
		        ps.setString(4, member.getPassword());
		        ps.setString(5, member.getTel());
		        member.setBalance(20);
		        ps.setDouble(6, member.getBalance());
		        ps.setString(7, member.getPseudo());

		        if(ps.executeUpdate() > 0) {
			        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
			            if (generatedKeys.next()) {
			                member.setId(generatedKeys.getInt(1));
			            }else {
			                return false;
			            }
			        }
		        }
		        member.createVelo(member.getMemberVelos().get(0));
		        member.joinCategory(member.getMemberCategories().get(0));
		        return true;
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					ps.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(obj instanceof Manager) {
			Manager manager = (Manager) obj;
			try {
			    ps = this.connect.prepareStatement("INSERT INTO Manager(Firstname,Lastname,Password,Tel,IdCalendar,Pseudo) VALUES(?,?,?,?,?,?)");
		        ps.setString(1, manager.getFirstname());
		        ps.setString(2, manager.getLastname());
		        ps.setString(3, manager.getPassword());
		        ps.setString(4, manager.getTel());
		        ps.setInt(5, manager.getCategory().getNum());
		        ps.setString(6, manager.getPseudo());
		        
		        if(ps.executeUpdate() >0) {
		        	return true;
		        }
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}finally {
				try {
					ps.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(obj instanceof Treasurer) {
			Treasurer treasurer = (Treasurer) obj;
			try {
			    ps = this.connect.prepareStatement("INSERT INTO Treasurer VALUES (?,?,?,?,?,?)");
			    ps.setInt(1, treasurer.getId());
		        ps.setString(2, treasurer.getFirstname());
		        ps.setString(3, treasurer.getLastname());
		        ps.setString(4, treasurer.getPassword());
		        ps.setString(5, treasurer.getTel());
		        ps.setString(6, treasurer.getPseudo());

		        if(ps.executeUpdate() > 0) {
		        	return true;
		        }
				
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}finally {
				try {
					ps.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean delete(Person obj) {
		return false;
	}

	@Override
	public boolean update(Person obj) {
		return false;
	}

	@Override
	public Person find(int id) {
		return null;
	}

	@Override
	public List<Person> findAll() {
		List<Person> allPersons = new ArrayList<Person>();
		Member member;
		Manager manager;
		Treasurer treasurer;
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Member");
			while(result.next()){
				// Compléter avec info de base
				member = new Member(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				member.setBalance(result.getDouble("Balance"));
				member.setId(result.getInt("IdMember"));
				// Compléter avec les catégories
				ResultSet result2 = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM cat_memb where IdMember =  " + result.getInt("IdMember"));
				CategoryDAO categoryDAO = new CategoryDAO(this.connect);
				while(result2.next()) {
					// L'id du calendrier = l'id de la catégorie ( type )
					member.getMemberCategories().add(categoryDAO.find(result2.getInt("IdCalendar")));	
				}
				allPersons.add(member);
			}
			result.close();
			result = this.connect.createStatement().executeQuery("SELECT * FROM Manager");
			while(result.next()){
				// Compléter avec info de base
				manager = new Manager(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				manager.setId(result.getInt("IdManager"));
				// Compléter avec les catégories
				CategoryDAO categoryDAO = new CategoryDAO(this.connect);
				manager.setCategory(categoryDAO.find(result.getInt("IdCalendar")));
				allPersons.add(manager);
			}
			result.close();
			result = this.connect.createStatement().executeQuery("SELECT * FROM Treasurer");
			while(result.next()){
				// Compléter avec info de base
				treasurer = new Treasurer(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				treasurer.setId(result.getInt("IdTreasurer"));
				allPersons.add(treasurer);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				result.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return allPersons;
	}
}
