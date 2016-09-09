package system

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.context.annotation.AnnotationConfigApplicationContext

import scala.reflect.ClassTag


/**
 * Created by puspendu on 9/7/16.
 */

object SpringContextActorSystemProvider{
    def create(name:String="AkkaSpring", config:Config=ConfigFactory.load)={
        val ctx = new AnnotationConfigApplicationContext()
        ctx.scan("system","actors")
        ctx.refresh()
        ctx.getBean(classOf[ActorSystem],name,config)
    }

//    def createPrototypeActorSystem={
//        val ctx = new AnnotationConfigApplicationContext()
//        ctx.scan("system","actors")
//        ctx.refresh()
//        ctx.getBean(classOf[PrototypeActorSystem],name,config)
//    }

}
