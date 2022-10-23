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
package com.photowey.auto.pipeline.in.action.throttler;

import com.foldright.auto.pipeline.AutoPipeline;
import com.photowey.auto.pipeline.in.action.throttler.token.MessageThrottlerToken;

import java.util.List;

/**
 * {@code MessageThrottler}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
@AutoPipeline
public interface MessageThrottler {

    /**
     * 节流单个消息
     *
     * @param messageThrottlerToken 消息限流令牌
     * @return 是否被节流
     */
    boolean throttle(MessageThrottlerToken messageThrottlerToken);

    /**
     * 节流多个消息。任意一个消息被节流将返回true,否则返回false
     *
     * @param messageThrottlerTokens 多个消息限流令牌
     * @return 是否被节流
     */
    boolean anyThrottle(List<MessageThrottlerToken> messageThrottlerTokens);

    /**
     * 节流多个消息。所有消息被节流才会返回true, 否则返回false
     *
     * @param messageThrottlerTokens 多个消息限流令牌
     * @return 是否被节流
     */
    boolean allThrottle(List<MessageThrottlerToken> messageThrottlerTokens);
}
