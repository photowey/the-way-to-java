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
package com.photowey.auto.pipeline.in.action.throttler.proxy;

import com.photowey.auto.pipeline.in.action.throttler.MessageThrottler;
import com.photowey.auto.pipeline.in.action.throttler.constant.SendSwitch;
import com.photowey.auto.pipeline.in.action.throttler.enums.MergeStrategy;
import com.photowey.auto.pipeline.in.action.throttler.handler.AcquireThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.handler.ClassificationThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.handler.DuplicateThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerPipeline;
import com.photowey.auto.pipeline.in.action.throttler.token.MessageThrottlerToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * {@code MessageThrottlerProxy}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
@Slf4j
public class MessageThrottlerProxy {

    @Autowired
    private AcquireThrottlerHandler acquireThrottlerHandler;
    private MessageThrottler messageThrottler;

    @PostConstruct
    public void init() {
        messageThrottler = new MessageThrottlerPipeline()
                .addLast(new ClassificationThrottlerHandler())
                .addLast(new DuplicateThrottlerHandler())
                .addLast(acquireThrottlerHandler);
    }

    /**
     * 限流单个消息
     *
     * @param messageThrottlerToken 单个消息令牌
     * @return 是否限流成功
     */
    public boolean throttle(MessageThrottlerToken messageThrottlerToken) {
        if (!SendSwitch.ENABLE_API_THROTTLER) {
            return false;
        }
        try {
            boolean throttled = messageThrottler.throttle(messageThrottlerToken);
            return SendSwitch.THROTTLER_ONLY_WATCH ? false : throttled;
        } catch (Exception e) {
            log.error("Failed to throttle messageSendDTO:" + messageThrottlerToken, e);
            // throttle内部异常不应该影响正常请求，遇到此情况直接降级限流通过
            return false;
        }
    }

    /**
     * 限流多个消息, 合并策略可通过 {@link SendSwitch#THROTTLER_MERGE_STRATEGY} 开关控制
     *
     * @param messageThrottlerTokens 多个消息令牌
     * @return 是否限流成功
     */
    public boolean throttle(List<MessageThrottlerToken> messageThrottlerTokens) {
        if (!SendSwitch.ENABLE_API_THROTTLER) {
            return false;
        }

        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        MergeStrategy mergeStrategy = MergeStrategy.getByName(SendSwitch.THROTTLER_MERGE_STRATEGY);
        if (mergeStrategy == null) {
            log.error("illegal throttler mergeStrategy:" + SendSwitch.THROTTLER_MERGE_STRATEGY);
            return false;
        }

        try {
            boolean throttled = mergeStrategy.throttle(messageThrottler, messageThrottlerTokens);
            return SendSwitch.THROTTLER_ONLY_WATCH ? false : throttled;
        } catch (Exception e) {
            log.error("Failed to throttle messageSendDTO:" + messageThrottlerTokens, e);
            // throttle内部异常不应该影响正常请求，遇到此情况直接降级限流通过
            return false;
        }
    }
}