package chaoyue.study.test.container;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.CglibSubclassingInstantiationStrategy;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryTest {
	private final Logger log = LoggerFactory.getLogger(BeanFactoryTest.class);

	/**
	 * DefaultListableBeanFactory 功能比较全的一个BeanFactory实现类
	 */
	@Test
	public void testBeanFactory() {
		// 创建beanFactory
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		// 解析BeanDefinition 并注册到beanFactory中（beanFactory实现了BeanDefinitionRegistry）
		BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		// 通过解析xml的方式读取BeanDefinition
		beanDefinitionReader.loadBeanDefinitions("bean.xml");
		// 从beanFactory获取bean
		log.info("getUser ->{}", beanFactory.getBean("user"));
	}

	/**
	 * beanFactory可以指定对应的实例化策略进行实例化
	 * SimpleInstantiationStrategy：使用反射的方式进行实例化
	 * CglibSubclassingInstantiationStrategy：使用cglib的方式进行实例化(是上面的子类)
	 * 也可以自己实现InstantiationStrategy进行实例化
	 * 默认使用的是CglibSubclassingInstantiationStrategy，因为子类可以调用父类的方法（优先调用父类的实例化方式，父类处理不了再使用子类）
	 */
	@Test
	public void testInstantiationStrategy() throws Exception{
		// 创建Bean工厂，加载BeanDefinition
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		beanDefinitionReader.loadBeanDefinitions("bean.xml");

		// 将GenericBeanDefinition转化成RootBeanDefinition
		String beanName = "user";
		BeanDefinition beanDefinition =  beanFactory.getBeanDefinition(beanName);
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
		rootBeanDefinition.overrideFrom(beanDefinition);
		rootBeanDefinition.resolveBeanClass(this.getClass().getClassLoader());

		// 使用实例化策略来手动实例化一个bean
		CglibSubclassingInstantiationStrategy strategy = new CglibSubclassingInstantiationStrategy();
		// debug可以看到 这边是直接调用的SimpleInstantiationStrategy的方法
		Object instantiate = strategy.instantiate(rootBeanDefinition, beanName, beanFactory);
		log.info("user:{}", instantiate);
	}

	/**
	 * BeanFactoryPostProcessor 会在BeanFactory创建完成之后被调用
	 * 注意这个是ApplicationContext去触发的（在refresh方法中），即ApplicationContext创建BeanFactory完成后，去触发的PostProcessor调用
	 * 这边调用了自定义的BeanFactoryPostProcessor {@link chaoyue.study.processor.MyBeanFactoryPostProcessor}
	 */
	@Test
	public void testBeanFactoryPostProcessor(){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean.xml");
		log.info("userEncrypt ->{}", applicationContext.getBean("userEncrypt"));
	}
}
