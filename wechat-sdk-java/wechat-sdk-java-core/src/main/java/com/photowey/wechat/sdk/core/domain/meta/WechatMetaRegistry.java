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
package com.photowey.wechat.sdk.core.domain.meta;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.photowey.wechat.sdk.core.checker.WechatExceptionChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code WechatMetaRegistry}
 *
 * @author photowey
 * @date 2024/02/25
 * @since 1.0.0
 */
@Slf4j
public class WechatMetaRegistry implements MetaRegistry, Serializable, BeanFactoryAware {

    private static final long serialVersionUID = -2872840185774493360L;

    private final Map<String, Meta> ctx = new ConcurrentHashMap<>();
    private final Set<String> tenantIds = new ConcurrentSkipListSet<>();

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void register(Meta... metas) {
        this.register(Stream.of(metas).collect(Collectors.toList()));
    }

    @Override
    public void register(Collection<Meta> metas) {
        WechatExceptionChecker.checkNotNull(metas, "The metas can't be empty.");

        this.registerRelax(metas);
    }

    @Override
    public void registerRelax(Collection<Meta> metas) {
        if (null != metas) {
            metas.forEach(this::register);
        }
    }

    @Override
    public Meta remove(String tenantId) {
        this.tenantIds.remove(tenantId);
        return this.ctx.remove(tenantId);
    }

    @Override
    public Meta meta(String tenantId) {
        Meta meta = this.metaRelax(tenantId);
        if (ObjectUtils.isEmpty(meta)) {
            MetaLoader metaLoader = this.beanFactory.getBean(MetaLoader.class);
            meta = metaLoader.load(tenantId);
            if (!ObjectUtils.isEmpty(meta)) {
                this.register(meta);
            }
        }

        return meta;
    }

    @Override
    public Meta metaRelax(String tenantId) {
        return this.ctx.get(tenantId);
    }

    @Override
    public Set<String> tenantIds() {
        return this.tenantIds;
    }

    private void register(Meta meta) {
        this.tenantIds.add(meta.tenantId());
        this.ctx.put(meta.tenantId(), meta);
        if (log.isInfoEnabled()) {
            log.info("Report: register tenant meta:{}", JSON.toJSONString(meta, JSONWriter.Feature.PrettyFormat));
        }
    }

}
