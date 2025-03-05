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
package io.github.photowey.jwt.authcenter.property;

import io.github.photowey.jwt.authcenter.core.constant.AuthorityConstants;
import io.github.photowey.jwt.authcenter.core.util.Collections;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.cors.CorsConfiguration;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@code SecurityProperties}.
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/15
 */
@Data
@Validated
public class SecurityProperties implements Serializable {

    @Serial
    private static final long serialVersionUID = 4445655258372899686L;

    @Valid
    private CorsConfiguration cors = new CorsConfiguration();
    @Valid
    private Deploy deploy = new Deploy();
    @Valid
    private Auth auth = new Auth();
    @Valid
    private Token token = new Token();
    @Valid
    private Mock mock = new Mock();
    @Valid
    private IgnorePath ignores = new IgnorePath();
    @Valid
    private SelectPath selects = new SelectPath();

    @Data
    @Validated
    public static class Deploy implements Serializable {

        @Serial
        private static final long serialVersionUID = 4154940705697685071L;

        @NotBlank(message = "请配置: Nginx 地址")
        private String nginx;
        @NotBlank(message = "请配置: API Gateway 地址")
        private String gateway;
    }

    @Data
    @Validated
    public static class Auth implements Serializable {

        @Serial
        private static final long serialVersionUID = -5177116394349152348L;

        @Valid
        private Issuer issuer = new Issuer();
        @Valid
        private Jwt jwt = new Jwt();
        @Valid
        private Activated activated = new Activated();
        @Valid
        private Loader loader = new Loader();
        @Valid
        private Checker checker = new Checker();

        public Issuer issuer() {
            return issuer;
        }

        public Jwt jwt() {
            return jwt;
        }

        public Activated activated() {
            return activated;
        }

        public Loader loader() {
            return loader;
        }

        public Checker checker() {
            return checker;
        }
    }

    @Data
    @Validated
    public static class Issuer implements Serializable {

        @Serial
        private static final long serialVersionUID = -2512203819892599541L;

        @NotBlank(message = "请配置: 令牌签发机构: 秘钥")
        private String secret;
        @NotBlank(message = "请配置: 令牌签发机构秘: URI")
        private String uri;

        public String secret() {
            return secret;
        }

        public String uri() {
            return uri;
        }
    }

    @Data
    @Validated
    public static class Jwt implements Serializable {

        @Serial
        private static final long serialVersionUID = 7166000514675396420L;

        @NotBlank(message = "请配置: `jwt` 秘钥")
        private String secret;
        private String authorities = "ath";

        /**
         * 1 day
         */
        private long tokenValidityInSeconds = TimeUnit.DAYS.toSeconds(1);
        /**
         * 7 days
         */
        private long tokenValidityInSecondsForRememberMe = TimeUnit.DAYS.toSeconds(7);

        public String secret() {
            return secret;
        }

        public String authorities() {
            return authorities;
        }

        public long tokenValidityInSeconds() {
            return tokenValidityInSeconds;
        }

        public long tokenValidityInSecondsForRememberMe() {
            return tokenValidityInSecondsForRememberMe;
        }
    }

    @Data
    @Validated
    public static class Token implements Serializable {

        @Serial
        private static final long serialVersionUID = 5426065480527769412L;

        private String header = AuthorityConstants.AUTHORIZATION_HEADER;
        private String innerHeader = AuthorityConstants.AUTHORIZATION_TOKEN_PREFIX_PROXY;

        public String header() {
            return header;
        }

        public String innerHeader() {
            return innerHeader;
        }
    }

    @Data
    @Validated
    public static class Mock implements Serializable {

        @Serial
        private static final long serialVersionUID = -8759736364181583010L;

        private boolean enabled = false;
        private String secret = "io.github.photowey.platform.authorize.security.mock.salt";
    }

    @Data
    @Validated
    public static class IgnorePath implements Serializable {

        @Serial
        private static final long serialVersionUID = -2486718833719722665L;

        private List<String> paths;

        public List<String> paths() {
            if (Objects.isNull(this.paths)) {
                this.paths = Collections.emptyList();
            }

            return paths;
        }
    }

    @Data
    @Validated
    public static class SelectPath implements Serializable {

        @Serial
        private static final long serialVersionUID = -8837252384796346137L;

        private List<String> paths;

        public List<String> paths() {
            if (Objects.isNull(this.paths)) {
                this.paths = Collections.emptyList();
            }

            return paths;
        }
    }

    @Data
    @Validated
    public static class Activated implements Serializable {

        @Serial
        private static final long serialVersionUID = 4088030375798553446L;

        /**
         * 未激活是否允许登录
         */
        private boolean enabled = true;
    }

    @Data
    @Validated
    public static class Loader implements Serializable {

        @Serial
        private static final long serialVersionUID = 4088030375798553446L;

        /**
         * 权限加载器
         * |- 默认缓存加载器
         */
        private String name = "cache";

        public String name() {
            return name;
        }
    }

    @Data
    @Validated
    public static class Checker implements Serializable {

        @Serial
        private static final long serialVersionUID = 4088030375798553446L;

        /**
         * 账号检查器
         */
        private String name = "local";

        public String name() {
            return name;
        }
    }

    // ----------------------------------------------------------------

    public CorsConfiguration cors() {
        return cors;
    }

    public Auth auth() {
        return auth;
    }

    public Token token() {
        return token;
    }

    public Mock mock() {
        return mock;
    }

    public IgnorePath ignores() {
        return ignores;
    }

    public SelectPath selects() {
        return selects;
    }

    // ----------------------------------------------------------------

    public static String getPrefix() {
        return "io.github.photowey.authorize.security";
    }
}



