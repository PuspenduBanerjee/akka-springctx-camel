package system

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext


/**
  * Created by puspendu on 9/7/16.
  */

object SpringContextActorSystemProvider {
  val rootContext = new GenericApplicationContext()
  rootContext.refresh()

  def create(name: String = "AkkaSpring", config: Config = ConfigFactory.load) = {
    val ctx = new AnnotationConfigApplicationContext()
    ctx.setParent(rootContext)
    val propertySource = new ActorSystemProperties(name, config);
    ctx.getEnvironment.getPropertySources.addLast(propertySource)
    ctx.scan("system", "actors")
    ctx.refresh()
    ctx.getBean(classOf[ActorSystem])
  }

}
