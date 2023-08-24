/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.oauth2.authentication.server.config;

import com.alibaba.fastjson.JSON;
import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.SecurityUser;
import com.photowey.oauth2.authentication.jwt.model.enums.ConfigLocation;
import com.photowey.oauth2.authentication.jwt.model.enums.ConfigType;
import com.photowey.oauth2.authentication.jwt.model.oauth2.OAuth2JksProperties;
import com.photowey.oauth2.authentication.jwt.model.principal.PrincipalModel;
import com.photowey.oauth2.authentication.jwt.util.JwtSecurityUtils;
import com.photowey.oauth2.authentication.jwt.util.OAuth2PrincipalUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import static com.photowey.oauth2.authentication.jwt.constant.TokenConstants.AUTHORIZATION_SERVER_PUBLIC_KEY_FILENAME;
import static com.photowey.oauth2.authentication.jwt.constant.TokenConstants.AUTHORIZATION_SERVER_TOKEN_KEY_ENDPOINT_URL;

/**
 * {@code AccessTokenConfigure}
 *
 * @author photowey
 * @date 2022/01/15
 * @since 1.0.0
 */
@Configuration
public class AccessTokenConfigure implements EnvironmentAware {

    private final RestTemplate restTemplate;
    private final OAuth2JksProperties jksProperties;
    private Environment environment;

    @Value("${oauth.server.host:localhost")
    private String authServerHost;
    @Value("${oauth.server.port:${server.port}}")
    private Integer authServerPort;

    public AccessTokenConfigure(RestTemplate restTemplate, OAuth2JksProperties jksProperties) {
        this.restTemplate = restTemplate;
        this.jksProperties = jksProperties;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(this.jwtAccessTokenConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter accessTokenEnhancer = new JwtAccessTokenEnhancer();
        this.populateAccessTokenKeyPair(accessTokenEnhancer);

        // this.populateAccessTokenByKey(accessTokenEnhancer);
        // this.populateAccessTokenVerifier(accessTokenEnhancer);

        return accessTokenEnhancer;
    }

    @Bean
    public KeyPair keyPair() {
        return this.populateKeyPair();
    }

    private void populateAccessTokenKeyPair(JwtAccessTokenConverter accessTokenConverter) {
        accessTokenConverter.setKeyPair(this.keyPair());
    }

    private void populateAccessTokenByKey(JwtAccessTokenConverter accessTokenConverter) {
        accessTokenConverter.setSigningKey(this.jksProperties.getPrivateKey());
        accessTokenConverter.setVerifierKey(this.jksProperties.getPublicKey());
    }

    public KeyPair populateKeyPair() {
        ConfigLocation configLocation = this.jksProperties.getConfigLocation();
        ConfigType configType = this.jksProperties.getConfigType();
        if (ConfigLocation.CONFIG_CENTER.equals(configLocation)) {
            return this.handleByConfigurer();
        } else {
            if (ConfigType.JKS.equals(configType)) {
                return this.handleByJks();
            } else {
                return this.handleByTxt();
            }
        }
    }

    public KeyPair handleByConfigurer() {
        PublicKey publicKey = JwtSecurityUtils.publicKeyFromConfigurer(this.jksProperties.getPublicKey());
        PrivateKey privateKey = JwtSecurityUtils.privateKeyFromConfigurer(this.jksProperties.getPrivateKey());

        return new KeyPair(publicKey, privateKey);
    }

    public KeyPair handleByJks() {
        String keystore = jksProperties.getKeystore();
        if (keystore.contains("%s")) {
            keystore = String.format(keystore, this.determineProfilesActive());
        }
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keystore), jksProperties.getStorepass().toCharArray());

        return keyStoreKeyFactory.getKeyPair(jksProperties.getAlias(), jksProperties.getKeypass().toCharArray());
    }

    public KeyPair handleByTxt() {
        String publicKeyPath = this.jksProperties.getPublicKeyPath();
        String privateKeyPath = this.jksProperties.getPrivateKeyPath();
        if (publicKeyPath.contains("%s")) {
            publicKeyPath = String.format(publicKeyPath, this.determineProfilesActive());
        }
        if (privateKeyPath.contains("%s")) {
            privateKeyPath = String.format(privateKeyPath, this.determineProfilesActive());
        }
        PublicKey publicKey = JwtSecurityUtils.publicKey(publicKeyPath);
        PrivateKey privateKey = JwtSecurityUtils.privateKey(privateKeyPath);

        return new KeyPair(publicKey, privateKey);
    }

    public String determineProfilesActive() {
        return this.environment.getProperty("spring.profiles.active");
    }

    @Deprecated
    private void populateAccessTokenVerifier(JwtAccessTokenConverter jwtAccessTokenConverter) {
        jwtAccessTokenConverter.setVerifier(new org.springframework.security.jwt.crypto.sign.RsaVerifier(this.retrievePublicKey()));
    }

    @Deprecated
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

    private static class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            LinkedHashMap<String, Object> additionalInformation = new LinkedHashMap<>();
            this.populateOAuthPrincipal(authentication, additionalInformation);
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

            return super.enhance(accessToken, authentication);
        }

        public void populateOAuthPrincipal(OAuth2Authentication authentication, LinkedHashMap<String, Object> additionalInformation) {
            // TODO handle Additional
            Object principal = authentication.getUserAuthentication().getPrincipal();
            if (principal instanceof SecurityUser) {
                SecurityUser securityUser = (SecurityUser) principal;
                additionalInformation.put(TokenConstants.TOKEN_USER_ID, securityUser.getUserId());
            } else {
                PrincipalModel principalModel = OAuth2PrincipalUtils.obtainPrincipalModel(principal);
                String userId = principalModel.getUserId();
                additionalInformation.put(TokenConstants.TOKEN_USER_ID, userId);
            }
        }
    }
}
