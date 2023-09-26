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
package com.photowey.spring.in.action.aop;

import com.photowey.spring.in.action.infras.getter.BeanFactoryGetter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@code AbstractAspect}
 *
 * @author photowey
 * @date 2023/09/26
 * @since 1.0.0
 */
public abstract class AbstractAspect implements BeanFactoryGetter, BeanFactoryAware {

    protected final UrlPathHelper helper = new UrlPathHelper();
    protected final AntPathMatcher requestMatcher = this.createMatcher(true);

    private ListableBeanFactory beanFactory;

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    protected AntPathMatcher createMatcher(boolean caseSensitive) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setTrimTokens(false);
        matcher.setCaseSensitive(caseSensitive);

        return matcher;
    }

}
