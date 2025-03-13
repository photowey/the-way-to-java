/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.security.service.impl;

import io.github.photowey.jwt.authcenter.security.loader.AuthenticationUserDetailLoader;
import io.github.photowey.jwt.authcenter.security.service.DomainUserDetailsService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code DomainUserDetailsServiceImpl}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/13
 */
public class DomainUserDetailsServiceImpl implements DomainUserDetailsService, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public UserDetails loadUserByUsername(String proxy) throws UsernameNotFoundException {
        return this.tryLoad(proxy);
    }

    public UserDetails tryLoad(String proxy) {
        Map<String, AuthenticationUserDetailLoader> beans =
            this.beanFactory.getBeansOfType(AuthenticationUserDetailLoader.class);
        List<AuthenticationUserDetailLoader> loaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(loaders);

        for (AuthenticationUserDetailLoader loader : loaders) {
            if (loader.supports(proxy)) {
                return loader.load(proxy);
            }
        }

        throw new UnsupportedOperationException("Unreached here");
    }
}

