/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.zookeeper.in.action.zk;

import com.photowey.zookeeper.in.action.AbstractZookeeperTest;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code ZookeeperTest}
 * ```shell
 * docker run -itd \
 * --restart always \
 * --name zookeeper \
 * --privileged=true \
 * -e TZ=Asia/Shanghai \
 * -v /etc/localtime:/etc/localtime:ro \
 * -p 2181:2181 \
 * -d zookeeper:3.4.12
 * ```
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@SpringBootTest
class ZookeeperTest extends AbstractZookeeperTest {

    @Test
    void testZookeeperClient() throws KeeperException, InterruptedException {
        String path = "/hello";
        String value = "hello world";
        String value2 = "hello world-2";

        this.nodeCreate(path, value, CreateMode.PERSISTENT);
        String data = this.nodeGet(path, true, new Stat());
        Assertions.assertEquals(value, data, "Get the path data1, changed");

        this.nodeChange(path, value2, true, -1);
        String data2 = this.nodeGet(path, true, new Stat());
        Assertions.assertEquals(value2, data2, "Get the path data2, changed");

        this.nodeDelete(path, true, -1);

        try {
            this.nodeGet(path, true, new Stat());
        } catch (KeeperException.NoNodeException e) {
        }
    }

    public void nodeCreate(String path, String value, CreateMode createMode) throws KeeperException, InterruptedException {
        this.zookeeperEngine.zooKeeper().create(path, this.serialize(value), ZooDefs.Ids.OPEN_ACL_UNSAFE, createMode);
    }

    public String nodeGet(String path, boolean watch, Stat stat) throws KeeperException, InterruptedException {
        byte[] data = this.zookeeperEngine.zooKeeper().getData(path, watch, stat);
        return this.deserialize(data);
    }

    public void nodeChange(String path, String value, boolean watch, int version) throws KeeperException, InterruptedException {
        this.zookeeperEngine.zooKeeper().exists(path, watch);
        this.zookeeperEngine.zooKeeper().setData(path, this.serialize(value), version);
    }

    public void nodeDelete(String path, boolean watch, int version) throws KeeperException, InterruptedException {
        this.zookeeperEngine.zooKeeper().exists(path, watch);
        this.zookeeperEngine.zooKeeper().delete(path, version);
    }

}
