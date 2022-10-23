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
package com.photowey.auto.pipeline.in.action.throttler.handler;

import com.photowey.auto.pipeline.in.action.throttler.constant.ClassificationConstant;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandlerContext;
import com.photowey.auto.pipeline.in.action.throttler.token.MessageThrottlerToken;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code ClassificationThrottlerHandler}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
public class ClassificationThrottlerHandler implements MessageThrottlerHandler {

    @Override
    public boolean throttle(MessageThrottlerToken messageThrottlerToken, MessageThrottlerHandlerContext context) {
        if (!ClassificationConstant.MARKETING.equals(messageThrottlerToken.getClassification())) {
            return false;
        }
        return context.throttle(messageThrottlerToken);
    }

    @Override
    public boolean anyThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext context) {
        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        // 获取营销消息
        List<MessageThrottlerToken> marketingMessageThrottlerTokens = messageThrottlerTokens.stream().filter(messageThrottlerToken -> {
            return ClassificationConstant.MARKETING.equals(messageThrottlerToken.getClassification());
        }).collect(Collectors.toList());

        // 如果营销消息为空，说明消息均不需要被限流，直接返回false
        if (CollectionUtils.isEmpty(marketingMessageThrottlerTokens)) {
            return false;
        }

        return context.anyThrottle(marketingMessageThrottlerTokens);
    }

    @Override
    public boolean allThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext context) {
        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        // 获取营销消息
        List<MessageThrottlerToken> marketingMessageThrottlerTokens = messageThrottlerTokens.stream().filter(messageThrottlerToken -> {
            return ClassificationConstant.MARKETING.equals(messageThrottlerToken.getClassification());
        }).collect(Collectors.toList());

        // 存在非营销消息，非营销消息不会被限流
        if (marketingMessageThrottlerTokens.size() < messageThrottlerTokens.size()) {
            return false;
        }

        return context.allThrottle(marketingMessageThrottlerTokens);
    }
}