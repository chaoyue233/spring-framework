package chaoyue.study.test.tool;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
	@Test
	public void test() throws Exception{
		// 模拟spring中的bootstrap Executor
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		// 模拟spring中的bean
		String[] beanNames = new String[]{"lily", "teddyDog", "jerry", "tom"};
		List<CompletableFuture<Void>> futures = new ArrayList<>();
		for (String beanName : beanNames) {
			Runnable task = () -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(beanName + " is created.");
			};
			CompletableFuture<Void> future = CompletableFuture.runAsync(task, threadPool);
			futures.add(future);
		}
		CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
		System.out.println("所有的bean被实例化后做的事情");
		Thread.sleep(3000);
	}
}
