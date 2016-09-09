package system;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;

/**
 * Created by 258265 on 9/9/2016.
 */
public class PrototypeActorSystem {
    private ActorSystem actorSystem;

    public PrototypeActorSystem(ActorSystem actorSystem) {
        this.actorSystem = actorSystem;
    }

    public ActorSystem getActorSystem() {
        return actorSystem;
    }
}
