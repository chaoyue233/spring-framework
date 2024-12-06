package chaoyue.study.beans;

import org.springframework.stereotype.Component;

@Component
public class UserEncrypt {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserEncrypt{" +
				"name='" + name + '\'' +
				'}';
	}
}
