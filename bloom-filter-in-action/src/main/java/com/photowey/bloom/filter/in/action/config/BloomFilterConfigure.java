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
package com.photowey.bloom.filter.in.action.config;

import com.photowey.bloom.filter.in.action.bloom.filter.BloomFilterHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code BloomFilterConfigure}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@Configuration
public class BloomFilterConfigure {

    @Bean
    public BloomFilterHolder bloomFilterHolder() {
        return new BloomFilterHolder(100_000_000, 0.01D);
    }

    @Bean
    public BloomFilterHolder bloomFilterHolderr() {
        return new BloomFilterHolder(100_000_000, 0.001D);
    }
}
