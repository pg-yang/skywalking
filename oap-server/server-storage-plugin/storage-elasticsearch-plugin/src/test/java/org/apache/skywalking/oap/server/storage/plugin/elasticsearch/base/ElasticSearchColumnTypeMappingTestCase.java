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

package org.apache.skywalking.oap.server.storage.plugin.elasticsearch.base;

import org.apache.skywalking.oap.server.core.analysis.metrics.IntList;
import org.apache.skywalking.oap.server.core.storage.model.ElasticSearchExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

public class ElasticSearchColumnTypeMappingTestCase {
    public List<String> a;

    @Test
    public void test() throws NoSuchFieldException {
        ColumnTypeEsMapping mapping = new ColumnTypeEsMapping();
        int defaultLength = 200;
        boolean defaultStorageOnly = false;

        Assertions.assertEquals("integer", mapping.transform(int.class, int.class, defaultLength, defaultStorageOnly, null));
        Assertions.assertEquals("integer", mapping.transform(Integer.class, Integer.class, defaultLength, defaultStorageOnly, null));

        Assertions.assertEquals("long", mapping.transform(long.class, long.class, defaultLength, defaultStorageOnly, null));
        Assertions.assertEquals("long", mapping.transform(Long.class, Long.class, defaultLength, defaultStorageOnly, null));

        Assertions.assertEquals("double", mapping.transform(double.class, double.class, defaultLength, defaultStorageOnly, null));
        Assertions.assertEquals("double", mapping.transform(Double.class, Double.class, defaultLength, defaultStorageOnly, null));

        Assertions.assertEquals("keyword", mapping.transform(String.class, String.class, defaultLength, defaultStorageOnly, null));
        Assertions.assertEquals("keyword", mapping.transform(String.class, String.class, 100_000, defaultStorageOnly, null));
        Assertions.assertEquals("text", mapping.transform(String.class, String.class, 100_000, true, null));

        final Type listFieldType = this.getClass().getField("a").getGenericType();
        Assertions.assertEquals("keyword", mapping.transform(List.class, listFieldType, defaultLength, defaultStorageOnly,
                                                         new ElasticSearchExtension(null, null, false, false, false)
        ));

        Assertions.assertEquals("keyword", mapping.transform(IntList.class, int.class, defaultLength, defaultStorageOnly,
                                                         new ElasticSearchExtension(null, null, true, false, false)));
        Assertions.assertEquals("text", mapping.transform(IntList.class, int.class, defaultLength, defaultStorageOnly,
                                                         new ElasticSearchExtension(null, null, false, false, false)));
    }
}
