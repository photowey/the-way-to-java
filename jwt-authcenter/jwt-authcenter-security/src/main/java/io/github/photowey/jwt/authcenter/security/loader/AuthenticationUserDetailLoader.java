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
package io.github.photowey.jwt.authcenter.security.loader;

import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * {@code AuthenticationUserDetailLoader}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/13
 */
public interface AuthenticationUserDetailLoader extends UserDetailLoader {

    UserDetails load(String proxy);

    default SecurityProperties securityProperties() {
        return this.beanFactory().getBean(SecurityProperties.class);
    }

    default String checker() {
        return this.securityProperties().auth().checker().name();
    }

    @Override
    default int getOrder() {
        return 0;
    }

    default String parseUsername(String proxy, String protocol) {
        return proxy.replaceAll(protocol, "");
    }
}
