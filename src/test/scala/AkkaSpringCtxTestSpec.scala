import akka.actor.{ActorRef, ActorSystem, Props}
import akka.camel._
import akka.testkit.TestActors.EchoActor
import akka.testkit.{ImplicitSender, TestKit}
import org.apache.camel.ExchangePattern
import org.apache.camel.builder.RouteBuilder
import org.scalatest._
import system.SpringContextActorSystemProvider
import system.SpringExtension._

/**
  * Created by puspendu on 9/6/16.
  */
class AkkaSpringCtxTestSpec extends TestKit(SpringContextActorSystemProvider.create("TestAkkaSpring"))
  with FlatSpecLike
  with ImplicitSender with Matchers with BeforeAndAfterAll {

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Spring-Akka Context" should
    "be able to create Spring UntypedActor[EchoActor] which send back messages unchanged" in {
    val echoActor = system.actorOf(
      SpringExtProvider.get(system).props("EchoActor"), "echoActor")
    echoActor ! "hello world"
    expectMsg("hello world")

  }

  it should "provide a SpringCamelContext where camel endpoint will be able to exchange msg with an actor[EchoActor]" in {
    val camel = CamelExtension(system)
    camel.context.addRoutes(new CustomRouteBuilder(system, system.actorOf(Props[EchoActor])))
    val msg = "Hi There"
    val response = camel.context.createProducerTemplate().sendBody("direct:testEP", ExchangePattern.InOut, msg)
    msg should ===(response)
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
