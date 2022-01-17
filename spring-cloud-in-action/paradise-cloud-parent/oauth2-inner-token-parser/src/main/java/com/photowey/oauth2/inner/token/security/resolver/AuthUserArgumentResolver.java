package com.photowey.oauth2.inner.token.security.resolver;

import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * {@code AuthUserArgumentResolver}
 *
 * @author photowey
 * @date 2022/01/17
 * @since 1.0.0
 */
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return AuthUser.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        return request.getAttribute(TokenConstants.AUTH_USER_KEY);
    }
}
