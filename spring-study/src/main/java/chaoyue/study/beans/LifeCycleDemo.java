package chaoyue.study.beans;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleDemo implements Lifecycle {
	@Override
	public void start() {
		System.out.println("lifeCycle start...");
	}

	@Override
	public void stop() {
		System.out.println("lifeCycle stop...");
	}

	@Override
	public boolean isRunning() {
		return false;
	}
}
