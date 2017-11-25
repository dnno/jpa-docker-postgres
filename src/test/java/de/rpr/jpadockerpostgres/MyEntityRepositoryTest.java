package de.rpr.jpadockerpostgres;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import de.rpr.jpadockerpostgres.repository.MyEntityRepository;
import de.rpr.jpadockerpostgres.support.PostgresHealthChecks;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JpaDockerPostgresApplication.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyEntityRepositoryTest {

  @Autowired
  ApplicationContext applicationContext;
  @Autowired
  MyEntityRepository repository;


  @ClassRule
  public static DockerComposeRule docker = DockerComposeRule.builder()
      .file("src/test/resources/docker-compose.yml")
      .waitingForService("postgres", HealthChecks.toHaveAllPortsOpen())
      .waitingForService("postgres", container -> PostgresHealthChecks.canConnectTo(container, "dbuser", "dbpassword", "custom"))
      .build();


  @Test
  public void context_should_load() {
    assertThat(applicationContext).isNotNull();
  }
}
