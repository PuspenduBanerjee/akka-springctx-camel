package system;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

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
     * Actor system singleton for this application.
     */
//    @Bean
//    public ActorSystem actorSystem() {
//        ActorSystem system = ActorSystem.create("AkkaSpring");
//        // initialize the application context in the Akka Spring Extension
//        SpringExtProvider.get(system).initialize(applicationContext);
//        return system;
//    }

    /**
     * Actor system singleton for this application.
     */
    @Bean
    @Scope("prototype")
    public ActorSystem actorSystem(String name, Config config) {
        ActorSystem system = ActorSystem.create(name, config);
        // initialize the application context in the Akka Spring Extension
        SpringExtProvider.get(system).initialize(applicationContext);
        return system;
    }

}