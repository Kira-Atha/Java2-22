package be.huygebaert.POJO;

public class Treasurer extends Person {
	public Treasurer() {}
	public Treasurer(String firstname, String lastname, String password, String tel, String pseudo) {
		super(firstname,lastname,password,tel,pseudo);
	}

	public boolean managePayments(Member member,double amount) {
		if(member.updateBalance(amount)) {
			return true;
		}
		return false;
	}
}
