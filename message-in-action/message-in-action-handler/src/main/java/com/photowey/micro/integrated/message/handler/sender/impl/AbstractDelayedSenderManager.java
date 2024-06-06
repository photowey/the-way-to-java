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
package com.photowey.micro.integrated.message.handler.sender.impl;

import com.photowey.micro.integrated.message.core.exception.MessageSenderNotFoundException;
import com.photowey.micro.integrated.message.handler.sender.DelayedMessageSender;
import com.photowey.micro.integrated.message.handler.sender.DelayedSenderManager;
import com.photowey.micro.integrated.message.handler.sender.MessageSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@code AbstractDelayedSenderManager}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public abstract class AbstractDelayedSenderManager extends AbstractNormalSenderManager implements DelayedSenderManager {

    protected final ConcurrentHashMap<String, DelayedMessageSender> delayedSenders = new ConcurrentHashMap<>();

    protected final Lock delayedLock = new ReentrantLock();

    public <T extends DelayedMessageSender> void delayedRegisterReport(T sender) {}

    /**
     * 注册消息发送器
     * <pre>
     * |- RabbitMQ
     * |- |- rabbit -> {@code RabbitDelayedMessageSender}
     * |- |- kafka -> {@code KafkaDelayedMessageSender}
     * |- |- rocket -> {@code RocketDelayedMessageSender}
     * |- ...
     * </pre>
     *
     * @param sender {@link DelayedMessageSender}
     */
    @Override
    public <T extends DelayedMessageSender> void delayedRegister(T sender) {
        this.delayedLock.lock();
        try {
            this.delayedSenders.computeIfAbsent(sender.name(), (x) -> sender);
            this.delayedRegisterReport(sender);
        } finally {
            this.delayedLock.unlock();
        }
    }

    /**
     * 获取消息发送器 {@link MessageSender}
     * |- 当获取不到时会抛出异常 {@link MessageSenderNotFoundException}
     *
     * @param sender 名称
     * @return {@link DelayedMessageSender}
     */
    @Override
    public DelayedMessageSender delayedAcquire(String sender) {
        if (this.delayedSenders.containsKey(sender)) {
            return this.delayedSenders.get(sender);
        }

        throw new MessageSenderNotFoundException("消息器({})未注册", sender);
    }

    /**
     * 尝试获取消息发送器 {@link DelayedMessageSender}
     * |- 当获取不到时返回 {@code null}
     *
     * @param sender 名称
     * @return {@link DelayedMessageSender}
     */
    @Override
    public DelayedMessageSender tryDelayedAcquire(String sender) {
        return this.delayedSenders.get(sender);
    }

    /**
     * 获取所有 {@link DelayedMessageSender} 名称
     *
     * @return {@link DelayedMessageSender} 名称列表
     */
    @Override
    public List<String> delayedCandidates() {
        return new ArrayList<>(this.delayedSenders.keySet());
    }

    /**
     * 获取所有 {@link DelayedMessageSender} 实例
     * |— 不可修改
     *
     * @return {@link DelayedMessageSender} 实例列表
     */
    @Override
    public List<DelayedMessageSender> delayedSenders() {
        Collection<DelayedMessageSender> values = this.delayedSenders.values();
        return Collections.unmodifiableList(new ArrayList<>(values));
    }
}
