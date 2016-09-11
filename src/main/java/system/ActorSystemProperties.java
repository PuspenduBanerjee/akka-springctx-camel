package system;

import com.typesafe.config.Config;
import org.springframework.core.env.PropertySource;

/**
 * Created by puspendu on 9/10/16.
 */

public class ActorSystemProperties extends PropertySource {
    public static final String NAME = "actorSystemName";
    public static final String CONFIG = "config";
    private static final String propertySourceName = "actorSystemPropertySource";
    private final String actorSystemName;
    private Config config;


    private ActorSystemProperties(String name) {
        this(name, null, null);
    }

    public ActorSystemProperties(String name, String actorSystemName, Config config) {
        super(name);
        this.actorSystemName = actorSystemName;
        this.config = config;
    }

    public ActorSystemProperties(String actorSystemName, Config config) {
        this(propertySourceName, actorSystemName, config);
    }

    public String getActorSystemName() {
        return actorSystemName;
    }


    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Object getProperty(String s) {
        switch (s) {
            case CONFIG:
                return config;
            case NAME:
                return actorSystemName;
        }
        return null;
    }

}
