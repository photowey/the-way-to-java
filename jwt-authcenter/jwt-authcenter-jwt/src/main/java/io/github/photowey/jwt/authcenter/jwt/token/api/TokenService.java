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
package io.github.photowey.jwt.authcenter.jwt.token.api;

import io.github.photowey.jwt.authcenter.jwt.encryptor.SubjectEncryptor;
import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.Authentication;

/**
 * {@code TokenService}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
public interface TokenService
    extends TokenValidatorService, AuthenticationService, TokenParserService {

    /**
     * 获取 {@link BeanFactory} 实例
     *
     * @return {@link BeanFactory}
     */
    BeanFactory beanFactory();

    /**
     * 获取 {@link SecurityProperties} 实例
     *
     * @return {@link SecurityProperties}
     */
    @Override
    default SecurityProperties securityProperties() {
        return this.beanFactory().getBean(SecurityProperties.class);
    }

    /**
     * 获取 {@link SubjectEncryptor} 实例
     *
     * @return {@link SubjectEncryptor}
     */
    @Override
    default SubjectEncryptor subjectEncryptor() {
        return this.beanFactory().getBean(SubjectEncryptor.class);
    }

    /**
     * 颁发令牌
     * |- 默认: 不记住我
     *
     * @param authentication {@link Authentication} 认证对象
     * @return 令牌
     */
    default String createToken(Authentication authentication) {
        return this.createToken(authentication, false);
    }

    /**
     * 颁发令牌
     *
     * @param authentication {@link Authentication} 认证对象
     * @param rememberMe     rememberMe 记住我 true: 是; false: 否
     * @return 令牌
     */
    String createToken(Authentication authentication, boolean rememberMe);
}
