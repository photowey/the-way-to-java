package com.photowey.spring.in.action.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code ScheduleTaskTest}.
 *
 * @author weichangjun
 * @version 1.0.0
 * @since 2025/07/07
 */
@Slf4j
@SpringBootTest
@EnableScheduling
class ScheduleTaskTest {

    /**
     * 需要自定义一个任务执行线程池 - 最好不要占用 Spring 的调度线程
     * |- 这个是一个示例->默认已经配置好了
     * |- 可以通过 @Qualifier("asyncXxxExecutor") 指定自定义的线程池
     */
    @Autowired
    private TaskExecutor taskExecutor;

    @Scheduled(cron = "0/5 * * * * ?")
    public void scheduledTask() {
        this.async();
    }

    private void async(/*可以添加自定义的静态参数*/) {
        CompletableFuture.runAsync(this::sync, taskExecutor);
    }

    private void sync(/*可以添加自定义的静态参数*/) {
        try {
            this.onSchedule();
        } catch (Throwable e) {
            // TODO 这儿一定要-try-catch 避免出现其他情况导致定时任务无法执行
            //  由于在这个把异常抛出去,async() 方法没有 join | get 异常也不会真的抛出
            //  其实: 打个日志或者其他的均可以 -> 比如: 发个钉钉机器人消息等等
            // 此处打个日志即可
            // throw new RuntimeException(e);

            log.error("asset: xxx定时任务执行异常: {} - 一起其他一些参数均可打印", e.getMessage(), e);
        }
    }

    private void onSchedule(/*可以添加自定义的静态参数*/) {
        // 1.上一把全局分布式锁 -> 避免多实例在统一时间出发
        // 2.执行任务
        // 2.1.先作幂等判断 -> 可以根据状态或数据是否重复写入等判断
        // |- 2.1.1.避免重复执行数据
        // |- 2.1.2.避免多实例由于时间不同步同一任务“错峰”执行 -> 数据重复
        // 2.2.查询数据库
        // 2.3.处理数据

        // this#onSchedule_example 是一个示例
    }

    private void onSchedule_example(/*可以添加自定义的静态参数*/) {
        // 注意: 将上锁和释放锁的逻辑封装成方法 - 避免忘记释放锁
        this.syncRun(() -> {
            // 2.执行任务
            // 2.1.先作幂等判断 -> 必须根据状态或数据是否重复写入等判断
            // |- 2.1.1.避免重复执行数据
            // |- 2.1.2.避免多实例由于时间不同步同一人物“错峰”执行 -> 数据重复
            if (this.determineThisTimesIsTriggerDuplicate()) {
                return;
            }
            // 2.2.查询
            this.executeScheduleTask();
        });
    }

    private void syncRun(Runnable task) {
        // 1.以本地锁效仿分布式锁
        Lock lock = new ReentrantLock();
        // 1.上一把全局分布式锁 -> 避免多实例在统一时间出发
        lock.lock();
        try {
            // 注意: 此处的 Runnable 是一个普通的函数接口 不是线程
            // 为什么？
            // 1.没调用 start() 方法
            // 2.没提交线程池
            task.run();
        } finally {
            lock.unlock();
        }
    }

    private boolean determineThisTimesIsTriggerDuplicate() {
        // TODO 待完善
        return false;
    }

    private void executeScheduleTask() {
        // TODO 真正执行逻辑的地方
        // 查询是否有临期的数据
        // Y: -> 写入数据
        // N: -> 忽略
    }
}
