package com.photowey.oauth2.inner.token.security.filter;

import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import com.photowey.oauth2.inner.token.security.parser.AccessTokenParser;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code AccessTokenFilter}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
public class AccessTokenFilter extends OncePerRequestFilter {

    private final AccessTokenParser accessTokenParser;

    public AccessTokenFilter(AccessTokenParser accessTokenParser) {
        this.accessTokenParser = accessTokenParser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader(TokenConstants.GATEWAY_TOKEN_NAME);
        if (StringUtils.hasText(accessToken)) {
            AuthUser authUser = this.accessTokenParser.parseAccessToken(accessToken);
            request.setAttribute(TokenConstants.AUTH_USER_KEY, authUser);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (StringUtils.hasText(accessToken)) {
                request.removeAttribute(TokenConstants.AUTH_USER_KEY);
            }
        }
    }
}
