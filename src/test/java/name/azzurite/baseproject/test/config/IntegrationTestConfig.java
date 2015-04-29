package name.azzurite.baseproject.test.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@ComponentScan({
		"name.azzurite.baseproject.test.config.base",
		"name.azzurite.baseproject.test.config.integration",
		"name.azzurite.baseproject.web.rest"
})
@EntityScan("name.azzurite.baseproject.domain.entity")
@EnableJpaRepositories("name.azzurite.baseproject.domain.repository")
public class IntegrationTestConfig {

}
