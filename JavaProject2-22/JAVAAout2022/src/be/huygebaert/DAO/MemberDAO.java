package be.huygebaert.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import be.huygebaert.POJO.Category;
import be.huygebaert.POJO.Member;


public class MemberDAO extends DAO<Member>{
	public MemberDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Member member) {
		try(PreparedStatement ps = this.connect.prepareStatement("INSERT INTO Cat_Memb VALUES(?,?)")){
			ps.setInt(1, member.getId());
			if(member.getMemberCategories().size()==1) {
				ps.setInt(2, member.getMemberCategories().get(0).getNum());
			}else {
				Category lastCategory = member.getMemberCategories().get(member.getMemberCategories().size()-1);
				ps.setInt(2, lastCategory.getNum());
			}
			if(ps.executeUpdate()>0) {
				if(member.updateBalance(-20)) {
					return true;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Member obj) {
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
					member.getMemberCategories().add(categoryDAO.find(result.getInt("IdCalendar")));
				}
				
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
		try {
			ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Member");
			while(result.next()){
				member = new Member(result.getString("Firstname"),result.getString("Lastname"),result.getString("Password"),result.getString("Tel"),result.getString("Pseudo"));
				member.setBalance(result.getDouble("Balance"));
				member.setId(result.getInt("IdMember"));
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
