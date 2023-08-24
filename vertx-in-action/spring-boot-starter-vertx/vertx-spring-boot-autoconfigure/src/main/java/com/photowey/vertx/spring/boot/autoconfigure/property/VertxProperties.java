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
package com.photowey.vertx.spring.boot.autoconfigure.property;

import com.photowey.vertx.spring.boot.core.model.HandlerMapping;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.List;

/**
 * {@code VertxProperties}
 *
 * @author photowey
 * @date 2022/02/17
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "vertx")
public class VertxProperties implements Serializable {

    private static final int CPU = Runtime.getRuntime().availableProcessors();

    private static final long serialVersionUID = 8002285078780827690L;

    private Print print = new Print();
    private int port = 7923;
    private int eventLoopPoolSize = CPU;
    private int workerPoolSize = CPU;
    private int instances = CPU;
    private long maxEventLoopExecuteTime = 2_000;
    private List<HandlerMapping> handlerMappings;

    @Data
    public static class Print implements Serializable {

        private static final long serialVersionUID = 5309508438650934243L;

        private boolean enabled = true;
    }
}
