package com.photowey.oauth2.authentication.minigram.service;

import com.photowey.oauth2.authentication.minigram.request.MinigramRequest;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@code MinigramUserDetailsService}
 *
 * @author photowey
 * @date 2022/07/28
 * @since 1.0.0
 */
public interface MinigramUserDetailsService {

    UserDetails register(MinigramRequest request);

    UserDetails loadByOpenId(String clientId, String openId);

}
