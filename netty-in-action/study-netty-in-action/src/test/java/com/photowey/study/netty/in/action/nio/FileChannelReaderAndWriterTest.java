package com.photowey.study.netty.in.action.nio;

import org.junit.jupiter.api.Test;

/**
 * {@code FileChannelWriterAndReaderTest}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
class FileChannelReaderAndWriterTest {

    @Test
    void testReadAndWrite() {
        FileChannelReaderAndWriter readerAndWriter = new FileChannelReaderAndWriter();
        readerAndWriter.readAndWrite();
    }

}