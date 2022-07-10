package com.photowey.bloom.filter.in.action.bloom.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code BloomFilterHolderTest}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
@SpringBootTest
class BloomFilterHolderTest {

    @Autowired
    private BloomFilterHolder bloomFilterHolder;

    @Autowired
    @Qualifier("bloomFilterHolderr")
    private BloomFilterHolder bloomFilterHolderr;

    @Test
    void testBloomFilter() {
        for (long i = 0; i < 100_000_000; i++) {
            this.bloomFilterHolder.put(i);
        }

        int count = 0;

        for (long i = 100_000_000; i < 100_000_000 * 2; i++) {
            if (this.bloomFilterHolder.mightContain(i)) {
                count++;
            }
        }

        System.out.println("count = " + count);
    }

    @Test
    void testBloomFilterV2() {
        for (long i = 0; i < 100_000_000; i++) {
            this.bloomFilterHolderr.put(i);
        }

        int count = 0;

        for (long i = 100_000_000; i < 100_000_000 * 2; i++) {
            if (this.bloomFilterHolderr.mightContain(i)) {
                count++;
            }
        }

        System.out.println("count = " + count);
    }
}