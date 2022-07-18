package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import be.huygebaert.POJO.Cyclo;
import be.huygebaert.POJO.Manager;
import be.huygebaert.POJO.Member;
import be.huygebaert.POJO.Person;
import be.huygebaert.POJO.Treasurer;

public class MemberDAO extends DAO<Member>{
	public MemberDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Member find(int id) {
		Member member;
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Member WHERE IdMember = " +id);
			if(result.first()){
				// Compléter avec info de base
				member = new Member(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				member.setBalance(result.getDouble("Balance"));
				member.setId(result.getInt("IdMember"));
				// Compléter avec les catégories
				result = this.connect.createStatement().executeQuery("SELECT * FROM cat_memb where IdMember =  " + id);
				CategoryDAO categoryDAO = new CategoryDAO(this.connect);
				while(result.next()) {
					// L'id du calendrier = l'id de la catégorie ( type )
					member.getMemberCategories().add(categoryDAO.find(result.getInt("IdCalendar")));
				}
				return member;
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Member> findAll() {
		List <Member> allMembers = new ArrayList<Member>();
		Member member;
		// TODO Leur(s) vélo(s) ? Véhicule ? Inscription? 
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Member");
			while(result.next()){
				// Compléter avec info de base
				member = new Member(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				member.setBalance(result.getDouble("Balance"));
				member.setId(result.getInt("IdMember"));
				// Compléter avec les catégories
				ResultSet result2 = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery(
						"SELECT * FROM Member INNER JOIN Cat_Memb "
						+ "ON Member.IdMember = Cat_Memb.IdMember "
						+ "INNER JOIN Calendar "
						+ "ON Calendar.IdCalendar = Cat_Memb.IdCalendar "
						+ "WHERE IdMember =" + result.getInt("IdMember"));

				while(result2.next()) {
					CycloDAO cycloDAO = new CycloDAO(this.connect);
					DescenderDAO descenderDAO = new DescenderDAO(this.connect);
					TrailRiderDAO trailRiderDAO = new TrailRiderDAO(this.connect);
					TrialistDAO trialistDAO = new TrialistDAO(this.connect);
					
					if(result2.getInt("IdCalendar") == 1) {
						member.getMemberCategories().add(cycloDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 2) {
						member.getMemberCategories().add(descenderDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 3) {
						member.getMemberCategories().add(trailRiderDAO.find(result.getInt("IdCalendar")));
					}
					if(result.getInt("IdCalendar") == 4) {
						member.getMemberCategories().add(trialistDAO.find(result.getInt("IdCalendar")));
					}
				}
				allMembers.add(member);
			}
			return allMembers;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
