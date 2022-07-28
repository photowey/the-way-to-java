package com.photowey.oauth2.authentication.minigram.provider;

import com.photowey.oauth2.authentication.minigram.request.MinigramRequest;
import com.photowey.oauth2.authentication.minigram.service.MinigramUserDetailsService;
import com.photowey.oauth2.authentication.minigram.token.MinigramAuthenticationToken;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Objects;

/**
 * {@code MinigramAuthenticationProvider}
 *
 * @author photowey
 * @date 2022/07/28
 * @since 1.0.0
 */
public class MinigramAuthenticationProvider implements AuthenticationProvider, MessageSourceAware {

    private final GrantedAuthoritiesMapper authoritiesMapper;
    private final MinigramUserDetailsService minigramUserDetailsService;
    private MessageSourceAccessor messageSourceAccessor;

    public MinigramAuthenticationProvider(MinigramUserDetailsService minigramUserDetailsService) {
        this.authoritiesMapper = new NullAuthoritiesMapper();
        this.minigramUserDetailsService = minigramUserDetailsService;
        this.messageSourceAccessor = SpringSecurityMessageSource.getAccessor();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(MinigramAuthenticationToken.class, authentication,
                () -> messageSourceAccessor.getMessage(
                        "MinigramAuthenticationProvider.onlySupports",
                        "Only MinigramAuthenticationToken is supported"
                )
        );

        MinigramAuthenticationToken unAuthenticationToken = (MinigramAuthenticationToken) authentication;
        MinigramRequest credentials = (MinigramRequest) unAuthenticationToken.getCredentials();
        UserDetails userDetails = this.minigramUserDetailsService.loadByOpenId(credentials.getClientId(), credentials.getOpenId());
        if (Objects.isNull(userDetails)) {
            userDetails = this.minigramUserDetailsService.register(credentials);
        }

        return this.createSuccessAuthentication(authentication, userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MinigramAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
    }

    private Authentication createSuccessAuthentication(Authentication authentication, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = this.authoritiesMapper.mapAuthorities(userDetails.getAuthorities());
        MinigramAuthenticationToken authenticationToken = new MinigramAuthenticationToken(userDetails, authorities);
        authenticationToken.setDetails(authentication.getPrincipal());

        return authenticationToken;
    }
}
