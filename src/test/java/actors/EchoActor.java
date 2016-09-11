package actors; /**
 * Created by puspendu on 9/6/16.
 */

import akka.actor.UntypedActor;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;

/**
 * An actor that can count using an injected CountingService.
 *
 * @note The scope here is prototype since we want to create a new actor
 * instance for use of this bean.
 */
@Named("EchoActor")
@Scope("prototype")
public class EchoActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        getSender().tell(message, getSelf());
    }
}