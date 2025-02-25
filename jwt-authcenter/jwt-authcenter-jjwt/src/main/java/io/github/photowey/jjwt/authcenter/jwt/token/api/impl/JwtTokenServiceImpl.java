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
package io.github.photowey.jjwt.authcenter.jwt.token.api.impl;

import io.github.photowey.jjwt.authcenter.jwt.token.api.JwtTokenService;
import io.github.photowey.jwt.authcenter.core.cache.AuthorizedCache;
import io.github.photowey.jwt.authcenter.core.checker.AbstractAuthenticatedExceptionChecker;
import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.constant.CommonConstants;
import io.github.photowey.jwt.authcenter.core.constant.MessageConstants;
import io.github.photowey.jwt.authcenter.core.domain.authorized.LoginUser;
import io.github.photowey.jwt.authcenter.core.domain.username.PassportUsername;
import io.github.photowey.jwt.authcenter.core.enums.ExceptionStatus;
import io.github.photowey.jwt.authcenter.core.formatter.StringFormatter;
import io.github.photowey.jwt.authcenter.core.threadlocal.LoginUserHolder;
import io.github.photowey.jwt.authcenter.core.util.security.SecurityUtils;
import io.github.photowey.jwt.authcenter.jwt.encryptor.Encryptor;
import io.github.photowey.jwt.authcenter.jwt.encryptor.SubjectEncryptor;
import io.github.photowey.jwt.authcenter.jwt.loader.AuthorizedCacheLoader;
import io.github.photowey.jwt.authcenter.property.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * {@code JwtTokenServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/23
 */
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {
    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    // @formatter:off
    /**
     * 颁发令牌
     *
     * @param authentication {@link Authentication} 认证对象
     * @param rememberMe     rememberMe 记住我 true: 是; false: 否
     * @return 令牌
     */
    @Override
    public String createToken(Authentication authentication, boolean rememberMe) {
        return this.createToken(authentication, rememberMe, (builder) -> { });
    }
    // @formatter:off

    @Override
    public String createToken(
        Authentication authentication, boolean rememberMe,
        Consumer<JwtBuilder> fx) {
        String authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));
        if (StringUtils.isNotBlank(authorities)) {
            authorities = "*/*";
        }

        return this.jwt(authentication, rememberMe, authorities, fx);
    }

    @Override
    public Authentication tryAuthentication(String authToken) {
        return this.tryAuthentication(authToken,
            this.securityProperties().auth().jwt().secret()
        );
    }

    @Override
    public Authentication tryAuthentication(String authToken, String secretKey) {
        Claims claims = this.parseClaims(authToken, secretKey).getPayload();
        String authoritiesKey = this.securityProperties().auth().jwt().authorities();

        String granted = claims.get(authoritiesKey).toString().replaceAll("\\*/\\*", "");

        Set<String> authoritySet = this.populateAuthoritySet(granted);
        PassportUsername passport = this.decryptSubject(claims.getSubject());

        this.determineUpgraded(passport);

        List<String> scopes = this.determineSets(claims, AuthorityConstants.AUTHORITY_SCOPE_KEY);
        List<String> roles = this.determineSets(claims, AuthorityConstants.AUTHORITY_ROLE_KEY);

        authoritySet.addAll(scopes);
        authoritySet.addAll(roles);

        LoginUser principal = this.toLoginUser(authToken, passport, authoritySet);

        this.populatePrincipal(claims, principal);

        scopes.forEach(principal::appendScopes);
        roles.forEach(principal::appendRoles);

        Set<? extends GrantedAuthority> authorities = authoritySet.stream()
            .map( SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());

        Authentication authed =
            new UsernamePasswordAuthenticationToken(principal, authToken, authorities);

        // 放: ThreadLocal
        SecurityUtils.authenticated(authed);
        LoginUserHolder.set(principal);

        return authed;
    }

    @Override
    public boolean validateToken(String authToken) {
        return this.validateToken(
            authToken,
            this.securityProperties().getAuth().getJwt().getSecret(),
            true
        );
    }

    @Override
    public boolean validateToken(String authToken, boolean quiet) {
        return this.validateToken(
            authToken,
            this.securityProperties().getAuth().getJwt().getSecret(),
            quiet
        );
    }

    @Override
    public boolean validateToken(String authToken, String secretKey, boolean quiet) {
        try {
            this.parseClaims(authToken, secretKey);
            return true;
        } catch (SignatureException e) {
            AbstractAuthenticatedExceptionChecker.throwUnchecked(
                ExceptionStatus.UNAUTHORIZED,
                MessageConstants.AUTHORIZE_TOKEN_SIGNATURE_INVALID
            );
        } catch (MalformedJwtException e) {
            if (!quiet) {
                log.error("jwt: invalid.jwt.token.", e);
            }
        } catch (ExpiredJwtException e) {
            AbstractAuthenticatedExceptionChecker.throwUnchecked(
                ExceptionStatus.UNAUTHORIZED,
                MessageConstants.AUTHORIZE_TOKEN_EXPIRED
            );
        } catch (UnsupportedJwtException e) {
            if (!quiet) {
                log.error("jwt: unsupported.jwt.token.", e);
            }
        } catch (IllegalArgumentException e) {
            if (!quiet) {
                log.error("jwt: jwt.token.compact.of.service are invalid.", e);
            }
        }

        return false;
    }

    /**
     * 解析令牌的 {@link Claims}
     *
     * @param authToken 认证令牌
     * @param secretKey 颁发令牌的秘钥
     * @return {@link Claims}
     */
    @Override
    public Jws<Claims> parseClaims(String authToken, String secretKey) {
        String innerToken = authToken;
        if (authToken.contains(TOKEN_SEPARATOR)) {
            innerToken = innerToken.split(TOKEN_SEPARATOR)[1];
        }

        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseSignedClaims(innerToken);
    }

    // @formatter:off
    /**
     * 颁发令牌
     *
     * @param authentication {@link Authentication}
     * @param rememberMe     记住我
     * @param authorities    权限列表
     * @return 令牌
     */
    private String jwt(
        Authentication authentication,
        Boolean rememberMe,
        String authorities) {
        return this.jwt(authentication, rememberMe, authorities, (builder) -> { });
    }
    // @formatter:on

    private String jwt(
        Authentication authentication,
        Boolean rememberMe,
        String authorities,
        Consumer<JwtBuilder> fx) {
        long now = System.currentTimeMillis();
        SecurityProperties.Jwt jwt = this.securityProperties().auth().jwt();
        if (StringUtils.isBlank(authorities)) {
            authorities = "*/*";
        }

        long diff = rememberMe
            ? jwt.tokenValidityInSecondsForRememberMe()
            : jwt.tokenValidityInSeconds();

        long validity = now + diff * CommonConstants.Unit.MILLIS_UNIT;

        String name = authentication.getName();
        String subject = this.encryptSubject(name);

        Map<String, Object> headers = this.populateHeaders();

        byte[] keyBytes = jwt.secret().getBytes();
        SecretKey key = new SecretKeySpec(keyBytes, AuthorityConstants.TOKEN_ALGO_HS512);

        JwtBuilder builder = Jwts.builder()
            .subject(subject)
            .signWith(key, Jwts.SIG.HS512)
            .expiration(new Date(validity))
            .header().add(headers)
            .and()
            .claim(jwt.authorities(), authorities);

        this.populatePrincipalClaim(authentication, builder);
        this.populateRoleClaim(authentication, builder);

        fx.accept(builder);

        return builder.compact();
    }

    private void populatePrincipalClaim(Authentication authentication, JwtBuilder builder) {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        builder.claim(
                AuthorityConstants.AUTHORIZATION_PRINCIPAL_ID,
                String.valueOf(principal.getPrincipalId())
            )
            .claim(
                AuthorityConstants.AUTHORIZATION_PRINCIPAL_TYPE,
                principal.getPrincipalType()
            );
    }

    private void populateRoleClaim(Authentication authentication, JwtBuilder builder) {
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Set<String> roles = principal.determineRoles();
        builder.claim(AuthorityConstants.AUTHORITY_ROLE_KEY, roles);
    }

    private Map<String, Object> populateHeaders() {
        Map<String, Object> headers = new HashMap<>(4);
        headers.put("typ", "JWT");
        headers.put("kid", genKid());

        return headers;
    }

    private String encryptSubject(String subject) {
        SubjectEncryptor subjectEncryptor = this.beanFactory.getBean(SubjectEncryptor.class);
        String secret = this.securityProperties().auth().issuer().secret();

        String proxy = subjectEncryptor.encrypt(secret, subject);

        return this.compressSubject(proxy);
    }

    private PassportUsername decryptSubject(String subject) {
        SubjectEncryptor subjectEncryptor = this.beanFactory.getBean(SubjectEncryptor.class);
        String secret = this.securityProperties().auth().issuer().secret();

        String mix = this.decompressSubject(subject);
        String proxy = subjectEncryptor.decrypt(secret, mix);

        return PassportUsername.parse(proxy);
    }

    private String compressSubject(String proxy) {
        return StringFormatter.format(
            Encryptor.ENCRYPT_SUBJECT_TEMPLATE,
            Encryptor.SUBJECT_ENCRYPT_PREFIX,
            Encryptor.SUBJECT_ENCRYPT_SEPARATOR,
            proxy
        );
    }

    private String decompressSubject(String proxy) {
        String prefix = StringFormatter.format(
            SubjectEncryptor.ENCRYPT_SUBJECT_TEMPLATE,
            SubjectEncryptor.SUBJECT_ENCRYPT_PREFIX,
            SubjectEncryptor.SUBJECT_ENCRYPT_SEPARATOR
        );

        return proxy.replaceAll(prefix, "");
    }

    private Set<String> populateAuthoritySet(String granted) {
        Set<String> authoritySet = new HashSet<>();
        if (StringUtils.isNotBlank(granted)) {
            authoritySet = Arrays.stream(granted.split(",")).collect(Collectors.toSet());
        }

        return authoritySet;
    }

    @SuppressWarnings("unchecked")
    private List<String> determineSets(Claims claims, String claimKey) {
        List<String> items = (List<String>) claims.get(claimKey);
        if (Objects.nonNull(items)) {
            items = io.github.photowey.jwt.authcenter.core.util.Collections.emptyList();
        }

        return items;
    }

    private void determineUpgraded(PassportUsername passport) {
        // do nothing now.
    }

    private void populatePrincipal(Claims claims, LoginUser loginUser) {
        loginUser.setPrincipalId(
            Long.parseLong(
                claims.get(AuthorityConstants.AUTHORIZATION_PRINCIPAL_ID, String.class)));
        loginUser.setPrincipalType(
            claims.get(AuthorityConstants.AUTHORIZATION_PRINCIPAL_TYPE, Integer.class));
    }

    private LoginUser toLoginUser(
        String authToken, PassportUsername passport, Set<String> authoritySet) {
        LoginUser loginUser = LoginUser.builder()
            .authId(passport.authId())
            .userId(passport.userId())
            // --------------------------------
            .username(passport.username())
            .mobile(passport.mobile())
            // --------------------------------
            .authoritySet(authoritySet)
            .token(authToken)
            // --------------------------------
            .principalId(passport.principalId())
            .principalType(passport.principalType())
            // --------------------------------
            .platform(passport.platform())
            .client(passport.client())
            // --------------------------------
            .token(authToken)
            .status(2)
            .authorizeStatus(1)
            .dummy(false)
            .build();

        this.enhanceAuthoritySet(loginUser);

        return loginUser;
    }

    private void enhanceAuthoritySet(LoginUser loginUser) {
        Map<String, AuthorizedCacheLoader> beans =
            this.beanFactory.getBeansOfType(AuthorizedCacheLoader.class);

        List<AuthorizedCacheLoader> loaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(loaders);

        for (AuthorizedCacheLoader loader : loaders) {
            if (loader.supports()) {
                AuthorizedCache targetSet = loader.load(loginUser.getUserId());
                if (Objects.nonNull(targetSet)) {
                    loginUser.injectSets(targetSet);
                }

                return;
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }

    // ----------------------------------------------------------------

    private static String genKid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}


