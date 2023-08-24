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
package com.photowey.auto.pipeline.in.action.throttler.handler;

import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandlerContext;
import com.photowey.auto.pipeline.in.action.throttler.token.MessageThrottlerToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code DuplicateThrottlerHandler}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
@Slf4j
public class DuplicateThrottlerHandler implements MessageThrottlerHandler {

    @Override
    public boolean throttle(MessageThrottlerToken messageThrottlerToken, MessageThrottlerHandlerContext context) {
        if (messageThrottlerToken.isThrottled()) {
            return false;
        }
        boolean throttleResult = context.throttle(messageThrottlerToken);
        messageThrottlerToken.markThrottled();
        return throttleResult;
    }

    @Override
    public boolean anyThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext context) {
        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        // 过滤掉已经被限流的消息
        List<MessageThrottlerToken> needMessageThrottlerTokens = messageThrottlerTokens.stream()
                .filter(messageThrottlerToken -> !messageThrottlerToken.isThrottled()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needMessageThrottlerTokens)) {
            return false;
        }

        boolean throttleResult = context.anyThrottle(needMessageThrottlerTokens);
        needMessageThrottlerTokens.forEach(messageThrottlerToken -> messageThrottlerToken.markThrottled());
        return throttleResult;
    }

    @Override
    public boolean allThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext context) {
        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        // 过滤掉已经被限流的消息
        List<MessageThrottlerToken> needMessageThrottlerTokens = messageThrottlerTokens.stream()
                .filter(messageThrottlerToken -> !messageThrottlerToken.isThrottled()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(needMessageThrottlerTokens)) {
            return false;
        }

        boolean throttleResult = context.allThrottle(needMessageThrottlerTokens);
        needMessageThrottlerTokens.forEach(messageThrottlerToken -> messageThrottlerToken.markThrottled());
        return throttleResult;
    }
}