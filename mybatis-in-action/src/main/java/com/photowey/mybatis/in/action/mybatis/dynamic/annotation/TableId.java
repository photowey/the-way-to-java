/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.mybatis.in.action.mybatis.dynamic.annotation;

import com.photowey.mybatis.in.action.mybatis.dynamic.enums.IdTypeEnum;

import java.lang.annotation.*;

/**
 * {@code TableId}
 * 标记数据表主键
 *
 * @author photowey
 * @date 2021/11/08
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableId {

    /**
     * 主键标记
     * 数据库主键字段名称
     *
     * @return 数据库主键字段名称
     */
    String value();

    /**
     * 数据库主键类型
     * 默认自增长
     *
     * @return {@link IdTypeEnum}
     */
    IdTypeEnum type() default IdTypeEnum.AUTO;
}
