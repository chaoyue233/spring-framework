package chaoyue.study.beans;

import org.springframework.stereotype.Component;

@Component
public class TeddyDog extends Dog{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "TeddyDog{" +
				"name='" + name + '\'' +
				'}';
	}
}
