package com.photowey.zookeeper.in.action.lock;

import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * {@code IReadWriteLockTest}
 *
 * @author photowey
 * @date 2021/11/04
 * @since 1.0.0
 */
@SpringBootTest
class IReadWriteLockTest {

    @Autowired
    private ZkClient zkClient;

    @Test
    public void test1() throws InterruptedException {
        IReadWriteLock readWriteLock = new ReadWriteLock(this.zkClient);
        IReadLock readLock = readWriteLock.readLock();
        readLock.lock();
        System.out.println("test1--IReadLock");
        System.out.println("======休眠30s,30s后释放锁=========");
        for (int i = 0; i < 30; i++) {
            System.out.println(30 - i);
            TimeUnit.SECONDS.sleep(1);
        }
        readLock.unlock();
    }

    @Test
    public void test2() throws InterruptedException {
        IReadWriteLock readWriteLock = new ReadWriteLock(this.zkClient);
        IReadLock readLock = readWriteLock.readLock();
        readLock.lock();
        System.out.println("test2--IReadLock");
        System.out.println("======休眠10s,10s后释放锁=========");
        for (int i = 0; i < 10; i++) {
            System.out.println(10 - i);
            TimeUnit.SECONDS.sleep(1);
        }
        readLock.unlock();
    }

    @Test
    public void test3() throws InterruptedException {
        IReadWriteLock readWriteLock = new ReadWriteLock(this.zkClient);
        IWriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        System.out.println("test3--IWriteLock");
        System.out.println("======休眠20s,20s后释放锁=========");
        for (int i = 0; i < 20; i++) {
            System.out.println(20 - i);
            TimeUnit.SECONDS.sleep(1);
        }
        writeLock.unlock();
    }

    @Test
    public void test4() throws InterruptedException {
        IReadWriteLock readWriteLock = new ReadWriteLock(this.zkClient);
        IWriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        System.out.println("test4--IWriteLock");
        System.out.println("======休眠40s,40s后释放锁=========");
        for (int i = 0; i < 40; i++) {
            System.out.println(40 - i);
            TimeUnit.SECONDS.sleep(1);
        }
        writeLock.unlock();
    }

    @Test
    public void test5() throws InterruptedException {
        IReadWriteLock readWriteLock = new ReadWriteLock(this.zkClient);
        IReadLock readLock = readWriteLock.readLock();
        readLock.lock();
        System.out.println("test5--IReadLock");
        readLock.unlock();
    }
}