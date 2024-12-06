package chaoyue.study;

import chaoyue.study.beans.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloSpring {
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new AnnotationConfigApplicationContext("chaoyue.study");
		Dog bean = applicationContext.getBean(Dog.class);
		bean.setAge(20);
		System.out.println(bean);
		System.out.println(bean.getAge());
	}
}
