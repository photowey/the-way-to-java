package com.photowey.study.netty.in.action.nio;

import io.netty.channel.ChannelId;
import io.netty.channel.DefaultChannelId;
import org.junit.jupiter.api.Test;

/**
 * {@code ChannelIdTests}
 *
 * @author photowey
 * @date 2022/05/21
 * @since 1.0.0
 */
class ChannelIdTests {

    @Test
    void testChannelId() {
        // MACHINE_ID.length + PROCESS_ID_LEN + SEQUENCE_LEN + TIMESTAMP_LEN + RANDOM_LEN
        // 8 + 4 + 4+ 8 + 4
        // 005056fffec00001-00002a78-00000000-1886a0c1389cc682-d2d0b2af
        // d2d0b2af
        // 0 80 86 -1 -2 -64 0 1 0 0 42 120 0 0 0 0 24 -122 -96 -63 56 -100 -58 -126 -46 -48 -78 -81
        ChannelId channelId = DefaultChannelId.newInstance();
        System.out.println(channelId.asLongText());
        System.out.println(channelId.asShortText());

    }

}
