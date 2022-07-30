/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
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
package com.photowey.oauth2.authentication.minigram.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * {@code MinigramSessionKeyResponse}
 *
 * @author photowey
 * @date 2022/07/28
 * @see * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
 * @since 1.0.0
 */
@Data
public class MinigramSessionKeyResponse implements Serializable {

    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private Integer code;
    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String message;
    /**
     * 会话密钥
     */
    @JsonProperty("session_key")
    private String sessionKey;
    /**
     * 用户唯一标识
     */
    @JsonProperty("openid")
    private String openId;
    /**
     * 用户在开放平台唯一标识
     */
    @JsonProperty("unionid")
    private String unionId;

}
