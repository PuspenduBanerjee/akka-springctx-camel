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
package system;

import com.typesafe.config.Config;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Properties;

/**
 * Created by puspendu on 9/10/16.
 */

public class ActorSystemProperties extends PropertiesPropertySource {
    public static final String NAME = "actorSystemName";
    public static final String CONFIG = "config";
    private static final String propertySourceName = "actorSystemPropertySource";


    private ActorSystemProperties(String name) {
        this(name, null, null, null);
    }

    public ActorSystemProperties(String name, String actorSystemName, Config config, Properties addtionalProperties) {
        super(name,addtionalProperties);
        source.put(NAME,actorSystemName);
        source.put(CONFIG,config);
    }

    public ActorSystemProperties(String actorSystemName, Config config,  Properties addtionalProperties) {
        this(propertySourceName, actorSystemName, config, addtionalProperties);
    }

}
