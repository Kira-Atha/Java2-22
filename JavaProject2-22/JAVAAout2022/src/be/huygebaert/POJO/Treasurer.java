package be.huygebaert.POJO;

import java.util.List;

import be.huygebaert.DAO.DAO;

public class Treasurer extends Person {
	private static final long serialVersionUID = 7206955388975555505L;

	public Treasurer() {}

	public Treasurer(String firstname, String lastname, String password, String tel, String pseudo) {
		try {
			Person.idCount++;
			this.id = Person.idCount;
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
		if(member.calculateBalance(amount)) {
			if(member.updateBalance(member)) {
				return true;
			}
		}
		return false;
	}
	private void sendPaymentReminderLetter() {
		
	}
	
	private void payDriver() {
		
	}
	
	private void claimForfeit() {
		
	}
}
