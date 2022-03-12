package com.photowey.study.netty.in.action.nio;

import org.junit.jupiter.api.Test;

/**
 * {@code FileChannelReaderTest}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
class FileChannelReaderTest {

    @Test
    void testRead() {
        FileChannelReader reader = new FileChannelReader();
        reader.read();
    }

}