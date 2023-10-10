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
package com.photowey.redis.in.action.expirable.annotation;

import com.photowey.redis.in.action.expirable.autoconfigure.ExpirableCacheAutoConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * {@code EnableExpirableCache}
 *
 * @author photowey
 * @date 2023/10/10
 * @since 1.0.0
 */
@Inherited
@Documented
@AutoConfigurationPackage
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(value = {ExpirableCacheAutoConfigurer.class})
public @interface EnableExpirableCache {
}
