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
package com.photowey.druid.in.action.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.photowey.druid.in.action.filter.DruidAdFilter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@code RemoveDruidAdConfigure}
 *
 * @author photowey
 * @date 2022/02/20
 * @since 1.0.0
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(DruidDataSourceAutoConfigure.class)
@ConditionalOnProperty(name = "spring.datasource.druid.stat-view-servlet.enabled", havingValue = "true", matchIfMissing = true)
public class RemoveDruidAdConfigure {

    /**
     * 除去 {@code Druid} 页面底部的广告
     *
     * @param properties {@link DruidStatProperties}
     * @return {@link FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean<DruidAdFilter> removeDruidAdFilter(DruidStatProperties properties) {
        DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
        String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
        String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
        FilterRegistrationBean<DruidAdFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(this.druidAdFilter());
        registrationBean.addUrlPatterns(commonJsPattern);

        return registrationBean;
    }

    @Bean
    public DruidAdFilter druidAdFilter() {
        return new DruidAdFilter();
    }
}
