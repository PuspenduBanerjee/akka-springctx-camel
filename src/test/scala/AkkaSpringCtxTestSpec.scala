import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import akka.camel.{CamelMessage, _}
import akka.testkit.{ImplicitSender, TestActors, TestKit, TestKitBase, TestProbe}
import com.typesafe.config.ConfigFactory
import org.apache.camel.builder.RouteBuilder
import org.scalatest._
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import actors.EchoActor
import system.SpringExtension._
import akka.pattern._
import system.SpringContextActorSystemProvider

import scala.concurrent.duration._
import scala.reflect.ClassTag

/**
  * Created by puspendu on 9/6/16.
  */
class AkkaSpringCtxTestSpec extends TestKit(SpringContextActorSystemProvider.create())
 with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll{

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "An Echo actor created as Spring UntypedActor" must {
    "send back messages unchanged" in {
      val echoActor=system.actorOf(
        SpringExtProvider.get(system).props("EchoActor"),"echoActor")
      echoActor ! "hello world"
      expectMsg("hello world")
      val camel = CamelExtension(system)
      camel.context.addRoutes(new CustomRouteBuilder(system, echoActor))
    }

  }

}


class CustomRouteBuilder(system: ActorSystem, echoActor: ActorRef)
  extends RouteBuilder {
  def configure {
    from("direct:testEP")
      .routeId("test-route")
      .to(echoActor)

  }
}
