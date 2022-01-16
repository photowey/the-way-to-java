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
package com.photowey.oauth2.authentication.server.service;

import com.photowey.oauth2.authentication.core.constant.TokenConstants;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRoleDTO;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRolePermissionDTO;
import com.photowey.oauth2.authentication.service.permission.SystemRolePermissionService;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@code HotLoadRolePermissionService}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class HotLoadRolePermissionService implements SmartInitializingSingleton {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SystemRolePermissionService permissionService;

    @Override
    public void afterSingletonsInstantiated() {
        List<SystemRolePermissionDTO> rolePermissions = this.permissionService.reloadRolePermissions();
        rolePermissions.parallelStream().peek(rolePermission -> {
            List<String> roles = new ArrayList<>();
            if (isNotEmpty(rolePermission.getRoles())) {
                for (SystemRoleDTO role : rolePermission.getRoles()) {
                    roles.add(role.getCode().startsWith(TokenConstants.ROLE_PREFIX) ? role.getCode() : TokenConstants.ROLE_PREFIX + role.getCode());
                }
            }
            this.redisTemplate.opsForHash().put(TokenConstants.OAUTH_PERMISSION_URLS, rolePermission.getUrl(), roles);
        });
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

}