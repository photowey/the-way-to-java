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
package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUser;
import com.photowey.oauth2.authentication.mybatis.repository.SystemUserRepository;
import com.photowey.oauth2.authentication.service.permission.SystemUserService;
import org.springframework.stereotype.Service;

/**
 * {@code SystemUserServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserRepository, SystemUser> implements SystemUserService {
}
