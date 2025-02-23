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
package io.github.photowey.jwt.authcenter.core.constant;

/**
 * {@code MessageConstants}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2025/02/24
 */
public interface MessageConstants {

    String AUTHORIZE_BAD_CREDENTIALS = "账号或密码错误";
    String AUTHORIZE_BAD_USER_ID = "标识错误";

    String AUTHORIZE_UPDATE_PASSWORD_BAD_CREDENTIALS = "密码错误";
    String AUTHORIZE_BAD_CAPTCHA = "验证码错误";

    String AUTHORIZE_ACCOUNT_REGISTERED = "账号已注册";
    String AUTHORIZE_ACCOUNT_NOT_ACTIVATE = "账号未激活";
    String AUTHORIZE_ACCOUNT_NOT_REGISTER = "账号未注册";

    String AUTHORIZE_ACCOUNT_FAILED = "认证失败.t";
    String AUTHORIZE_ACCOUNT_UNAUTHORIZED = "未认证.t";
    String AUTHORIZE_TOKEN_EXPIRED = "令牌已过期";
    String AUTHORIZE_TOKEN_SIGNATURE_INVALID = "令牌不授信";
    String AUTHORIZE_REQUEST_INVALID = "请求不授信";
}

