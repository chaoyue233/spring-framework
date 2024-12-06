package chaoyue.study.test;

import chaoyue.study.service.UserService;
import chaoyue.study.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

public class AopTest {

	@Test
	public void testProxyFactory() {
		// 原始对象
		UserService userService = new UserServiceImpl();

		MethodBeforeAdvice advice = (method, args, target) -> {
			System.out.println("前置通知");
			method.invoke(target, args);
		};


		// 代理工厂 创建代理对象
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(userService);
		proxyFactory.addAdvice(advice);
		proxyFactory.addInterface(UserService.class);
		Object proxy = proxyFactory.getProxy();
		System.out.println(proxy);

		// 通过代理对象调用方法
		UserService proxyObject = (UserService) proxy;
		proxyObject.addUser();
	}
}
