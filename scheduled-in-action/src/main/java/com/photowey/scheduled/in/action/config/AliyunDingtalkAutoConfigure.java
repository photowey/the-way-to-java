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
package com.photowey.scheduled.in.action.config;

import com.photowey.scheduled.in.action.core.setter.MarkdownMessageSetter;
import com.photowey.scheduled.in.action.core.setter.TextMessageSetter;
import com.photowey.scheduled.in.action.dingtalk.sign.DingtalkSignAlgorithm;
import com.photowey.scheduled.in.action.dingtalk.sign.DingtalkSignAlgorithmImpl;
import com.photowey.scheduled.in.action.dingtalk.sign.SignResult;
import com.photowey.scheduled.in.action.notify.dingtalk.DingtalkMessageNotifier;
import com.photowey.scheduled.in.action.notify.dingtalk.DingtalkMessageNotifierImpl;
import com.photowey.scheduled.in.action.property.MessageProperties;
import com.photowey.scheduled.in.action.property.ScheduledProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * {@code AliyunDingtalkAutoConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@AutoConfiguration
@EnableConfigurationProperties(value = {
    MessageProperties.class,
    ScheduledProperties.class,
})
public class AliyunDingtalkAutoConfigure {

    @Bean
    public DingtalkMessageNotifier dingtalkMessageNotifier() {
        return new DingtalkMessageNotifierImpl();
    }

    @Bean
    public DingtalkSignAlgorithm<SignResult> dingtalkSignAlgorithm() {
        return new DingtalkSignAlgorithmImpl();
    }

    @Bean
    public TextMessageSetter textMessageSetter() {
        return new TextMessageSetter();
    }

    @Bean
    public MarkdownMessageSetter markdownMessageSetter() {
        return new MarkdownMessageSetter();
    }
}
