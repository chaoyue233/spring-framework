package chaoyue.study.test.container;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class ApplicationContextTest {
	private final Logger log = LoggerFactory.getLogger(ApplicationContextTest.class);

	/**
	 * 创建ApplicationContext其实有新老两套api
	 * 新版本统一了从类路径加载和绝对路径加载两种方式，使用解析器去判断通过那种方式去加载
	 * Web环境也有自己的上下文，用来管理tomcat和mvc相关的类
	 */
	@Test
	public void testGetApplicationContext() {
		// 老版本
		ApplicationContext ctx1 = new ClassPathXmlApplicationContext("bean.xml");
		log.info("ctx1 -> {}", ctx1.getBean("user"));
		ApplicationContext ctx2 = new FileSystemXmlApplicationContext("/src/main/resources/bean.xml");
		log.info("ctx2 -> {}", ctx2.getBean("user"));
		// 新版本
		ApplicationContext ctx3 = new GenericXmlApplicationContext("bean.xml");
		log.info("ctx3 -> {}", ctx3.getBean("user"));
	}

	@Test
	public void testAnnotationConfigApplicationContext(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("chaoyue.study");
		log.info("user -> {}", ctx.getBean("user"));
		log.info("user1 -> {}",ctx.getBean("user1"));
		// 容器启动和容器关闭可能不是同一个线程触发的
		ctx.close();
	}

	@Test
	public void testEarlyApplicationListener() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		// 添加监听器（早期事件）
		ctx.addApplicationListener(event -> {
			System.out.println("监听到事件：" + event);
		});
		// 加载资源
		ctx.load("bean.xml");
		// 手动刷新容器
		ctx.refresh();
		System.out.println(ctx.getBean("user"));
	}

	@Test
	public void testFactoryBean(){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("chaoyue.study");
		// 获取factoryBean生产的bean
		log.info("user1 -> {}",ctx.getBean("user1"));
		// 获取factoryBean本身
		log.info("user1 -> {}",ctx.getBean("&user1"));
	}
}
