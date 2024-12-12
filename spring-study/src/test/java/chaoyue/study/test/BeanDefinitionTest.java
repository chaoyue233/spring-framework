package chaoyue.study.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanMetadataAttribute;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

public class BeanDefinitionTest {
	private final Logger log = LoggerFactory.getLogger(BeanDefinitionTest.class);

	/**
	 * GenericBeanDefinition：比较常用的BeanDefinition
	 * MutablePropertyValues：BeanDefinition中封装的成员变量信息
	 */
	@Test
	public void testGenericBeanDefinition() {
		// 手动创建一个BeanDefinition
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName("chaoyue.study.bean.User");
		beanDefinition.setLazyInit(false);
		// 封装成员变量
		MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
		mutablePropertyValues.add("name", "chaoyue");
		mutablePropertyValues.add("age", 20);
		beanDefinition.setPropertyValues(mutablePropertyValues);
		log.info("beanDefinition -> {}", beanDefinition);
	}

	/**
	 * RootBeanDefinition：归一化之后的BeanDefinition
	 * 所有的BeanDefinition最后都会转化为RootBeanDefinition方便进行统一处理
	 */
	@Test
	public void testRootBeanDefinition() {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName("chaoyue.study.bean.User");
		beanDefinition.setLazyInit(false);
		MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
		mutablePropertyValues.add("name", "chaoyue");
		mutablePropertyValues.add("age", 20);
		beanDefinition.setPropertyValues(mutablePropertyValues);

		// 使用overrideFrom快速通过其他的BeanDefinition构建RootBeanDefinition
		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
		rootBeanDefinition.overrideFrom(beanDefinition);

		log.info("rootBeanDefinition -> {}", rootBeanDefinition);
	}

	@Test
	public void testChildBeanDefinition() {
		GenericBeanDefinition dog = new GenericBeanDefinition();
		dog.setBeanClassName("chaoyue.study.bean.Dog");
		BeanMetadataAttribute color = new BeanMetadataAttribute("color", "yellow");
		BeanMetadataAttribute age = new BeanMetadataAttribute("age", 10);
		dog.addMetadataAttribute(color);
		dog.addMetadataAttribute(age);

		// 子BeanDefinition依赖父BeanDefinition
		ChildBeanDefinition teddy = new ChildBeanDefinition("dog");
		teddy.setBeanClassName("chaoyue.study.bean.TeddyDog");
		BeanMetadataAttribute name = new BeanMetadataAttribute("name", "Tom");
		teddy.addMetadataAttribute(name);
	}

	/**
	 * BeanDefinitionRegistry：管理BeanDefinition的容器
	 * 本质上就是Map，用来存放所有的BeanDefinition，key为beanName
	 */
	@Test
	public void testBeanDefinitionRegistry() {
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName("chaoyue.study.bean.User");
		beanDefinition.setLazyInit(false);
		MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
		mutablePropertyValues.add("name", "chaoyue");
		mutablePropertyValues.add("age", 20);
		beanDefinition.setPropertyValues(mutablePropertyValues);

		// 将BeanDefinition注册到BeanDefinitionRegistry中
		// BeanDefinitionRegistry中管理的是BeanDefinition（Bean的元数据）
		// BeanFactory中管理的是Bean的实例
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		registry.registerBeanDefinition("user", beanDefinition);
	}

	/**
	 * BeanDefinitionReader：读取BeanDefinition的工具（比如通过xml，通过注解等）
	 * 读取后的BeanDefinition会被放入到BeanDefinitionRegistry中
	 * XmlBeanDefinitionReader：通过读取xml文件生成BeanDefinition
	 */
	@Test
	public void testXmlBeanDefinitionReader() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		// 构建Reader需要一个registry 用来存放解析后的BeanDefinition
		BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(registry);
		// 通过解析xml的方式读取BeanDefinition
		beanDefinitionReader.loadBeanDefinitions("bean.xml");
		log.info("beanDefinitionCount -> {}", registry.getBeanDefinitionCount());
	}

	/**
	 * ClassPathBeanDefinitionScanner：通过包扫描生成BeanDefinition
	 * 包扫描扫的是class文件，通过字节码操作技术去解析类元信息
	 */
	@Test
	public void testClassPathBeanDefinitionScanner() {
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
		// 通过包扫描的方式读取BeanDefinition
		scanner.scan("chaoyue.study.beans");
		log.info("beanDefinitionCount -> {}", registry.getBeanDefinitionCount());
	}
}
