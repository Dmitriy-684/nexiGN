package app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * A configuration class that specifies in which directory the beans for Spring container will be scanned,
 * in which directory JpaRepositories will be scanned
 * and from which file the key values for the project will be taken
 */

@ComponentScan("app")
@EnableJpaRepositories(basePackages = {"app"})
@EntityScan("app")
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class SpringConfig {

}
