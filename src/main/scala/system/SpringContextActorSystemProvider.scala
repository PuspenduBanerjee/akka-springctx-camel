package system

import akka.actor.ActorSystem
import org.springframework.context.annotation.AnnotationConfigApplicationContext
;

/**
 * Created by puspendu on 9/7/16.
 */

object SpringContextActorSystemProvider{
    def create:ActorSystem = {
        val ctx = new AnnotationConfigApplicationContext()
        ctx.scan("system","actors")
        ctx.refresh()
        ctx.getBean(classOf[ActorSystem])
    }

}
