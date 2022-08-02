package be.huygebaert.POJO;

public class Treasurer extends Person {
	private static final long serialVersionUID = 7206955388975555505L;

	public Treasurer() {}

	public Treasurer(String firstname, String lastname, String password, String tel, String pseudo) {
		try {
			this.firstname = firstname;
			this.lastname=lastname;
			this.password=password;
			this.tel=tel;
			this.pseudo=pseudo;
		}catch(Exception e) {
			System.out.println("Treasurer doesn't create");
		}
	}

	public boolean managePayments(Member member,double amount) {
		if(member.updateBalance(amount)) {
			return true;
		}
		return false;
	}
}
