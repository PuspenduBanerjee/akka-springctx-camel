package system;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;

import static system.SpringExtension.SpringExtProvider;

/**
 * The application configuration.
 */
@Configuration
@ImportResource("classpath*:**/applicationContext.xml")
class AppConfiguration {

    // the application context is needed to initialize the Akka Spring Extension
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Actor system Singleton per ApplicationContext for this application.
     */
    @Bean
    public ActorSystem actorSystem(ApplicationContext applicationContext) {
        Environment environment = applicationContext.getEnvironment();
        final String name = environment.getProperty(ActorSystemProperties.NAME);
        final Config config = environment.getProperty(ActorSystemProperties.CONFIG, Config.class);
        ActorSystem system = ActorSystem.create(name, config);
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

}