package management.sttock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SttockApplication {

	public static void main(String[] args) {
		SpringApplication.run(SttockApplication.class, args);

	}

}
