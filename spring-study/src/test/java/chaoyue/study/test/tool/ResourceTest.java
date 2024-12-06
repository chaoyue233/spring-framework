package chaoyue.study.test.tool;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ResourceTest {
	private final Logger log = LoggerFactory.getLogger(ResourceTest.class);

	/**
	 * PathMatchingResourcePatternResolver 可以解析资源路径并加载资源
	 * classpath: 只在当前项目的类路径下找
	 * classpath*: 会去当前项目和其依赖的所有jar包的类路径下找
	 */
	@Test
	public void testPathMatchingResourcePatternResolver() throws Exception {

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath:META-INF/spring.handlers");
		log.info("resource.length ->{}", resources.length);
		resources = resolver.getResources("classpath*:META-INF/spring.handlers");
		log.info("resource.length ->{}", resources.length);
	}

	/**
	 * ResourceLoader：资源加载器
	 * 可以从网络上获取资源，甚至可以实现自己的协议解析器去获取资源
	 */
	@Test
	public void testResourceLoader() throws Exception {
		DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource("https://spring.io/");
		log.info("resource:{}", resource.getURI());
	}
}
