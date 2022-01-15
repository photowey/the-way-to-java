package com.photowey.oauth2.authentication.server.config;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.core.constant.TokenConstants;
import com.photowey.oauth2.authentication.core.model.SecurityUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static com.photowey.oauth2.authentication.core.constant.TokenConstants.AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME;
import static com.photowey.oauth2.authentication.core.constant.TokenConstants.AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL;

/**
 * {@code AccessTokenConfigure}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Configuration
public class AccessTokenConfigure {

    private final RestTemplate restTemplate;

    @Value("${oauth.server.host:localhost")
    private String authServerHost;
    @Value("${oauth.server.port:${server.port}}")
    private Integer authServerPort;

    public AccessTokenConfigure(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean

    public TokenStore tokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenEnhancer();
        jwtAccessTokenConverter.setVerifier(new org.springframework.security.jwt.crypto.sign.RsaVerifier(this.retrievePublicKey()));
        return jwtAccessTokenConverter;
    }

    private String retrievePublicKey() {
        final ClassPathResource classPathResource = new ClassPathResource(AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME);
        try (
                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()))
        ) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            String url = String.format("http://%s:%d%s", authServerHost, authServerPort, AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL);
            final String responseValue = this.restTemplate.getForObject(url, String.class);
            return JSON.parseObject(JSON.parseObject(responseValue).getString("data")).getString("value");
        }
    }

    public static class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            SecurityUser securityUser = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
            LinkedHashMap<String, Object> additionalInformation = new LinkedHashMap<>();
            additionalInformation.put(TokenConstants.TOKEN_USER_ID, securityUser.getUserId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

            return super.enhance(accessToken, authentication);
        }
    }
}
