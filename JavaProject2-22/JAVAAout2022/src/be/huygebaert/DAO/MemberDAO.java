package be.huygebaert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

	// Pour rejoindre la catégorie, je profite de cette DAO vide ( INSERT INTO => CREATE )
	@Override
	public boolean create(Member member) {
		try(PreparedStatement ps = this.connect.prepareStatement("INSERT INTO Cat_Memb VALUES(?,?)")){
			ps.setInt(1, member.getId());
			ps.setInt(2, member.getMemberCategories().get(0).getNum());
			System.out.println("[PERSON DAO]MEMBER ID => "+member.getId()+" CATE ID => "+member.getMemberCategories().get(0).getNum());
			if(ps.executeUpdate()>1) {
				return true;
			}else {
				return false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Member obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Member member) {
		String sql = "UPDATE MEMBER set Balance = ? WHERE idMember = ?";
		try {
			PreparedStatement statement = this.connect.prepareStatement(sql);
			statement.setDouble(1,member.getBalance());
			statement.setInt(2,member.getId());
			statement.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Member find(int id) {
		Member member;
		ResultSet result = null;
		try {
			result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Member WHERE IdMember = " +id);
			if(result.first()){
		// Member info
				String firstname = result.getString("Firstname");
				
				member = new Member(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				member.setBalance(result.getDouble("Balance"));
				member.setId(result.getInt("IdMember"));
		// Category of member
				
				result = this.connect.createStatement().executeQuery("SELECT * FROM Cat_Memb where IdMember =  " + id);
				CategoryDAO categoryDAO = new CategoryDAO(this.connect);
				while(result.next()) {
					// L'id du calendrier = l'id de la catégorie ( type )
					//System.out.println("This member "+firstname+" have : " +result.getInt("IdCalendar") );
					member.getMemberCategories().add(categoryDAO.find(result.getInt("IdCalendar")));
					//System.out.println("BUg0");
				}
				
				/*
		// Vehicle of member
				result = this.connect.createStatement().executeQuery("SELECT * FROM VEHICLE where IdDriver ="+id );
				VehicleDAO vehicleDAO = new VehicleDAO(this.connect);
				while(result.next()) {
					member.setMemberVehicle(vehicleDAO.find(result.getInt("IdVehicle")));
				}
				
		// Velo(s) of member
				
				result = this.connect.createStatement().executeQuery("SELECT * FROM VELO where IdMember = "+id);
				VeloDAO veloDAO = new VeloDAO(this.connect);
				while(result.next()) {
					member.getMemberVelos().add(veloDAO.find(result.getInt("IdVelo")));
				}
				*/
				return member;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
					switch(result2.getInt("IdCalendar")) {
						case 1:
							member.getMemberCategories().add(cycloDAO.find(result2.getInt("IdCalendar")));
						case 2:
							member.getMemberCategories().add(descenderDAO.find(result2.getInt("IdCalendar")));
						case 3:
							member.getMemberCategories().add(trailRiderDAO.find(result2.getInt("IdCalendar")));
						case 4:
							member.getMemberCategories().add(trialistDAO.find(result2.getInt("IdCalendar")));
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
