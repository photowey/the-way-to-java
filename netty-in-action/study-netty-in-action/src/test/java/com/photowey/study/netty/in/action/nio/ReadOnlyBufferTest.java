package com.photowey.study.netty.in.action.nio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ReadOnlyBufferException;

/**
 * {@code ReadOnlyBufferTest}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
class ReadOnlyBufferTest {

    @Test
    void testReadOnly() {
        Assertions.assertThrows(ReadOnlyBufferException.class, () -> {
            ReadOnlyBuffer readOnlyBuffer = new ReadOnlyBuffer();
            readOnlyBuffer.readOnly();
        });
    }

}