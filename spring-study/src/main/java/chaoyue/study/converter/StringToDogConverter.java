package chaoyue.study.converter;

import chaoyue.study.beans.Dog;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义转化器
 * 这边省去一些复杂的校验，仅做简单的功能实现
 */
public class StringToDogConverter implements Converter<String, Dog> {

	// "red|12" -> Dog(color=red,age=12)
	@Override
	public Dog convert(String source) {
		if (source.contains("|")) {
			String[] split = source.split("\\|");
			Dog dog = new Dog();
			dog.setColor(split[0]);
			dog.setAge(Integer.parseInt(split[1]));
			return dog;
		}
		throw new RuntimeException("类型转换异常,source:" + source);
	}
}
