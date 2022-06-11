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

import java.lang.annotation.*;

/**
 * {@code PublicApi}
 * 公共 API 内部服务、网关、直接访问服务--均可
 *
 * @author photowey
 * @date 2022/01/29
 * @since 1.0.0
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PublicApi {

    /**
     * 执定的 {@code service} 才能够访问 - 也就是:白名单
     *
     * @return 指定的服务列表
     */
    String[] value() default {};
}
