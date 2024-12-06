package chaoyue.study.beans;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class AfterSingletonsInstantiatedDemo implements SmartInitializingSingleton {
	@Override
	public void afterSingletonsInstantiated() {
		System.out.println("所有的非懒加载单利bean实例化完成！");
	}
}
