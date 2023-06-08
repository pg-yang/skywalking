/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.recevier.configuration.discovery;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AgentConfigurationsReaderTest {
    @Test
    public void testReadAgentConfigurations() {
        AgentConfigurationsReader reader = new AgentConfigurationsReader(
            this.getClass().getClassLoader().getResourceAsStream("agent-dynamic-configuration.yml"));

        Map<String, AgentConfigurations> configurationCache = reader.readAgentConfigurationsTable()
                                                                    .getAgentConfigurationsCache();
        Assertions.assertEquals(2, configurationCache.size());
        AgentConfigurations agentConfigurations0 = configurationCache.get("serviceA");
        Assertions.assertEquals("serviceA", agentConfigurations0.getService());
        Assertions.assertEquals(2, agentConfigurations0.getConfiguration().size());
        Assertions.assertEquals("1000", agentConfigurations0.getConfiguration().get("trace.sample_rate"));
        Assertions.assertEquals(
            "/api/seller/seller/*", agentConfigurations0.getConfiguration().get("trace.ignore_path"));
        Assertions.assertEquals(
            "faa43ed870531970966dd533f4d68ec96adffa65405a0d86c4eaad431b93c2f97f98b31a532105fa7199719a3b276b0e890928174945032d8d804373a81cd21b",
            agentConfigurations0.getUuid()
        );

        AgentConfigurations agentConfigurations1 = configurationCache.get("serviceB");
        Assertions.assertEquals("serviceB", agentConfigurations1.getService());
        Assertions.assertEquals(2, agentConfigurations1.getConfiguration().size());
        Assertions.assertEquals("1000", agentConfigurations1.getConfiguration().get("trace.sample_rate"));
        Assertions.assertEquals(
            "/api/seller/seller/*", agentConfigurations1.getConfiguration().get("trace.ignore_path"));
        Assertions.assertEquals(
            "faa43ed870531970966dd533f4d68ec96adffa65405a0d86c4eaad431b93c2f97f98b31a532105fa7199719a3b276b0e890928174945032d8d804373a81cd21b",
            agentConfigurations0.getUuid()
        );
    }

    @Test
    public void testReadAgentDefaultConfigurations() {
        AgentConfigurationsReader reader = new AgentConfigurationsReader(
            this.getClass().getClassLoader().getResourceAsStream("agent-dynamic-default-configuration.yml"));

        Map<String, AgentConfigurations> configurationCache = reader.readAgentConfigurationsTable()
                                                                    .getAgentConfigurationsCache();
        Assertions.assertEquals(2, configurationCache.size());
        AgentConfigurations agentConfigurations0 = configurationCache.get("serviceA");
        Assertions.assertEquals("serviceA", agentConfigurations0.getService());
        Assertions.assertEquals(3, agentConfigurations0.getConfiguration().size());
        Assertions.assertEquals("5", agentConfigurations0.getConfiguration().get("trace.sample_rate"));
        Assertions.assertEquals(
            "/api/seller/seller/*", agentConfigurations0.getConfiguration().get("trace.ignore_path"));
        Assertions.assertEquals(
            ".gif,jpg", agentConfigurations0.getConfiguration().get("trace.ignore_suffix"));
        Assertions.assertEquals(
            "840131d861d8cf4d83e48dae3929184764fd56dd3c992771f34cfafdca5cc485ed31963a0c15788ba4fe3e165fc04f2dfc76ba315a4932291db1b03460a509be",
            agentConfigurations0.getUuid()
        );

        AgentConfigurations agentConfigurations1 = configurationCache.get("serviceB");
        Assertions.assertEquals("serviceB", agentConfigurations1.getService());
        Assertions.assertEquals(2, agentConfigurations1.getConfiguration().size());
        Assertions.assertEquals("1000", agentConfigurations1.getConfiguration().get("trace.sample_rate"));
        Assertions.assertEquals(
            "/api/test/*", agentConfigurations1.getConfiguration().get("trace.ignore_path"));
        Assertions.assertEquals(
            "840131d861d8cf4d83e48dae3929184764fd56dd3c992771f34cfafdca5cc485ed31963a0c15788ba4fe3e165fc04f2dfc76ba315a4932291db1b03460a509be",
            agentConfigurations0.getUuid()
        );
    }
}
