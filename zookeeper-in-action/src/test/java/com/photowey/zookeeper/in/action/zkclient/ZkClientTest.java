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
