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

import com.photowey.auto.pipeline.in.action.throttler.config.ThrottlerModeConfiguration;
import com.photowey.auto.pipeline.in.action.throttler.constant.SendSwitch;
import com.photowey.auto.pipeline.in.action.throttler.mode.ThrottlerMode;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandler;
import com.photowey.auto.pipeline.in.action.throttler.pipeline.MessageThrottlerHandlerContext;
import com.photowey.auto.pipeline.in.action.throttler.proxy.ThrottlerProxy;
import com.photowey.auto.pipeline.in.action.throttler.token.AcquireToken;
import com.photowey.auto.pipeline.in.action.throttler.token.MessageThrottlerToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@code AcquireThrottlerHandler}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
public class AcquireThrottlerHandler implements MessageThrottlerHandler {

    private static final Logger apiThrottlerLog = LoggerFactory.getLogger("api.throttler.log");

    @Autowired
    private ThrottlerProxy throttlerProxy;
    @Autowired
    private ThrottlerModeConfiguration throttlerModeConfiguration;

    private boolean throttle(AcquireToken acquireToken) {
        // 获取限流模式
        ThrottlerMode throttlerMode = throttlerModeConfiguration.getThrottlerMode(acquireToken.getAppKey(), acquireToken.getThrottleTag());
        // 执行限流
        return !throttlerProxy.tryAcquireWithAppKey(throttlerMode, acquireToken.getAppKey(), acquireToken.getPermits());
    }

    @Override
    public boolean throttle(MessageThrottlerToken messageThrottlerToken, MessageThrottlerHandlerContext context) {
        boolean throttled = throttle(new AcquireToken(messageThrottlerToken.getThrottleTag(), messageThrottlerToken.getAppKey(), messageThrottlerToken.getPermits()));

        // 限流日志埋点
        if (SendSwitch.THROTTLER_ONLY_WATCH || throttled) {
            log(messageThrottlerToken.getAppKey(), messageThrottlerToken.getPermits(), messageThrottlerToken.getThrottleTag(), throttled);
        }

        return throttled;
    }

    @Override
    public boolean anyThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext context) {
        return throttle(messageThrottlerTokens, acquireTokens -> acquireTokens.stream().anyMatch(this::throttle)
        );
    }

    @Override
    public boolean allThrottle(List<MessageThrottlerToken> messageThrottlerTokens, MessageThrottlerHandlerContext messageThrottlerHandlerContext) {
        return throttle(messageThrottlerTokens, acquireTokens -> acquireTokens.stream().allMatch(this::throttle));
    }

    private static boolean throttle(List<MessageThrottlerToken> messageThrottlerTokens, Function<List<AcquireToken>, Boolean> function) {
        if (CollectionUtils.isEmpty(messageThrottlerTokens)) {
            return false;
        }

        List<AcquireToken> acquireTokens = messageThrottlerTokens.stream()
                .collect(Collectors.groupingBy(messageThrottlerToken -> messageThrottlerToken.getAppKey()))
                .entrySet()
                .stream()
                .map(messageEntry -> {
                    String appKey = messageEntry.getKey();
                    int permits = messageEntry.getValue().stream()
                            .map(messageThrottlerToken -> messageThrottlerToken.getPermits())
                            .reduce(Integer::sum).orElse(1);
                    String throttlerTag = messageEntry.getValue().get(0).getThrottleTag();
                    return new AcquireToken(throttlerTag, appKey, permits);
                }).collect(Collectors.toList());

        boolean throttled = function.apply(acquireTokens);

        // 限流日志埋点
        if (SendSwitch.THROTTLER_ONLY_WATCH || throttled) {
            messageThrottlerTokens.forEach(messageThrottlerToken -> {
                log(messageThrottlerToken.getAppKey(), messageThrottlerToken.getPermits(), messageThrottlerToken.getThrottleTag(), throttled);
            });
        }

        return throttled;
    }

    private static void log(String appKey, int permits, String throttlerTag, boolean throtted) {
        List<String> metrics = new ArrayList<>();
        metrics.add(appKey);
        metrics.add(String.valueOf(permits));
        metrics.add(throttlerTag);
        metrics.add(String.valueOf(throtted));
        String logContent = StringUtils.join(metrics, "|");
        apiThrottlerLog.info(logContent);
    }
}
