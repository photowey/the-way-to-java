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
package io.github.photowey.jwt.authcenter.jwt.encryptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code DefaultSubjectEncryptor1}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
public class DefaultSubjectEncryptor implements SubjectEncryptor {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public String encrypt(String key, String data) {
        throw new UnsupportedOperationException("Unsupported now");
    }

    @Override
    public String decrypt(String key, String base64Data) {
        throw new UnsupportedOperationException("Unsupported now");
    }
}
