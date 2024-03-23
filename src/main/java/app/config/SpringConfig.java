package app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("app")
@EnableJpaRepositories(basePackages = {"app"})
@EntityScan("app")
@EnableAutoConfiguration
@PropertySource("classpath:application.properties")
public class SpringConfig {

}
