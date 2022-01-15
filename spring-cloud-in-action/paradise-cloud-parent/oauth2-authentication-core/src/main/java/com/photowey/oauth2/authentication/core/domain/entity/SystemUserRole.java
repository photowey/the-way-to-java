package com.photowey.oauth2.authentication.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
public class SystemUserRole {
    private Long id;
    private Long userId;
    private Long roleId;
}
