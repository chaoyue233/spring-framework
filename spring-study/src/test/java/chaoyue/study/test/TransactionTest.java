package chaoyue.study.test;

import chaoyue.study.transaction.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionTest {

	@Test
	public void testTransaction() {
		AnnotationConfigApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("chaoyue.study.transaction");
		AccountService accountService = applicationContext.getBean(AccountService.class);
		accountService.transfer(1, 2, 100);
	}
}
