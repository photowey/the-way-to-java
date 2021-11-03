package com.photowey.rocketmq.in.action.mmap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * {@code MmapHandlerTest}
 *
 * @author photowey
 * @date 2021/11/01
 * @since 1.0.0
 */
@SpringBootTest
class MmapHandlerTest {

    @Autowired
    private MmapHandler mmapHandler;

    @Test
    void testMmap() throws IOException {
        String content = "hello-rocketmq-test-mmap";
        String echo = this.mmapHandler.mmap("hello-rocketmq", content);
        Assertions.assertEquals(content, echo);
    }

}