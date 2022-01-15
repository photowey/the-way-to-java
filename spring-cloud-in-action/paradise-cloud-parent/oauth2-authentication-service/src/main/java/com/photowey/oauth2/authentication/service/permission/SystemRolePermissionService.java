package com.photowey.oauth2.authentication.service.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRolePermissionDTO;
import com.photowey.oauth2.authentication.core.domain.entity.SystemRolePermission;

import java.util.List;

/**
 * {@code SystemRolePermissionService}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface SystemRolePermissionService extends IService<SystemRolePermission> {

    List<SystemRolePermissionDTO> reloadRolePermissions();
}
