package com.photowey.oauth2.authentication.minigram.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "errcode")
    private Integer code;
    /**
     * 错误信息
     */
    @JSONField(name = "errmsg")
    private String message;
    /**
     * 会话密钥
     */
    @JSONField(name = "session_key")
    private String sessionKey;
    /**
     * 用户唯一标识
     */
    @JSONField(name = "openid")
    private String openId;
    /**
     * 用户在开放平台唯一标识
     */
    @JSONField(name = "unionid")
    private String unionId;

}
