package medalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "medalert")
@ComponentScan(basePackages = "medalert")
@EntityScan(basePackages = "medalert.model")
@EnableJpaRepositories(basePackages = "medalert.repository")
public class MedalertApplication implements CommandLineRunner{

	public static void main(String[] args) {

		SpringApplication.run(MedalertApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        System.out.println("L'application Medalert est lancée avec succès !");
    }
}

