package de.rpr.jpadockerpostgres;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import de.rpr.jpadockerpostgres.repository.MyEntityRepository;
import de.rpr.jpadockerpostgres.support.PortMappingInitializer;
import de.rpr.jpadockerpostgres.support.PostgresHealthChecks;
import de.rpr.jpadockerpostgres.support.PropagateDockerRule;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(
    classes = JpaDockerPostgresApplication.class,
    initializers = PortMappingInitializer.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyEntityRepositoryTest {

  @Autowired
  ApplicationContext applicationContext;
  @Autowired
  MyEntityRepository repository;


  private static DockerComposeRule docker = DockerComposeRule.builder()
      .file("src/test/resources/docker-compose.yml")
      .waitingForService("postgres", HealthChecks.toHaveAllPortsOpen())
      .waitingForService("postgres", PostgresHealthChecks::canConnectTo)
      .build();

  @ClassRule
  public static TestRule exposePortMappings = RuleChain.outerRule(docker)
      .around(new PropagateDockerRule(docker));

  @Test
  public void context_should_load() {
    assertThat(applicationContext).isNotNull();
  }

}
