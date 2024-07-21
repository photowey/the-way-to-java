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
package com.photowey.spring.project.infras.in.action.rust.optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@code ResultTest}
 *
 * @author photowey
 * @since 2024/07/22
 */
class ResultTest {

    @Test
    void testResult() {
        Result<String, Throwable> resultOk = Result.Ok("Success");
        Result<String, Throwable> resultErr = Result.Err(new RuntimeException("Crashed"));

        Assertions.assertTrue(resultOk.isOk());
        Assertions.assertTrue(resultErr.isErr());

        Assertions.assertFalse(resultOk.isErr());
        Assertions.assertFalse(resultErr.isOk());

        String value = resultOk.unwrap();
        Assertions.assertEquals("Success", value);
        Assertions.assertEquals("Crashed", resultErr.unwrapErr().getMessage());
    }
}