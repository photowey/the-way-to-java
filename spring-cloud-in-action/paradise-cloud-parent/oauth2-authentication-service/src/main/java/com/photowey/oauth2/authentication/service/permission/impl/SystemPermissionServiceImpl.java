package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemPermission;
import com.photowey.oauth2.authentication.mybatis.repository.SystemPermissionRepository;
import com.photowey.oauth2.authentication.service.permission.SystemPermissionService;
import org.springframework.stereotype.Service;

/**
 * {@code PermissionServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemPermissionServiceImpl extends ServiceImpl<SystemPermissionRepository, SystemPermission> implements SystemPermissionService {

}
