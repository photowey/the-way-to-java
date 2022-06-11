/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.redis.in.action.lock;

import com.photowey.redis.in.action.engine.redis.IRedisEngine;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.stream.Collectors;

/**
 * {@code RedisLock}
 *
 * @author photowey
 * @date 2021/10/29
 * @since 1.0.0
 */
@Slf4j
@Component
public class RedisLock implements IRedisLock {

    private static final long LOCK_TIME = 1000L;
    private static final long RETRY_LOCK_INTERVAL = 10;
    private static final String REDIS_DIST_LOCK_NS = "redis:dist:lock:ns:";

    private static final Object LOCK = new Object();
    private static final DelayQueue<LockData<LockItem>> DELAY_QUEUE = new DelayQueue<>();

    private static final ThreadLocal<String> LOCKERS = new ThreadLocal<>();
    private static final ThreadLocal<String> LOCK_NAMES = new ThreadLocal<>();

    private final IRedisEngine redisEngine;
    private final ExecutorService executorService;

    private static final AtomicBoolean ONCE = new AtomicBoolean(false);

    @Getter
    @Setter
    private Thread ownerThread;
    @Getter
    @Setter
    private String lockName = "default:lock";

    public RedisLock(IRedisEngine redisEngine) {
        this.redisEngine = redisEngine;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @PostConstruct
    public void init() throws Exception {
        String releaseScript = this.readLua("release.lua");
        String pexpireScript = this.readLua("pexpire.lua");

        LUA_SCRIPTS.put("release", releaseScript);
        LUA_SCRIPTS.put("pexpire", pexpireScript);
    }

    @Override
    public void lock() {
        this.lock(this.getLockName());
    }

    @Override
    public void lock(String lockName) {
        synchronized (LOCK) {
            while (!tryLock(lockName)) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    @Override
    public boolean tryLock() {
        return this.tryLock(this.getLockName());
    }

    @Override
    public boolean tryLock(String lockName) {
        Thread t = Thread.currentThread();
        // 说明本线程正在持有锁
        if (ownerThread == t) {
            return true;
        } else if (ownerThread != null) {
            // 说明本进程中有别的线程正在持有分布式锁
            return false;
        }
        try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
            String token = this.createLockToken();
            synchronized (this) {
                if ((ownerThread == null) && this.setNx(jedis, token, lockName)) {
                    // 抢锁成功
                    LOCKERS.set(token);
                    LOCK_NAMES.set(lockName);
                    this.setOwnerThread(t);

                    // 启动-心跳线程
                    if(!ONCE.getAndSet(true)) {
                        executorService.execute(new HeartBeat());
                    }

                    // 发送延迟消息
                    DELAY_QUEUE.add(new LockData<>(LOCK_TIME, new LockItem(lockName, token, true, Thread.currentThread())));
                    log.info("current thread:[{}],acquire lock:[{}] successfully...", Thread.currentThread().getName(), lockName);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void unlock() {
        synchronized (LOCK) {
            if (ownerThread != Thread.currentThread()) {
                throw new IllegalStateException("not support release other's lock!");
            }
            try (Jedis jedis = this.redisEngine.jedisEngine().jedis()) {
                Long result = eval(jedis, releaseLockLua(), LOCK_NAMES.get(), LOCKERS.get());
                if (result != 0L) {
                    log.info("current thread:[{}],release lock:[{}] successfully...", Thread.currentThread().getName(), LOCK_NAMES.get());
                } else {
                    log.info("current thread:[{}],release lock:[{}] failure...", Thread.currentThread().getName(), LOCK_NAMES.get());
                }
            } catch (Exception e) {
                DELAY_QUEUE.add(new LockData<>(RETRY_LOCK_INTERVAL, new LockItem(LOCK_NAMES.get(), LOCKERS.get(), false, Thread.currentThread())));
                throw new RuntimeException("release the lock exception", e);
            } finally {
                LOCKERS.remove();
                String lockerName = LOCK_NAMES.get();
                LOCK_NAMES.remove();
                this.setOwnerThread(null);
                log.info("current thread:[{}],release local lock:[{}] successfully...", Thread.currentThread().getName(), lockerName);
            }

            LOCK.notifyAll();
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException("not support new condition!");
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("not support try-lock with timeout!");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException("not support lock with interruptibly!");
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdown();
    }

    public String createLockToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public boolean setNx(Jedis jedis, String token, String lockName) {
        SetParams params = new SetParams();
        params.px(LOCK_TIME);
        params.nx();
        return "OK".equals(jedis.set(REDIS_DIST_LOCK_NS + lockName, token, params));
    }

    public Long eval(Jedis jedis, String script, String key, String value) {
        return (Long) jedis.eval(script, Collections.singletonList(REDIS_DIST_LOCK_NS + key), Collections.singletonList(value));
    }

    public Long eval(Jedis jedis, String script, String key, String value, String time) {
        return (Long) jedis.eval(script, Collections.singletonList(REDIS_DIST_LOCK_NS + key), Arrays.asList(value, time));
    }

    private class HeartBeat implements Runnable {

        @Override
        public void run() {
            log.info("--- >>> watch dog thread running <<< ---");
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    LockItem lockItem = DELAY_QUEUE.take().getData();

                    try (Jedis jedis = redisEngine.jedisEngine().jedis()) {
                        Thread lockThread = lockItem.getOwner();
                        boolean dead = !lockThread.isAlive() || !lockItem.isDelayed();
                        if (dead) {
                            // 线程死亡或者释放锁操作
                            this.handleDead(jedis, lockItem, lockThread);
                        } else {
                            // 续期操作
                            this.handleDelay(lockItem, jedis, lockThread);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("delay the current lock exception", e);
                    }
                } catch (InterruptedException e) {
                    log.error("the current watch dog thread was interrupted", e);
                    break;
                }
            }
            log.info("--- >>> the watch dog thread down <<< ---");
        }

        private void handleDelay(LockItem lockItem, Jedis jedis, Thread lockThread) {
            Long result = -1L;
            try {
                // 判断本地锁所有权是否已释放，未释放需要续期
                if (ownerThread == lockItem.getOwner()) {
                    result = eval(jedis, delayLockLua(), lockItem.getKey(), lockItem.getValue(), String.valueOf(LOCK_TIME));
                } else {
                    result = 0L;
                }
            } catch (Exception e) {
                log.error("handle delay lock:[{}] exception", lockItem.getKey(), e);
            } finally {
                if (result == -1L) {
                    DELAY_QUEUE.add(new LockData<>(RETRY_LOCK_INTERVAL, new LockItem(lockItem.getKey(), lockItem.getValue(), true, lockThread)));
                    log.info("handle delay lock:[{}] exception and retry now", lockItem.getKey());
                } else if (result == 0L) {
                    log.info("handle delay lock:[{}] the lock released,bye~bye~", lockItem.getKey());
                } else {
                    DELAY_QUEUE.add(new LockData<>(LOCK_TIME, new LockItem(lockItem.getKey(), lockItem.getValue(), true, lockThread)));
                    log.info("handle delay lock:[{}] the lock running,go next~", lockItem.getKey());
                }
            }
        }

        private void handleDead(Jedis jedis, LockItem lockItem, Thread lockThread) {
            Long result = -1L;
            try {
                result = eval(jedis, releaseLockLua(), lockItem.getKey(), lockItem.getValue());
            } catch (Exception e) {
                log.error("handle dead-thread lock:[{}] exception", lockItem.getKey(), e);
            } finally {
                if (result == -1L) {
                    DELAY_QUEUE.add(new LockData<>(RETRY_LOCK_INTERVAL, new LockItem(lockItem.getKey(), lockItem.getValue(), false, lockThread)));
                    log.info("handle dead-thread lock:[{}] exception and retry now", lockItem.getKey());
                } else if (result == 0L) {
                    log.info("handle dead-thread lock:[{}] release failure...not exists or not the master of this lock, maybe!", lockItem.getKey());
                } else {
                    log.info("handle dead-thread lock:[{}] release successfully...bye~bye~", lockItem.getKey());
                }
            }
        }
    }

    private final static String DEFAULT_RELEASE_LOCK_LUA =
            "if redis.call('get', KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('del', KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    private final static String DEFAULT_DELAY_LOCK_LUA =
            "if redis.call('get', KEYS[1]) == ARGV[1]\n" +
                    "then\n" +
                    "    return redis.call('pexpire', KEYS[1], ARGV[2])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";

    private static final Map<String, String> LUA_SCRIPTS = new HashMap<>(3);

    public String delayLockLua() {
        return StringUtils.hasText(LUA_SCRIPTS.get("pexpire")) ? LUA_SCRIPTS.get("pexpire") : DEFAULT_DELAY_LOCK_LUA;
    }

    public String releaseLockLua() {
        return StringUtils.hasText(LUA_SCRIPTS.get("release")) ? LUA_SCRIPTS.get("release") : DEFAULT_RELEASE_LOCK_LUA;
    }

    public String readLua(final String luaScript) throws Exception {
        return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(luaScript).toURI()))
                .stream().filter(each -> !each.startsWith("---")).map(each -> each + System.lineSeparator()).collect(Collectors.joining());
    }
}
