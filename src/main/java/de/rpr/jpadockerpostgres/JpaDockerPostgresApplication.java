package de.rpr.jpadockerpostgres;

import de.rpr.jpadockerpostgres.repository.MyEntityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = MyEntityRepository.class)
public class JpaDockerPostgresApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaDockerPostgresApplication.class, args);
	}
}
