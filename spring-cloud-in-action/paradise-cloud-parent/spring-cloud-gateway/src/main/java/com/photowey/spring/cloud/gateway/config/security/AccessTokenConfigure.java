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
package com.photowey.spring.cloud.gateway.config.security;

import com.photowey.oauth2.authentication.jwt.constant.TokenConstants;
import com.photowey.oauth2.authentication.jwt.model.SecurityUser;
import com.photowey.oauth2.authentication.jwt.model.enums.ConfigLocation;
import com.photowey.oauth2.authentication.jwt.model.enums.ConfigType;
import com.photowey.oauth2.authentication.jwt.model.oauth2.OAuth2JksProperties;
import com.photowey.oauth2.authentication.jwt.util.JwtSecurityUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.LinkedHashMap;

/**
 * {@code AccessTokenConfigure}
 *
 * @author photowey
 * @date 2022/01/16
 * @since 1.0.0
 */
@Configuration
@EnableConfigurationProperties(value = {OAuth2JksProperties.class})
public class AccessTokenConfigure implements EnvironmentAware {

    private final OAuth2JksProperties jksProperties;
    private Environment environment;

    public AccessTokenConfigure(OAuth2JksProperties jksProperties) {
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

    private JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenEnhancer();
        this.populateAccessTokenKeyPair(jwtAccessTokenConverter);

        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
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

    private void populateAccessTokenKeyPair(JwtAccessTokenConverter jwtAccessTokenConverter) {
        jwtAccessTokenConverter.setKeyPair(this.keyPair());
    }

    private static class JwtAccessTokenEnhancer extends JwtAccessTokenConverter {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            SecurityUser securityUser = (SecurityUser) authentication.getUserAuthentication().getPrincipal();
            LinkedHashMap<String, Object> additionalInformation = new LinkedHashMap<>();
            // TODO handle Additional
            additionalInformation.put(TokenConstants.TOKEN_USER_ID, securityUser.getUserId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);

            return super.enhance(accessToken, authentication);
        }
    }
}
