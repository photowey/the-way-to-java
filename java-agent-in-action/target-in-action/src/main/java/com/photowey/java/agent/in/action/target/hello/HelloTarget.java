package com.photowey.java.agent.in.action.target.hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * {@code HelloTarget}
 * before:
 * <pre>
 * {@literal @}Slf4j
 * {@literal @}Component
 * public class HelloTarget implements SmartInitializingSingleton, DisposableBean {
 *
 *     private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
 *
 *     {@literal @}Override
 *     public void afterSingletonsInstantiated() {
 *         this.executorService.scheduleAtFixedRate(this::sayHello, 10L, 10L, TimeUnit.MILLISECONDS);
 *     }
 *
 *     {@literal @}Override
 *     public void destroy() throws Exception {
 *         this.executorService.shutdown();
 *     }
 *
 *     public void sayHello() {
 *         log.info("the schedule action:SayHello");
 *     }
 * }
 * </pre>
 *
 * @author photowey
 * @date 2021/12/13
 * @since 1.0.0
 */
@Slf4j
@Component
public class HelloTarget implements SmartInitializingSingleton, DisposableBean {

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void afterSingletonsInstantiated() {
        this.executorService.scheduleAtFixedRate(this::sayHello, 1000L, 1000L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void destroy() throws Exception {
        this.executorService.shutdown();
    }

    public void sayHello() {
        log.info("the schedule action:SayHello");
    }
}
