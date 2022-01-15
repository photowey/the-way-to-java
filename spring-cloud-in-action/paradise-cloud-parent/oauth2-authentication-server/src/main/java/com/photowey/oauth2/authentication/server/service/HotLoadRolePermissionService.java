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