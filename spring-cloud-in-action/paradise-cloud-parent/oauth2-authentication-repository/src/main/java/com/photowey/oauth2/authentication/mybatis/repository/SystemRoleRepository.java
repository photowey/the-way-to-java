package com.photowey.oauth2.authentication.mybatis.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.photowey.oauth2.authentication.core.domain.dto.SystemRoleDTO;
import com.photowey.oauth2.authentication.core.domain.entity.SystemRole;
import org.apache.ibatis.annotations.Param;

/**
 * {@code SystemRoleRepository}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
public interface SystemRoleRepository extends BaseMapper<SystemRole> {

    SystemRoleDTO findById(@Param("roleId") Long roleId);

}
