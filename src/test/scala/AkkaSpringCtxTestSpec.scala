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
