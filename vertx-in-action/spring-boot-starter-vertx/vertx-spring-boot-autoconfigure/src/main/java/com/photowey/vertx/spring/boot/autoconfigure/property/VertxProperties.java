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
