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
package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemPermission;
import com.photowey.oauth2.authentication.mybatis.repository.SystemPermissionRepository;
import com.photowey.oauth2.authentication.service.permission.SystemPermissionService;
import org.springframework.stereotype.Service;

/**
 * {@code PermissionServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionRepository, SystemPermission> implements SystemPermissionService {

}
