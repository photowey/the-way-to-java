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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRoleDTO;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRolePermissionDTO;
import com.photowey.oauth2.authentication.core.domain.entity.SystemPermission;
import com.photowey.oauth2.authentication.core.domain.entity.SystemRolePermission;
import com.photowey.oauth2.authentication.mybatis.repository.SystemPermissionRepository;
import com.photowey.oauth2.authentication.mybatis.repository.SystemRolePermissionRepository;
import com.photowey.oauth2.authentication.mybatis.repository.SystemRoleRepository;
import com.photowey.oauth2.authentication.service.permission.SystemRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * {@code SystemRolePermissionServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemRolePermissionServiceImpl extends
        ServiceImpl<SystemRolePermissionRepository, SystemRolePermission> implements SystemRolePermissionService {

    private SystemRoleRepository roleRepository;
    private SystemPermissionRepository permissionRepository;
    private SystemRolePermissionRepository rolePermissionRepository;

    public SystemRolePermissionServiceImpl(
            SystemRoleRepository roleRepository, SystemPermissionRepository permissionRepository, SystemRolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    /**
     * TODO 待优化-考虑分页-加载
     *
     * @return
     */
    @Override
    public List<SystemRolePermissionDTO> reloadRolePermissions() {
        List<SystemRolePermissionDTO> dtos = new ArrayList<>();
        List<SystemPermission> permissions = this.getSystemPermissions();
        for (SystemPermission permission : permissions) {
            List<SystemRoleDTO> roles = this.getSystemRoles(permission);
            SystemRolePermissionDTO dto = this.populateRolePermissionDTO(permission, roles);
            dtos.add(dto);
        }

        return dtos;
    }

    private List<SystemRoleDTO> getSystemRoles(SystemPermission permission) {
        QueryWrapper<SystemRolePermission> rolePermissionQueryWrapper = new QueryWrapper<>();
        rolePermissionQueryWrapper.eq("permission_id", permission.getId());
        List<SystemRolePermission> rolePermissions = this.rolePermissionRepository.selectList(rolePermissionQueryWrapper);
        return rolePermissions.stream().map(rolePermission -> this.roleRepository.findById(rolePermission.getRoleId())).collect(Collectors.toList());
    }

    private List<SystemPermission> getSystemPermissions() {
        return this.permissionRepository.selectList(new QueryWrapper<>());
    }

    private SystemRolePermissionDTO populateRolePermissionDTO(SystemPermission permission, List<SystemRoleDTO> roles) {
        return new SystemRolePermissionDTO(permission, roles);
    }
}
