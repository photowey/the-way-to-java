/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.spring.cloud.feign.interceptor.config;

import com.photowey.spring.cloud.feign.interceptor.RemoteFeignRequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code RemoteFeignConfigure}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
@Configuration
public class RemoteFeignConfigure {

    @Bean
    @ConditionalOnMissingBean
    public RemoteFeignRequestInterceptor remoteFeignRequestInterceptor() {
        return new RemoteFeignRequestInterceptor();
    }

}
