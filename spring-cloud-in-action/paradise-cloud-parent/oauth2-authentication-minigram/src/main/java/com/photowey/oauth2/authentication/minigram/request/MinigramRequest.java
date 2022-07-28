package com.photowey.oauth2.authentication.minigram.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * {@code MinigramRequest}
 *
 * @author photowey
 * @date 2022/07/28
 * @see * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/user-info/wx.getUserProfile.html
 * @since 1.0.0
 */
@Data
public class MinigramRequest implements Serializable {

    private UserInfo userInfo;
    private String rawData;
    private String signature;
    private String encryptedData;
    private String iv;
    private String cloudId;

    private String clientId;
    private String openId;
    private String unionId;

    @Data
    public static class UserInfo implements Serializable {

        private static final long serialVersionUID = -7074537166194570840L;

        /**
         * <pre>
         * {
         *   "nickName": "Band",
         *   "gender": 1,
         *   "language": "zh_CN",
         *   "city": "Guangzhou",
         *   "province": "Guangdong",
         *   "country": "CN",
         *   "avatarUrl": "http://wx.qlogo.cn/mmopen/vi_32/1vZvI39NWFQ9XM4LtQpFrQJ1xlgZxx3w7bQxKARol6503Iuswjjn6nIGBiaycAjAtpujxyzYsrztuuICqIM5ibXQ/0"
         * }
         * </pre>
         */

        @JsonProperty("nickName")
        private String nickName;
        @JsonProperty("gender")
        private Integer gender;
        @JsonProperty("language")
        private String language;
        @JsonProperty("city")
        private String city;
        @JsonProperty("province")
        private String province;
        @JsonProperty("country")
        private String country;
        @JsonProperty("avatarUrl")
        private String avatarUrl;


    }

}
