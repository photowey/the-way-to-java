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
package com.photowey.oauth2.authentication.api.security.annotation;

import com.photowey.oauth2.authentication.api.security.config.SecurityApiAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableSecurityApi}
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {SecurityApiAutoConfigure.class})
public @interface EnableSecurityApi {
}
