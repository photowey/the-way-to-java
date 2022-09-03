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
package com.photowey.spring.security.oauth2.pkce.client.resolver;

import com.nimbusds.jose.jwk.JWK;
import com.photowey.spring.security.oauth2.pkce.client.reader.ClasspathReader;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Component;

/**
 * {@code JwkResolver}
 *
 * @author photowey
 * @date 2022/09/03
 * @since 1.0.0
 */
@Component
public class JwkResolver {

    @Autowired
    private ClasspathReader classpathReader;
    @Autowired
    private Environment environment;


    @SneakyThrows
    public JWK apply() {
        return this.apply(null);
    }

    @SneakyThrows
    public JWK apply(ClientRegistration clientRegistration) {

        String jose = "client.jks";
        String alias = "client";
        String password = "photowey";
        String env = environment.getActiveProfiles()[0];
        String path = String.format("key/%s/%s", env, jose);

        return this.classpathReader.readJwk(path, alias, password);
    }
}

