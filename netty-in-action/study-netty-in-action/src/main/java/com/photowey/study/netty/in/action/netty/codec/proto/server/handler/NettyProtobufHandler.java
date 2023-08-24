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
package com.photowey.study.netty.in.action.netty.codec.proto.server.handler;

import com.photowey.study.netty.in.action.netty.codec.proto.StudentProtobuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * {@code NettyProtobufHandler}
 *
 * @author photowey
 * @date 2022/03/26
 * @since 1.0.0
 */
@Slf4j
public class NettyProtobufHandler extends SimpleChannelInboundHandler<StudentProtobuf.Student> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, StudentProtobuf.Student msg) throws Exception {
        log.info("handle protobuf request,the info:student.id:[{}] student.name:[{}]", msg.getId(), msg.getName());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~(>^ω^<), 我是8848~", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
