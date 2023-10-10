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
package com.photowey.micro.integrated.message.autoconfigure;

import com.photowey.micro.integrated.message.handler.engine.DefaultMessageEngine;
import com.photowey.micro.integrated.message.handler.engine.MessageEngine;
import com.photowey.micro.integrated.message.handler.sender.MessageSenderManager;
import com.photowey.micro.integrated.message.handler.sender.impl.DefaultMessageSenderManager;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * {@code PlatformMessageAutoconfigure}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
@AutoConfiguration
public class PlatformMessageAutoconfigure {

    @Bean
    @ConditionalOnMissingBean(MessageSenderManager.class)
    public MessageSenderManager messageSenderManager() {
        return new DefaultMessageSenderManager();
    }

    @Bean
    public MessageEngine messageEngine() {
        return new DefaultMessageEngine();
    }
}
