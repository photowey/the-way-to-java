package com.photowey.oauth2.authentication.mybatis.repository;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUser;

/**
 * {@code SystemUserRepository}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface SystemUserRepository extends BaseMapper<SystemUser> {

    SystemUser findByUserNameAndStatus(String userName, Integer status);
}
