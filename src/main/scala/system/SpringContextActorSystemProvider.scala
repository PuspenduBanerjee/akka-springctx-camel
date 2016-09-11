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
