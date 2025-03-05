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
package io.github.photowey.jwt.authcenter.repository.database;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.photowey.jwt.authcenter.core.domain.entity.AuthorizeUser;

/**
 * {@code AuthorizeUserRepository}
 * |- 认证|授权用户仓储
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/03/06
 */
public interface AuthorizeUserRepository extends BaseMapper<AuthorizeUser> {
    // do nothing now.
}

