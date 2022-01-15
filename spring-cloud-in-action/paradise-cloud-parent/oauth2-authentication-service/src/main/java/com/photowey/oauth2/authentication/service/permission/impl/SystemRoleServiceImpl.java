package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemRole;
import com.photowey.oauth2.authentication.mybatis.repository.SystemRoleRepository;
import com.photowey.oauth2.authentication.service.permission.SystemRoleService;
import org.springframework.stereotype.Service;

/**
 * {@code SystemRoleServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleRepository, SystemRole> implements SystemRoleService {
}
