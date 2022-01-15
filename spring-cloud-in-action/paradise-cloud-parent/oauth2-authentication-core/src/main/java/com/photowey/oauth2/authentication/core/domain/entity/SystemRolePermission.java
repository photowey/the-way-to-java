package com.photowey.oauth2.authentication.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_permission")
public class SystemRolePermission {

    private Long id;
    private Long roleId;
    private Long permissionId;
}
