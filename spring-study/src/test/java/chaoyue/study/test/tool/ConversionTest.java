package chaoyue.study.test.tool;

import chaoyue.study.beans.Dog;
import chaoyue.study.converter.StringToDogConverter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.List;

public class ConversionTest {
	private final Logger log = LoggerFactory.getLogger(ConversionTest.class);

	/**
	 * DefaultConversionService：默认的类型转化服务
	 * 里面包含了大量常用的Converter
	 */
	@Test
	public void testConversionService() {
		String source = "123";
		ConversionService service = new DefaultConversionService();
		boolean canConvert = service.canConvert(source.getClass(), Integer.class);
		if (canConvert) {
			Integer result = service.convert(source, Integer.class);
			log.info("result:{}", result);
		}
	}

	/**
	 * DefaultConversionService中默认就包含了字符串转List的转化器
	 */
	@Test
	public void testConvertList() {
		String source = "1,2,3,4,5";
		ConversionService service = new DefaultConversionService();
		List<Integer> target = service.convert(source, List.class);
		log.info("target:{}", target);
	}

	/**
	 * 添加自定义转化器
	 */
	@Test
	public void testConvertCustom() {
		String source = "yellow|23";
		GenericConversionService service = new GenericConversionService();
		service.addConverter(new StringToDogConverter());
		Dog target = service.convert(source, Dog.class);
		log.info("target:{}", target);
	}
}
