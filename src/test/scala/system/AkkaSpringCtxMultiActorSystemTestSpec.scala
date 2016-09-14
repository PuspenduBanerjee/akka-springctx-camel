package system

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
import java.util.Properties

import actors.EchoActor
import akka.actor.Props
import akka.testkit.TestProbe
import com.typesafe.config.ConfigFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._

/**
  * Created by 258265 on 9/9/2016.
  */
class AkkaSpringCtxMultiActorSystemTestSpec extends FlatSpec with Matchers {
  behavior of "akka-springctx-camel"
  it should "be able to create multiple instances of ActorSystem" in {
    val actorSystems = 2551 to 2552 map (x => {
      val as = SpringContextActorSystemProvider.create("ActorSystem" + x,
        ConfigFactory.parseString("akka.remote.netty.tcp.port=" + x).withFallback(ConfigFactory.load()))
      val echoActor = as.actorOf(Props[EchoActor])
      val probe = new TestProbe(as, "probe")
      val msg = "Hi There"
      echoActor tell(msg, probe.ref)
      probe.expectMsg(1000 millis, msg)
      as
    })

    actorSystems.foreach(x => {
      x.terminate()
    })
  }

  it should "be able to evaluate spring & camel placeholder properties" in {
    val actorSystems = 2551 to 2552 map (x => {
      val additionalProperties=new Properties()
      val instanceName="INSTANCE-"+x
      additionalProperties.setProperty("instance.name",instanceName)
      val as = SpringContextActorSystemProvider.create("ActorSystem" + x,
        ConfigFactory.parseString("akka.remote.netty.tcp.port=" + x).withFallback(ConfigFactory.load())
      ,additionalProperties)
      val resolvedInstanceName=SpringExtension.SpringExtProvider.get(as)
        .applicationContext.getBean("propertyPlaceHolderTestBean").toString
      resolvedInstanceName should ===(instanceName)
      as
    })

    actorSystems.foreach(x => {
      x.terminate()
    })
  }
}
