package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUserRole;
import com.photowey.oauth2.authentication.mybatis.repository.SystemUserRoleRepository;
import com.photowey.oauth2.authentication.service.permission.SystemUserRoleService;
import org.springframework.stereotype.Service;

/**
 * {@code SystemUserRoleServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleRepository, SystemUserRole> implements SystemUserRoleService {
}
