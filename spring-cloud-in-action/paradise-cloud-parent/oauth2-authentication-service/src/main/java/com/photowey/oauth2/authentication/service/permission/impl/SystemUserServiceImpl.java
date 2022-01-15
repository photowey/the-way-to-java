package com.photowey.oauth2.authentication.service.permission.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUser;
import com.photowey.oauth2.authentication.mybatis.repository.SystemUserRepository;
import com.photowey.oauth2.authentication.service.permission.SystemUserService;
import org.springframework.stereotype.Service;

/**
 * {@code SystemUserServiceImpl}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserRepository, SystemUser> implements SystemUserService {
}
