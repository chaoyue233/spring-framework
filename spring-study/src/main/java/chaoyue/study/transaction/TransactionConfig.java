package chaoyue.study.transaction;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableAspectJAutoProxy
public class TransactionConfig {

	/**
	 * 注入数据源
	 */
	@Bean
	public DataSource dataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource();
		hikariDataSource.setUsername("root");
		hikariDataSource.setPassword("123456");
		hikariDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jdbc-study?useSSL=false&serverTimezone=UTC");
		hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		return hikariDataSource;
	}

	/**
	 * 注入JdbcTemplate
	 */
	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource());
		return jdbcTemplate;
	}

	/**
	 * 注入事务管理器
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}
}
