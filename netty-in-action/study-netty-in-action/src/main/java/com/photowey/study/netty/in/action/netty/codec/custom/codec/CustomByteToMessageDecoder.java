/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.study.netty.in.action.netty.codec.custom.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * {@code CustomByteToMessageDecoder}
 *
 * @author photowey
 * @date 2022/03/28
 * @since 1.0.0
 */
@Slf4j
public class CustomByteToMessageDecoder extends ByteToMessageDecoder {

    private static final int LONG_BYTE_SIZE = 8;

    /**
     * 解码
     *
     * @param ctx {@link ChannelHandlerContext}
     * @param in  入站 {@link ByteBuf}
     * @param out 将节码后的数据传给 下一个 {@code handler}
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("handler {} is invoking ---------------------------- 111111-333-decoder", CustomByteToMessageDecoder.class.getName());

        // 需要 判断是否有 {@code 8} 个字节, 才能读取
        if (in.readableBytes() >= LONG_BYTE_SIZE) {
            out.add(in.readLong());
        }

        /**
         * 1.不论解码器 {@code handler} 还是编码器 {@code handler}, 接受的消息类型必须与待处理的消息类型一直
         * -- 否则, 该 {@code handler} 不会被执行.
         *
         * 2.在解码器 {@code handler} 进行数据节码时, 需要判断缓存区 {@link ByteBuf} 数据是否足够,
         * -- 否则接收到的数据会与预期的结果不一致.
         */
    }
}
