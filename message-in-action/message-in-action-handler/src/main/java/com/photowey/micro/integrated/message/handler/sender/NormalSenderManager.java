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
package com.photowey.micro.integrated.message.handler.sender;

import com.photowey.micro.integrated.message.core.exception.MessageSenderNotFoundException;

import java.util.List;

/**
 * {@code NormalSenderManager}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface NormalSenderManager extends SenderManager {

    /**
     * 注册消息发送器
     *
     * @param sender {@link MessageSender}
     */
    <T extends MessageSender> void register(T sender);

    /**
     * 获取消息发送器 {@link MessageSender}
     * |- 当获取不到时会抛出异常 {@link MessageSenderNotFoundException}
     *
     * @param sender 名称
     * @return {@link MessageSender}
     */
    MessageSender acquire(String sender);

    /**
     * 尝试获取消息发送器 {@link MessageSender}
     * |- 当获取不到时返回 {@code null}
     *
     * @param sender 名称
     * @return {@link MessageSender}
     */
    MessageSender tryAcquire(String sender);

    /**
     * 获取所有 {@link MessageSender} 名称
     *
     * @return {@link MessageSender} 名称列表
     */
    List<String> candidates();

    /**
     * 获取所有 {@link MessageSender} 实例
     * |— 不可修改
     *
     * @return {@link MessageSender} 实例列表
     */
    List<MessageSender> senders();
}
