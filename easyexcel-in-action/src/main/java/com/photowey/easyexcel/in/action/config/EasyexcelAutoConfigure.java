package com.photowey.easyexcel.in.action.config;

import com.photowey.easyexcel.in.action.reader.ResourceReader;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@code EasyexcelAutoConfigure}
 *
 * @author photowey
 * @date 2023/03/14
 * @since 1.0.0
 */
@AutoConfiguration
public class EasyexcelAutoConfigure {

    @Bean
    public ResourceReader resourceReader() {
        return new ResourceReader();
    }
}
