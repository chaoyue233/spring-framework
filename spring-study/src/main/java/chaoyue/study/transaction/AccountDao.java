package chaoyue.study.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void update(Account account) {
		jdbcTemplate.update("update test_account set balance=balance-? where id=?", account.getBalance(), account.getId());
	}
}
