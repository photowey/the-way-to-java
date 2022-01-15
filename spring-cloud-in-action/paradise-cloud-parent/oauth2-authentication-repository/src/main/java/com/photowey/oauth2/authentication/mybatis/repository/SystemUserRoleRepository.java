package com.photowey.oauth2.authentication.mybatis.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUserRole;

import java.util.List;

/**
 * {@code SystemUserRoleRepository}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface SystemUserRoleRepository extends BaseMapper<SystemUserRole> {

    List<SystemUserRole> findByUserId(Long userId);
}
