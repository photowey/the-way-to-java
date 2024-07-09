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
package com.photowey.spring.project.infras.in.action.accessor;

import io.github.photowey.spring.infras.core.annotation.condition.ConditionalOnSpEL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code MockGithubAccessor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/07/09
 */
@Slf4j
@Component
@ConditionalOnSpEL("${io.github.photowey.github.accessor.mock.profiles}")
public class MockGithubAccessor implements GithubAccessor {

    @Override
    public void afterSingletonsInstantiated() {
        log.info("--- io.github.photowey.spring.infras.core.example.accessor.MockGithubAccessor loaded ---");
    }

    @Override
    public String sayHello() {
        return "Mock";
    }
}