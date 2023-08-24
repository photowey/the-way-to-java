/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.solr.in.action.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.solr.in.action.domain.PageDocument;
import com.photowey.solr.in.action.domain.document.AreaDocument;
import com.photowey.solr.in.action.domain.location.GeographyCoordinate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code AreaServiceTest}
 *
 * @author photowey
 * @date 2022/03/21
 * @since 1.0.0
 */
@SpringBootTest
class AreaServiceTest {

    @Autowired
    AreaService areaService;

    @Test
    void testSearchNearby() {

        GeographyCoordinate coordinate = new GeographyCoordinate("106.504962", "29.533155", "30");

        PageDocument<AreaDocument> document = this.areaService.searchNearby(coordinate);
        System.out.println(JSON.toJSONString(document, SerializerFeature.PrettyFormat));
    }

    @Test
    void testSearchNearbyFilter() {

        GeographyCoordinate coordinate = new GeographyCoordinate("106.504962", "29.533155", "30");

        PageDocument<AreaDocument> document = this.areaService.searchNearbyFilter(coordinate);
        System.out.println(JSON.toJSONString(document, SerializerFeature.PrettyFormat));
    }

}