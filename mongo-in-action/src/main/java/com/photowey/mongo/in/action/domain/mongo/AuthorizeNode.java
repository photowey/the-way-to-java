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
package com.photowey.mongo.in.action.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@code AuthorizeNode}
 *
 * @author photowey
 * @date 2022/11/25
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizeNode implements Serializable {

    private static final long serialVersionUID = -7591302639865284900L;

    /**
     * 单位标识
     */
    private Long orgId;
    /**
     * 用户标识
     */
    private Long tenantId;
    /**
     * 权限标识
     */
    private Long permissionId;
    /**
     * 权限类型
     */
    private Integer permissionType;
}
