package chaoyue.study.test.tool;

import chaoyue.study.beans.Dog;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring中常用的反射工具
 */
public class ReflectTest {
	private HashMap<Integer, List<String>> myMap;
	private final Logger log = LoggerFactory.getLogger(ReflectTest.class);

	/**
	 * Introspector 内省API
	 * BeanInfo：Bean信息的封装，里面包含了属性和方法
	 * PropertyDescriptor：属性的封装，主要是属性的名称、类型、和读写方法（方便进行调用）
	 */
	@Test
	public void testIntrospector() throws Exception {
		// 根据类型获取Bean的信息
		BeanInfo beanInfo = Introspector.getBeanInfo(Dog.class);
		// 获取Bean的描述
		BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
		log.info("beanDescriptor -> {}", beanDescriptor);
		// 获取属性的描述
		PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor descriptor : descriptors) {
			log.info("name -> {}", descriptor.getName());
			log.info("readMethod -> {}", descriptor.getReadMethod());
			log.info("writeMethod -> {}", descriptor.getWriteMethod());
			log.info("descriptorString -> {}", descriptor.getPropertyType().descriptorString());
		}
	}

	/**
	 * 通过PropertyDescriptor可以很方便的通过反射进行属性的获取和设置
	 * 用于自动装配
	 */
	@Test
	public void testPropertyDescriptor() throws Exception {
		Dog dog = new Dog();
		// 直接创建属性描述符
		PropertyDescriptor propertyDescriptor = new PropertyDescriptor("color", Dog.class);
		Method writeMethod = propertyDescriptor.getWriteMethod();
		writeMethod.invoke(dog, "black");
		log.info("dog -> {}", dog);
		Method readMethod = propertyDescriptor.getReadMethod();
		Object result = readMethod.invoke(dog);
		log.info("result -> {}", result);
	}

	/**
	 * ResolvableType：对属性/方法的封装，用于获取泛型和超类信息
	 */
	@Test
	public void testResolvableType() throws Exception {
		// 通过Filed生成ResolvableType（所以ResolvableType也是对属性/方法的封装，只不过用来处理泛型和超类）
		ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
		// 获取超类 返回的也是ResolvableType
		log.info("superType -> {}", t.getSuperType()); // AbstractMap<Integer, List<String>>
		log.info("asMap -> {}", t.asMap()); // Map<Integer, List<String>>
		// 获取泛型
		log.info("generic-0 -> {}", t.getGeneric(0).resolve()); // Integer
		log.info("generic-1 -> {}", t.getGeneric(1).resolve()); // List
		t.getGeneric(1); // List<String>
		t.resolveGeneric(1, 0); // String
	}

	/**
	 * BeanWrapper：bean的包装类，方便进行属性填充。属性填充前会先进行实例创建
	 */
	@Test
	public void testBeanWrapper() throws Exception {
		// 通过BeanWrapper设置属性（会先实例化对象，再进行属性设置）
		BeanWrapper beanWrapper = new BeanWrapperImpl(Dog.class);
		beanWrapper.setPropertyValue("color", "red");
		beanWrapper.setPropertyValue("age", 10);
		log.info("bean ->{}", beanWrapper.getWrappedInstance());
		// 通过BeanWrapper使用map批量设置属性
		Map<String, Object> paramMap = Map.of("color", "green", "age", 20);
		beanWrapper.setPropertyValues(paramMap);
		log.info("bean ->{}", beanWrapper.getWrappedInstance());
		// 通过MutablePropertyValues批量设置属性
		// MutablePropertyValues在BeanDefinition中就有
		MutablePropertyValues pvs = new MutablePropertyValues();
		pvs.addPropertyValue("color", "yellow");
		pvs.addPropertyValue("age", 30);
		beanWrapper.setPropertyValues(pvs);
		log.info("bean ->{}", beanWrapper.getWrappedInstance());
	}

	/**
	 * 反射工具和BeanDefinition组合使用的例子
	 * 通过xml方式获取BeanDefinition
	 * 通过beanWrapper进行对象创建和属性填充
	 */
	@Test
	public void testBatchCreate() throws Exception {
		// 通过解析xml的方式读取BeanDefinition
		BeanDefinitionRegistry registry = new SimpleBeanDefinitionRegistry();
		BeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(registry);
		beanDefinitionReader.loadBeanDefinitions("bean.xml");
		// 通过BeanDefinition生成对象
		String[] beanDefinitionNames = registry.getBeanDefinitionNames();
		for (String beanDefinitionName : beanDefinitionNames) {
			log.info("beanDefinitionName -> {}", beanDefinitionName);
			BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
			// 使用class 生成BeanWrapper
			Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
			BeanWrapper beanWrapper = new BeanWrapperImpl(beanClass);
			// 添加类型转化服务(这边不能用lambda表达式，泛型会丢导致出错)
			// xml中读取的是TypedStringValue，需要通过转化器进行转化
			DefaultConversionService conversionService = new DefaultConversionService();
			conversionService.addConverter(new Converter<TypedStringValue, String>() {
				@Override
				public String convert(TypedStringValue source) {
					return source.getValue();
				}
			});
			conversionService.addConverter(new Converter<TypedStringValue, Integer>() {
				@Override
				public Integer convert(TypedStringValue source) {
					return Integer.parseInt(source.getValue());
				}
			});
			beanWrapper.setConversionService(conversionService);
			// 实例化和属性填充
			beanWrapper.setPropertyValues(beanDefinition.getPropertyValues());
			log.info("bean -> {}", beanWrapper.getWrappedInstance());
		}
	}
}
