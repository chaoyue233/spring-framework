package chaoyue.study.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * BeanFactoryPostProcessor 会在BeanFactory创建完成之后被调用
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userEncrypt");
		MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
		PropertyValue value = propertyValues.getPropertyValue("name");
		if (value != null) {
			TypedStringValue name = (TypedStringValue) value.getValue();
			Base64.Decoder decoder = Base64.getDecoder();
			propertyValues.addPropertyValue("name", new String(decoder.decode(name.getValue())));
		}
	}
}
