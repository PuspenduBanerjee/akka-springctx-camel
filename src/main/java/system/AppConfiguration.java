/*-
 * #%L
 * akka-springctx-scala-camel
 * %%
 * Copyright (C) 2016 PuspenduBanerjee
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package system;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import static system.SpringExtension.SpringExtProvider;

/**
 * The application configuration.
 */
@Configuration
@ImportResource("classpath*:**/applicationContext.xml")
@PropertySource(value="classpath*:**/applicationContext.properties",ignoreResourceNotFound=true)
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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}