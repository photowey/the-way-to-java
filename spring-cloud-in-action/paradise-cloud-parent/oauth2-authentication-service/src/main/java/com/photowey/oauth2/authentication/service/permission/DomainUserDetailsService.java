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
package com.photowey.oauth2.authentication.service.permission;

import com.photowey.oauth2.authentication.core.constant.TokenConstants;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUser;
import com.photowey.oauth2.authentication.core.domain.entity.SystemUserRole;
import com.photowey.oauth2.authentication.core.model.SecurityUser;
import com.photowey.oauth2.authentication.core.util.OAuthUtils;
import com.photowey.oauth2.authentication.mybatis.repository.SystemRoleRepository;
import com.photowey.oauth2.authentication.mybatis.repository.SystemUserRepository;
import com.photowey.oauth2.authentication.mybatis.repository.SystemUserRoleRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * {@code DomainUserDetailsService}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Service
public class DomainUserDetailsService implements UserDetailsService {

    private SystemUserRepository userRepository;
    private SystemUserRoleRepository userRoleRepository;
    private SystemRoleRepository roleRepository;

    public DomainUserDetailsService(
            SystemUserRepository userRepository, SystemUserRoleRepository userRoleRepository, SystemRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser systemUser = this.userRepository.findByUserNameAndStatus(username, 1);
        if (Objects.isNull(systemUser)) {
            throw new UsernameNotFoundException(String.format("The username:%s not found,check Please!", username));
        }
        List<SystemUserRole> userRoles = this.userRoleRepository.findByUserId(systemUser.getId());
        List<String> roles = new ArrayList<>();
        userRoles.forEach((userRole) -> {
            Optional.ofNullable(this.roleRepository.selectById(userRole.getRoleId())).ifPresent(systemRole -> roles.add(TokenConstants.ROLE_PREFIX + systemRole.getCode()));
        });

        return SecurityUser.builder()
                .userId(systemUser.getUserId())
                .username(systemUser.getUserName())
                .password(systemUser.getPassword())
                .authorities(AuthorityUtils.createAuthorityList(OAuthUtils.toArray(roles, String.class)))
                .build();
    }
}
