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
package com.photowey.wechat.sdk.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photowey.wechat.sdk.client.WechatClient;
import com.photowey.wechat.sdk.core.constant.WechatConstants;
import com.photowey.wechat.sdk.core.domain.meta.Meta;
import com.photowey.wechat.sdk.core.domain.meta.MetaRegistry;
import com.photowey.wechat.sdk.core.exception.WechatRequestException;
import com.photowey.wechat.sdk.core.property.WechatProperties;
import com.photowey.wechat.sdk.holder.ApplicationContextHolder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.net.URI;

/**
 * {@code AbstractApi}
 *
 * @author photowey
 * @date 2024/03/06
 * @since 1.0.0
 */
@Accessors(fluent = true)
public abstract class AbstractApi implements ApplyObjectMapper {

    @Getter
    private final WechatClient wechatClient;
    @Getter
    private final ObjectMapper objectMapper;
    private final String tenantId;

    public AbstractApi(WechatClient wechatClient, String tenantId) {
        this.wechatClient = wechatClient;
        this.objectMapper = new ObjectMapper();
        this.tenantId = tenantId;
        this.applyObjectMapper(this.objectMapper);
    }

    public MetaRegistry registry() {
        // TODO
        return null;
    }

    public Meta meta() {
        return this.registry().meta(this.tenantId());
    }

    protected RequestEntity<?> post(URI uri, Object body, HttpHeaders... httpHeaders) {
        try {
            RequestEntity.BodyBuilder builder = RequestEntity.post(uri)
                    .header(WechatConstants.HEADER_WECHAT_PAY_TENANT, this.tenantId());

            if (isNotEmpty(httpHeaders)) {
                for (HttpHeaders httpHeader : httpHeaders) {
                    builder.headers(httpHeader);
                }
            }

            String payload = this.objectMapper.writeValueAsString(body);

            return builder.body(payload);
        } catch (Exception e) {
            throw new WechatRequestException(e, "Post 请求失败:[{}]", uri.getPath());
        }
    }

    protected RequestEntity<?> patch(URI uri, Object body, HttpHeaders... httpHeaders) {
        try {
            RequestEntity.BodyBuilder builder = RequestEntity.patch(uri)
                    .header(WechatConstants.HEADER_WECHAT_PAY_TENANT, this.tenantId());

            if (isNotEmpty(httpHeaders)) {
                for (HttpHeaders httpHeader : httpHeaders) {
                    builder.headers(httpHeader);
                }
            }

            String payload = this.objectMapper.writeValueAsString(body);

            return builder.body(payload);
        } catch (Exception e) {
            throw new WechatRequestException(e, "Patch 请求失败:[{}]", uri.getPath());
        }
    }

    protected RequestEntity<?> put(URI uri, Object body, HttpHeaders... httpHeaders) {
        try {
            RequestEntity.BodyBuilder builder = RequestEntity.put(uri)
                    .header(WechatConstants.HEADER_WECHAT_PAY_TENANT, this.tenantId());

            if (isNotEmpty(httpHeaders)) {
                for (HttpHeaders httpHeader : httpHeaders) {
                    builder.headers(httpHeader);
                }
            }

            String payload = this.objectMapper.writeValueAsString(body);

            return builder.body(payload);
        } catch (Exception e) {
            throw new WechatRequestException(e, "Put 请求失败:[{}]", uri.getPath());
        }
    }

    protected RequestEntity<?> get(URI uri, HttpHeaders... httpHeaders) {
        RequestEntity.HeadersBuilder<?> builder = RequestEntity.get(uri)
                .header(WechatConstants.HEADER_WECHAT_PAY_TENANT, this.tenantId());

        if (isNotEmpty(httpHeaders)) {
            for (HttpHeaders httpHeader : httpHeaders) {
                builder.headers(httpHeader);
            }
        }

        return builder.build();
    }

    public String tenantId() {
        ConfigurableApplicationContext applicationContext = ApplicationContextHolder.INSTANCE.applicationContext();
        WechatProperties wechatProperties = applicationContext.getBean(WechatProperties.class);
        return StringUtils.hasText(tenantId) ? this.tenantId : wechatProperties.getGlobal().getTenantId();
    }

    @SafeVarargs
    private static <T> boolean isNotEmpty(T... objects) {
        return !ObjectUtils.isEmpty(objects);
    }
}
