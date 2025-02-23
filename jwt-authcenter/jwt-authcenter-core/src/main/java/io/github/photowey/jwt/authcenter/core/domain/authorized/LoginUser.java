/*
 * Copyright © 2025 the original author or authors.
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
package io.github.photowey.jwt.authcenter.core.domain.authorized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.github.photowey.jwt.authcenter.core.cache.AuthorizedCache;
import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.domain.role.SafeRole;
import io.github.photowey.jwt.authcenter.core.domain.scope.SafeScope;
import io.github.photowey.jwt.authcenter.core.util.Arrays;
import io.github.photowey.jwt.authcenter.core.util.Collections;
import io.github.photowey.jwt.authcenter.core.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * {@code LoginUser}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/01/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable, UserDetails, SafeRole, SafeScope {

    @Serial
    private static final long serialVersionUID = 4243030584001603899L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long authId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String username;
    private String mobile;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long principalId;
    private Integer principalType;

    private Integer type;
    private Integer status;
    private Integer authorizeStatus;

    @JsonIgnore
    private String platform;
    @JsonIgnore
    private String client;

    @JsonIgnore
    private String password = AuthorityConstants.DEFAULT_PASSWORD;

    @JsonIgnore
    private Set<String> authoritySet = new HashSet<>();

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

    /**
     * 授权作用域
     */
    private Set<String> scopes = new HashSet<>();
    private Set<String> roles = new HashSet<>();

    private Boolean dummy = false;

    private String token;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public LoginUser(Set<String> authoritySet) {
        this.authoritySet = authoritySet;
        this.dummy = true;
    }

    public LoginUser(Long userId, Set<String> authoritySet) {
        this.userId = userId;
        this.authoritySet = authoritySet;
    }

    @JsonIgnore
    public Long getUserId() {
        return this.userId;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return Strings.defaultIfEmpty(this.password, AuthorityConstants.DEFAULT_PASSWORD);
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(this.authoritySet).orElse(new HashSet<>(0)).stream()
            .map(authority -> {
                String authorityz =
                    authority.startsWith(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX)
                        ? authority
                        : AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX + authority;
                return new SimpleGrantedAuthority(authorityz);
            }).collect(Collectors.toSet());
    }

    public void appendScopes(String... scopes) {
        if (Arrays.isNotEmpty(scopes)) {
            if (Collections.isEmpty(this.scopes)) {
                this.scopes = Stream.of(scopes).collect(Collectors.toSet());

                return;
            }

            this.scopes.addAll(Stream.of(scopes).collect(Collectors.toSet()));
        }
    }

    public void appendScopes(Set<String> scopeSet) {
        if (Collections.isNotEmpty(scopeSet)) {
            if (Collections.isEmpty(this.scopes)) {
                this.scopes = scopeSet;

                return;
            }

            this.scopes.addAll(scopeSet);
        }
    }

    public void appendAuthoritySets(String... authorities) {
        if (Arrays.isNotEmpty(authorities)) {
            if (Collections.isEmpty(this.authoritySet)) {
                this.authoritySet = Stream.of(authorities).collect(Collectors.toSet());

                return;
            }

            this.authoritySet.addAll(Stream.of(authorities).collect(Collectors.toSet()));
        }
    }

    public void appendAuthoritySets(Set<String> authoritySet) {
        if (Collections.isNotEmpty(authorities)) {
            if (Collections.isEmpty(this.authoritySet)) {
                this.authoritySet = authoritySet;

                return;
            }

            this.authoritySet.addAll(authoritySet);
        }
    }

    public void appendRoles(String... roles) {
        if (Arrays.isNotEmpty(roles)) {
            if (Collections.isEmpty(this.roles)) {
                this.roles = Stream.of(roles).collect(Collectors.toSet());

                return;
            }

            this.roles.addAll(Stream.of(roles).collect(Collectors.toSet()));
        }
    }

    public void appendRoles(Set<String> roleSet) {
        if (Collections.isNotEmpty(roleSet)) {
            if (Collections.isEmpty(this.roles)) {
                this.roles = roleSet;

                return;
            }

            this.roles.addAll(roleSet);
        }
    }

    // ----------------------------------------------------------------

    public String determineStringPrincipalType() {
        return String.valueOf(this.getPrincipalType());
    }

    // ----------------------------------------------------------------

    public boolean determineScopeIsNotContains(String scope) {
        return !this.determineScopeIsContains(scope);
    }

    public boolean determineScopeIsContains(String scope) {
        return this.getScopes().stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet())
            .contains(scope);
    }

    public boolean determineRoleIsNotContains(String role) {
        return !this.determineRoleIsContains(role);
    }

    public boolean determineRoleIsContains(String role) {
        return this.getRoles()
            .stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet())
            .contains(role);
    }

    public boolean determineAuthoritySetIsNotContains(String authority) {
        return !this.determineAuthoritySetIsContains(authority);
    }

    public boolean determineAuthoritySetIsContains(String authority) {
        return this.getAuthoritySet()
            .stream()
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_SECURITY_AUTHORITY_PREFIX, ""))
            .map(it -> it.replaceAll(AuthorityConstants.SPRING_OAUTH2_SCOPE_PREFIX, ""))
            .collect(Collectors.toSet()).contains(authority);
    }

    // ----------------------------------------------------------------

    public static boolean determineAuthenticated(LoginUser loginUser) {
        return null != loginUser
            && null != loginUser.getDummy()
            && !loginUser.getDummy();
    }

    // ----------------------------------------------------------------

    public boolean determineIsDummy() {
        return this.getDummy();
    }

    // ----------------------------------------------------------------

    @Override
    public Set<String> getRoles() {
        if (null == this.roles) {
            this.roles = Collections.emptySet();
        }

        return this.roles;
    }

    @Override
    public Set<String> getScopes() {
        if (null == this.scopes) {
            this.scopes = Collections.emptySet();
        }

        return this.scopes;
    }

    public Set<String> roles() {
        return this.getRoles();
    }

    public Set<String> scopes() {
        return this.getScopes();
    }

    public Set<String> getAuthoritySet() {
        if (null == this.authoritySet) {
            this.authoritySet = Collections.emptySet();
        }

        return this.authoritySet;
    }

    // ----------------------------------------------------------------

    public static LoginUser createDummy() {
        Set<String> emptySet = Collections.emptySet();

        return LoginUser.builder()
            .userId(AuthorityConstants.DUMMY_LOGIN_USER_ID)
            .dummy(true)
            .authoritySet(emptySet)
            .roles(emptySet)
            .scopes(emptySet)
            .build();
    }

    // ----------------------------------------------------------------

    public Long authId() {
        return authId;
    }

    public Long userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String mobile() {
        return mobile;
    }

    public Long principalId() {
        return principalId;
    }

    public Integer principalType() {
        return principalType;
    }

    public String platform() {
        return platform;
    }

    public String client() {
        return client;
    }

    public Integer type() {
        return type;
    }

    public Integer status() {
        return status;
    }

    public Integer authorizeStatus() {
        return authorizeStatus;
    }

    public String token() {
        return token;
    }

    // ----------------------------------------------------------------

    public void injectSets(AuthorizedCache target) {
        this.appendAuthoritySets(target.authorities());
        this.appendRoles(target.roles());
        this.appendScopes(target.scopes());
    }

}


