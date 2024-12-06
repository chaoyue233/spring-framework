package chaoyue.study.test.tool;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class EnvironmentTest {
	private final Logger log = LoggerFactory.getLogger(EnvironmentTest.class);

	/**
	 * Environment 中封装了系统环境变量和jvm参数的信息
	 * PropertiesPropertySource：JVM相关信息
	 * SystemEnvironmentPropertySource：系统环境变量信息
	 */
	@Test
	public void testMoreEnvProperties() {
		ApplicationContext ctx = new GenericApplicationContext();
		Environment env = ctx.getEnvironment();
		log.info("java-version ->{}",env.getProperty("java.vm.specification.version"));
		log.info("JAVA_HOME -> {}", env.getProperty("JAVA_HOME"));
	}

	/**
	 * 使用JDK获取JVM信息
	 */
	@Test
	public void testSystemProperties() {
		Properties properties = System.getProperties();
		Set<Map.Entry<Object, Object>> entries = properties.entrySet();
		for (Map.Entry<Object, Object> entry : entries) {
			System.out.println(entry);
		}
	}

	/**
	 * 使用JDK获取系统环境变量
	 */
	@Test
	public void testSystemEnvironment() {
		Map<String, String> env = System.getenv();
		Set<Map.Entry<String, String>> entries = env.entrySet();
		for (Map.Entry<String, String> entry : entries) {
			System.out.println(entry);
		}
	}

	/**
	 * 可以通过代码动态添加自己定义的配置信息
	 * 配置信息会进行合并，并通过一定的顺序进行获取（配置覆盖）
	 */
	@Test
	public void testAddEnv() {
		ApplicationContext ctx = new GenericApplicationContext();
		StandardEnvironment env = (StandardEnvironment) ctx.getEnvironment();
		MutablePropertySources propertySources = env.getPropertySources();
		Properties properties = new Properties();
		properties.setProperty("testKey", "testValue");
		propertySources.addLast(new PropertiesPropertySource("my-properties", properties));
		System.out.println(env.getProperty("testKey"));
	}

	/**
	 * 可以使用 @PropertySource 注解来直接加载配置文件
	 * chaoyue.study.config.MyProperties
	 */
	@Test
	public void testAnnotation(){
		ApplicationContext ctx = new AnnotationConfigApplicationContext("chaoyue.study");
		Environment env = ctx.getEnvironment();
		System.out.println(env.getProperty("testname"));
	}
}
