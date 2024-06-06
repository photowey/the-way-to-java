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

/**
 * {@code MessageSenderManager}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
public interface MessageSenderManager extends NormalSenderManager, DelayedSenderManager, SenderManager {

    default boolean supports(String sender) {
        return false;
    }

    default boolean delayedSupports(String delayedSender) {
        return false;
    }
}
