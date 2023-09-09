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
 * {@code DelayedSenderManager}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface DelayedSenderManager extends SenderManager {

    /**
     * 注册消息发送器
     *
     * @param sender {@link DelayedMessageSender}
     */

    <T extends DelayedMessageSender> void delayedRegister(T sender);

    /**
     * 获取消息发送器 {@link DelayedMessageSender}
     * |- 当获取不到时会抛出异常 {@link MessageSenderNotFoundException}
     *
     * @param sender 名称
     * @return {@link DelayedMessageSender}
     */
    DelayedMessageSender delayedAcquire(String sender);

    /**
     * 尝试获取消息发送器 {@link DelayedMessageSender}
     * |- 当获取不到时返回 {@code null}
     *
     * @param sender 名称
     * @return {@link DelayedMessageSender}
     */
    DelayedMessageSender tryDelayedAcquire(String sender);

    /**
     * 获取所有 {@link DelayedMessageSender} 名称
     *
     * @return {@link DelayedMessageSender} 名称列表
     */
    List<String> delayedCandidates();

    /**
     * 获取所有 {@link DelayedMessageSender} 实例
     * |— 不可修改
     *
     * @return {@link DelayedMessageSender} 实例列表
     */
    List<DelayedMessageSender> delayedSenders();
}
