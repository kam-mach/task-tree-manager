package pl.springnauka.Tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TasksApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TasksApplication.class, args);

	}

}
