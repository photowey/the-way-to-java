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
package com.photowey.zookeeper.in.action.zkclient;

import com.photowey.zookeeper.in.action.AbstractZookeeperTest;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code ZkClientTest}
 *
 * @author photowey
 * @date 2021/11/03
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class ZkClientTest extends AbstractZookeeperTest {

    @Test
    void testZkClient() {
        String path = "/hello";
        String value = "hello world";
        String value2 = "hello world-2";
        ZkClient zkClient = this.zookeeperEngine.zkClient();

        if (!zkClient.exists(path)) {
            zkClient.createPersistent(path, value);
        }

        String data = zkClient.readData(path);
        Assertions.assertEquals(value, data, "Get the path data1, changed");

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                log.info("receive the zk change-event,the data-path:[{}], and newest data:[{}]", dataPath, data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                log.info("receive the zk delete-event,the data-path:[{}]", dataPath);
            }
        });

        zkClient.writeData(path, value2);
        String data2 = zkClient.readData(path);
        Assertions.assertEquals(value2, data2, "Get the path data2, changed");

        zkClient.deleteRecursive(path);
    }
}
