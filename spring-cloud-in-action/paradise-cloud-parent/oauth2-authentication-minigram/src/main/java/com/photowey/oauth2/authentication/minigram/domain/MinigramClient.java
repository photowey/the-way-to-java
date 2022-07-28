package com.photowey.oauth2.authentication.minigram.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * {@code MinigramClient}
 *
 * @author photowey
 * @date 2022/07/28
 * @since 1.0.0
 */
@Data
public class MinigramClient implements Serializable {

    private String clientId;
    private String appId;
    private String appSecret;
}
