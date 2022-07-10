package com.photowey.bloom.filter.in.action.bloom.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * {@code BloomFilterHolder}
 *
 * @author photowey
 * @date 2022/07/11
 * @since 1.0.0
 */
public class BloomFilterHolder {

    private final BloomFilter<Long> bloomFilter;

    public BloomFilterHolder(long expectedInsertions, double fpp) {
        bloomFilter = BloomFilter.create(Funnels.longFunnel(), expectedInsertions, fpp);
    }


    public void put(Long value) {
        this.bloomFilter.put(value);
    }

    public boolean mightContain(Long value) {
        return this.bloomFilter.mightContain(value);
    }


}
