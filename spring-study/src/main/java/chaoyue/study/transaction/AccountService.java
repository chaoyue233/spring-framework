package chaoyue.study.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class AccountService {

	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private AccountDao accountDao;

	public void transfer(Integer from, Integer to, Integer money) {
		// 在JDBC的层面 事务是通过 connection.setAutoCommit(false) 来开启一个事务的（关闭自动提交）
		TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		// 外界如果有事务，则 TransactionStatus 中会存放外界挂起事务。因为当前事务执行完成要恢复外界的挂起事务
		TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);
		try {
			Account account1 = new Account();
			account1.setId(from);
			account1.setBalance(money);
			Account account2 = new Account();
			account2.setId(to);
			account2.setBalance(-money);
			accountDao.update(account1);
			accountDao.update(account2);
			// commit中存在大量的回调，处理提前完成前后的扩展，处理资源的释放，挂起资源的恢复等等
			transactionManager.commit(transaction);
		} catch (Exception e) {
			System.out.println("转账失败...");
			// 处理的事情和commit差不多，rollback也是完成
			transactionManager.rollback(transaction);
		}
	}
}
