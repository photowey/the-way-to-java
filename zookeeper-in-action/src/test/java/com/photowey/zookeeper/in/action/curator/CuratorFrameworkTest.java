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
package com.photowey.zookeeper.in.action.curator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.photowey.zookeeper.in.action.AbstractZookeeperTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@code CuratorFrameworkTest}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class CuratorFrameworkTest extends AbstractZookeeperTest {

    @Test
    void testCuratorFrameworkClient() throws Exception {
        String path = "/hello";
        String value = "hello world";
        String value2 = "hello world-2";

        this.create(path, value, CreateMode.PERSISTENT);
        String data = this.query(path);
        Assertions.assertEquals(value, data, "Get the path data1, changed");

        this.update(path, value2);
        String data2 = this.query(path);
        Assertions.assertEquals(value2, data2, "Get the path data3, changed");
        this.delete(path);

        this.createAsync(path, value, CreateMode.PERSISTENT);

        Thread.sleep(2000);

        String data3 = this.query(path);
        Assertions.assertEquals(value, data3, "Get the path data3, changed");
        this.delete(path);

        this.transaction(path, value, value2);

        String data4 = this.query(path);
        Assertions.assertEquals(value2, data4, "Get the path data4, changed");
        this.delete(path);

        this.transactionOperations(path, value, value2);

        String data5 = this.query(path);
        Assertions.assertEquals(value2, data5, "Get the path data4, changed");
        this.delete(path);
    }

    @Test
    void testCuratorFrameworkNodeCache() throws Exception {
        String path = "/node/cache";

        String value = "hello node";
        String value2 = "hello node-2";

        this.create(path, value, CreateMode.PERSISTENT);
        String data = this.query(path);
        Assertions.assertEquals(value, data, "Get the path data1, changed");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        NodeCache nodeCache = new NodeCache(curatorFramework, path, false);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                if (null != nodeCache.getCurrentData()) {
                    log.info("receive the Curator node data change event,the new data:[{}],path:[{}]", deserialize(nodeCache.getCurrentData().getData()), nodeCache.getPath());
                } else {
                    log.info("receive the Curator node data change event,the path:[{}]", nodeCache.getPath());
                }
            }
        }, executorService);

        this.update(path, value2);

        String data2 = this.query(path);
        Assertions.assertEquals(value2, data2, "Get the path data2, changed");
        this.delete(path);
    }

    @Test
    void testCuratorFrameworkPathCache() throws Exception {
        String path = "/node/cache";

        String value = "hello node";
        String value2 = "hello node-2";

        this.create(path, value, CreateMode.PERSISTENT);
        String data = this.query(path);
        Assertions.assertEquals(value, data, "Get the path data1, changed");

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();

        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, path, true);
        // NORMAL                                         异步初始化
        // BUILD_INITIAL_CACHE             同步初始化
        // POST_INITIALIZED_EVENT    异步初始化,初始话后会触发事件
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                log.info("----------------------------------------:{}", event.getType());
                switch (event.getType()) {
                    case CHILD_ADDED:
                        log.info("receive the Curator node add children event,the new data:[{}],path:[{}]", deserialize(event.getData().getData()), event.getData().getPath());
                        break;
                    case CHILD_REMOVED:
                        log.info("receive the Curator node remove children event,the new data:[{}],path:[{}]", deserialize(event.getData().getData()), event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        log.info("receive the Curator node update children event,the new data:[{}],path:[{}]", deserialize(event.getData().getData()), event.getData().getPath());
                        break;
                    case CONNECTION_SUSPENDED:
                        // ConnectionState#SUSPENDED
                        break;
                    case CONNECTION_RECONNECTED:
                        // ConnectionState#RECONNECTED
                        break;
                    case CONNECTION_LOST:
                        // ConnectionState#LOST
                        break;
                    case INITIALIZED:
                        // PathChildrenCache.StartMode#POST_INITIALIZED_EVENT
                        // childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
                        break;
                    default:
                        break;
                }
            }
        }, executorService);

        String childPath1 = "/node/cache/path1";
        String childPath2 = "/node/cache/path2";
        String childPath3 = "/node/cache/path3";
        this.create(childPath1, value, CreateMode.PERSISTENT);
        this.create(childPath2, value, CreateMode.PERSISTENT);
        this.create(childPath3, value, CreateMode.PERSISTENT);

        List<String> children = curatorFramework.getChildren().watched().forPath(path);
        children.forEach(child -> {
            log.info("the path had child path is:[{}]", child);
        });

        this.update(childPath1, value2);
        this.delete(childPath2);

        Thread.sleep(1000_000);
    }

    public void create(String path, String value, CreateMode createMode) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        curatorFramework
                .create()
                .creatingParentsIfNeeded()
                .withMode(createMode)
                .forPath(path, this.serialize(value));
    }

    public void createAsync(String path, String value, CreateMode createMode) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        curatorFramework
                .create()
                .creatingParentsIfNeeded()
                .withMode(createMode)
                .inBackground((client, event) -> {
                    log.info("receive the Curator callback,the event:[{}],path:[{}]", event.getName(), event.getPath());
                })
                .forPath(path, this.serialize(value));
    }

    public void delete(String path) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        curatorFramework
                .delete()
                .deletingChildrenIfNeeded()
                .withVersion(-1)
                .forPath(path);
    }

    public void update(String path, String newValue) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        curatorFramework
                .setData()
                .withVersion(-1)
                .forPath(path, this.serialize(newValue));
    }

    public String query(String path) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        byte[] bytes = curatorFramework
                .getData()
                .storingStatIn(new Stat())
                .forPath(path);

        return this.deserialize(bytes);
    }

    public void transaction(String path, String value, String newValue) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();
        Collection<CuratorTransactionResult> transactionResults = curatorFramework
                .inTransaction()
                .create()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, this.serialize(value))
                .and()
                .setData()
                .forPath(path, this.serialize(newValue))
                .and()
                .commit();

        transactionResults.forEach(transactionResult -> {
            log.info("the operation type is:[{}],the path:[{}],result path:[{}], result state:\n{}",
                    transactionResult.getType(),
                    transactionResult.getForPath(),
                    transactionResult.getResultPath(),
                    JSON.toJSONString(transactionResult.getResultStat(), SerializerFeature.PrettyFormat)
            );
        });
    }

    public void transactionOperations(String path, String value, String newValue) throws Exception {
        CuratorFramework curatorFramework = this.zookeeperEngine.curatorFramework();

        List<CuratorOp> operations = new ArrayList<>();

        CuratorOp curatorOp = curatorFramework
                .transactionOp()
                .create()
                .withMode(CreateMode.PERSISTENT)
                .forPath(path, this.serialize(value));

        CuratorOp curatorOp2 = curatorFramework
                .transactionOp()
                .setData()
                .forPath(path, this.serialize(newValue));

        operations.add(curatorOp);
        operations.add(curatorOp2);

        Collection<CuratorTransactionResult> transactionResults = curatorFramework
                .transaction()
                .forOperations(operations);

        transactionResults.forEach(transactionResult -> {
            log.info("the operation type is:[{}],the path:[{}],result path:[{}], result state:\n{}",
                    transactionResult.getType(),
                    transactionResult.getForPath(),
                    transactionResult.getResultPath(),
                    JSON.toJSONString(transactionResult.getResultStat(), SerializerFeature.PrettyFormat)
            );
        });

    }

}
