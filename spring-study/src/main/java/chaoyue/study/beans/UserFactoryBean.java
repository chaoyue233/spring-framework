package chaoyue.study.beans;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component("user1")
public class UserFactoryBean implements FactoryBean<User> {

	/**
	 * 返回一个具体的bean实例，该实例会被注册到容器中
	 * 以这种编程的方式注入可以处理复杂bean的创建
	 * 注解@Bean也可以实现类似的效果，但是@Bean适合处理我们自己的复杂业务bean
	 * FactoryBean适合用来封装给第三方使用（第三方只要决定是否注入FactoryBean就可以决定是否开启功能）
	 */
	@Override
	public User getObject() throws Exception {
		User user = new User();
		user.setName("由FactoryBean创建的User");
		user.setAge(233);
		return user;
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}
}
