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
package com.photowey.cryptono.in.action.handler;

import com.photowey.crypto.in.action.crypto.CryptoJava;
import com.photowey.crypto.in.action.reader.ClassPathReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code DefaultPhoneNumberEncryptor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
public class DefaultPhoneNumberEncryptor implements PhoneNumberEncryptor, SmartInitializingSingleton {

    private static final String MOBILE_STRATEGY = "PhoneNumber";
    private ConfigurableListableBeanFactory beanFactory;

    private ConcurrentHashMap<Class<?>, KeyPair> ctx = new ConcurrentHashMap<>(2);

    @Override
    public boolean supports(String strategy) {
        return MOBILE_STRATEGY.equalsIgnoreCase(strategy);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.ctx.computeIfAbsent(KeyPair.class, (x) -> {
            String publicKey = ClassPathReader.joinRead("key/public-key.txt");
            String privateKey = ClassPathReader.joinRead("key/private-key.txt");

            PublicKey puk = CryptoJava.RSA.publicKeyFromString(publicKey);
            PrivateKey pvk = CryptoJava.RSA.privateKeyFromString(privateKey);

            return new KeyPair(puk, pvk);
        });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + ORDERED_STEP;
    }

    @Override
    public String encrypt(String number) {
        try {
            return CryptoJava.RSA.encrypt(number, this.ctx.get(KeyPair.class).getPublic());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}