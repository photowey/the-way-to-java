package com.photowey.study.netty.in.action.nio;

import org.junit.jupiter.api.Test;

/**
 * {@code FileChannelTransferFromTest}
 *
 * @author photowey
 * @date 2022/03/12
 * @since 1.0.0
 */
class FileChannelTransferFromTest {

    @Test
    void tesTransferFrom() {
        FileChannelTransferFrom transfer = new FileChannelTransferFrom();
        transfer.transferFrom();
    }
}