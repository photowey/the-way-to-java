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
        return new BloomFilterHolder(10_000_000, 0.01D);
    }

    @Bean
    public BloomFilterHolder bloomFilterHolderr() {
        return new BloomFilterHolder(10_000_000, 0.001D);
    }
}
