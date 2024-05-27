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
package com.photowey.cryptono.in.action.Initializer;

import com.photowey.cryptono.in.action.core.entity.PhoneNumber;
import com.photowey.cryptono.in.action.core.entity.Segment;
import com.photowey.cryptono.in.action.handler.PhoneNumberEncryptor;
import com.photowey.cryptono.in.action.repository.PhoneNumberRepository;
import com.photowey.cryptono.in.action.repository.SegmentRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * {@code PhoneNumberEncryptedSegmentInitializer}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
@Component
public class PhoneNumberEncryptedSegmentInitializer implements ApplicationListener<ContextRefreshedEvent>, BeanFactoryAware {

    private final AtomicBoolean started = new AtomicBoolean(false);

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (this.started.compareAndSet(false, true)) {
            this.init();
        }
    }

    private void init() {
        this.initDictionary();
        this.initPhoneNumber();
    }

    private void initDictionary() {
        List<Segment> dictionary = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            String segment = String.format("%03d", i);

            String encrypted = this.numberEncryptor().encrypt(segment);
            Segment row = Segment.builder()
                    .segment(segment)
                    .encrypted(encrypted)
                    .build();
            dictionary.add(row);
        }

        this.segmentRepository().saveAll(dictionary);
    }

    void initPhoneNumber() {
        List<String> phoneNumbers = mockPhoneNumbers();

        List<PhoneNumber> rows = new ArrayList<>();
        for (String phoneNumber : phoneNumbers) {
            String encrypted = this.numberEncryptor().encryptPhoneNumber(phoneNumber, (x) -> {
                Segment segment = this.segmentRepository().findBySegment(x);
                return segment.getEncrypted();
            });
            PhoneNumber row = PhoneNumber.builder()
                    .phoneNumber(phoneNumber)
                    .encrypted(encrypted)
                    .build();

            rows.add(row);
        }

        this.phoneNumberRepository().saveAll(rows);
    }

    private PhoneNumberRepository phoneNumberRepository() {
        return this.beanFactory.getBean(PhoneNumberRepository.class);
    }

    private SegmentRepository segmentRepository() {
        return this.beanFactory.getBean(SegmentRepository.class);
    }

    private PhoneNumberEncryptor numberEncryptor() {
        return this.beanFactory.getBean(PhoneNumberEncryptor.class);
    }

    private static List<String> mockPhoneNumbers() {
        return Arrays.asList(
                // 150
                "15012345678", "15087654321",

                // 15112 -> 151120
                "15112045678", "15187654321",
                "15112045678", "15187654321",

                // 1511 -> 151100
                "15110045678", "15100654321",

                // 181
                "18112345678", "18187654321"
        );
    }
}