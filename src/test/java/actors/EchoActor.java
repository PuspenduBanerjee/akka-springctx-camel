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