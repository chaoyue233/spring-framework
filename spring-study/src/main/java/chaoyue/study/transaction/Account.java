package chaoyue.study.transaction;

public class Account {
	private Integer id;
	private Integer balance;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id=" + id +
				", balance=" + balance +
				'}';
	}
}
