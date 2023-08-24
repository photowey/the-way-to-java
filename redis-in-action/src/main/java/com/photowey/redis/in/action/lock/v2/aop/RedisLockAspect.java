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
package com.photowey.redis.in.action.lock.v2.aop;

import com.photowey.redis.in.action.lock.v2.annotation.RedisLock;
import com.photowey.redis.in.action.lock.v2.definition.RedisLockDefinition;
import com.photowey.redis.in.action.lock.v2.enums.RedisLockPrefix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * {@code RedisLockAspect}
 *
 * @author photowey
 * @date 2022/10/04
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class RedisLockAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    /**
     * 扫描的任务队列
     */
    private final ConcurrentLinkedQueue<RedisLockDefinition> definitions;
    /**
     * 线程池,维护keyAliveTime
     */
    private final ScheduledExecutorService scheduler;

    public RedisLockAspect(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.definitions = new ConcurrentLinkedQueue<>();
        this.scheduler = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("rlock-pool").daemon(true).build());
    }

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                Iterator<RedisLockDefinition> iterator = definitions.iterator();
                while (iterator.hasNext()) {
                    RedisLockDefinition definition = iterator.next();
                    // 判空
                    if (definition == null) {
                        iterator.remove();
                        continue;
                    }
                    // 判断 key 是否还有效,无效的话进行移除
                    if (redisTemplate.opsForValue().get(definition.getBusinessKey()) == null) {
                        iterator.remove();
                        continue;
                    }
                    // 超时重试次数,超过时给线程设定中断
                    if (definition.getCurrentCount() > definition.getTryCount()) {
                        definition.getCurrentTread().interrupt();
                        iterator.remove();
                        continue;
                    }
                    // 判断是否进入最后三分之一时间
                    long now = System.currentTimeMillis();
                    boolean shouldExtend = (definition.getLastModifyTime() + definition.getModifyPeriod()) <= now;
                    if (shouldExtend) {
                        definition.setLastModifyTime(now);
                        this.redisTemplate.expire(definition.getBusinessKey(), definition.getLockTime(), TimeUnit.SECONDS);
                        log.info("re-keepalive redis.lock, businessKey: [{}], retry count: {}", definition.getBusinessKey(), definition.getCurrentCount());
                        definition.setCurrentCount(definition.getCurrentCount() + 1);
                    }
                }
            } catch (Throwable e) {
                log.error("do redis keepalive schedule task exception", e);
            }

        }, 0, 2, TimeUnit.SECONDS);
    }


    @Pointcut("@annotation(com.photowey.redis.in.action.lock.v2.annotation.RedisLock)")
    public void redisLockPC() {
    }

    @Around(value = "redisLockPC()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = this.resolveMethod(pjp);
        RedisLock annotation = method.getAnnotation(RedisLock.class);
        RedisLockPrefix prefix = annotation.prefix();
        Object[] params = pjp.getArgs();
        String ukString = params[annotation.lockFiled()].toString();
        // parameter validate
        // or SpEL
        String businessKey = prefix.getUniqueKey(ukString);
        String uniqueValue = UUID.randomUUID().toString();
        // 加锁
        Object result = null;
        try {
            Boolean done = this.redisTemplate.opsForValue().setIfAbsent(businessKey, uniqueValue, annotation.lockTime(), TimeUnit.SECONDS);
            // FIXME check-done
            log.info("acquire redis.lock, businessKey is [{}], result:[{}]", businessKey, done);
            Thread currentThread = Thread.currentThread();
            definitions.add(new RedisLockDefinition(businessKey, annotation.lockTime(), System.currentTimeMillis(), currentThread, annotation.tryCount()));
            result = pjp.proceed();
            if (currentThread.isInterrupted()) {
                throw new InterruptedException("You had been interrupted");
            }
        } catch (InterruptedException e) {
            log.error("Interrupt exception, rollback transaction", e);
            throw new Exception("Interrupt exception, please send request again");
        } catch (Exception e) {
            log.error("has some error, please check again", e);
            // TODO do something
        } finally {
            this.redisTemplate.delete(businessKey);
            log.info("release the lock, businessKey is [{}]", businessKey);
        }

        return result;
    }

    private Method resolveMethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getMethod();
    }
}
