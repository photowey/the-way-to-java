package com.photowey.oauth2.authentication.minigram.service;

import com.photowey.oauth2.authentication.minigram.domain.MinigramClient;

/**
 * {@code MinigramClientService}
 *
 * @author photowey
 * @date 2022/07/28
 * @since 1.0.0
 */
public interface MinigramClientService {

    MinigramClient get(String clientId);
}
