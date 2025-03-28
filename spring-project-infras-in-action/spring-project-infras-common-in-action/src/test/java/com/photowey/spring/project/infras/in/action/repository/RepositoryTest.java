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
package com.photowey.spring.project.infras.in.action.repository;

import com.photowey.spring.project.infras.in.action.App;
import com.photowey.spring.project.infras.in.action.LocalTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code RepositoryTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/01
 */
@SpringBootTest(classes = App.class)
class RepositoryTest extends LocalTest {

    @Test
    void testRepository() {
        Assertions.assertNotNull(this.employeeRepository);
    }

}