package com.photowey.oauth2.authentication.mybatis.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.oauth2.authentication.core.domain.entity.SystemRolePermission;

import java.util.List;

/**
 * {@code SystemRolePermissionRepository}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface SystemRolePermissionRepository extends BaseMapper<SystemRolePermission> {

    List<SystemRolePermission> findByPermissionId(Long permissionId);
}
