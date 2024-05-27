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
package com.photowey.cryptono.in.action.repository;

import com.photowey.cryptono.in.action.core.entity.PhoneNumber;
import com.photowey.cryptono.in.action.core.entity.Segment;
import com.photowey.cryptono.in.action.handler.PhoneNumberEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code PhoneNumberRepositoryTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
@SpringBootTest
class PhoneNumberRepositoryTest {

    @Autowired
    private PhoneNumberEncryptor numberEncryptor;

    @Autowired
    private SegmentRepository segmentRepository;
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;

    //@Test
    void testEncrypt() {
        String phoneNumber1 = "16112345678";
        String phoneNumber2 = "16187654321";

        String phoneNumber3 = "19112345678";
        String phoneNumber4 = "19187654321";

        List<String> phoneNumbers = Arrays.asList(phoneNumber1, phoneNumber2, phoneNumber3, phoneNumber4);

        List<PhoneNumber> rows = new ArrayList<>();
        for (String phoneNumber : phoneNumbers) {
            String encrypted = this.numberEncryptor.encryptPhoneNumber(phoneNumber);
            PhoneNumber row = PhoneNumber.builder()
                    .phoneNumber(phoneNumber)
                    .encrypted(encrypted)
                    .build();

            rows.add(row);
        }

        this.phoneNumberRepository.saveAll(rows);
    }

    /**
     * <pre>
     * List<String> phoneNumbers = Arrays.asList(
     *                 // 150
     *                 "15012345678", "15087654321",
     *
     *                 // 15112 -> 151120
     *                 "15112045678", "15187654321",
     *                 "15112045678", "15187654321",
     *
     *                 // 1511 -> 151100
     *                 "15110045678", "15100654321",
     *
     *                 // 181
     *                 "18112345678", "18187654321"
     *         );
     * </pre>
     */
    @Test
    void testQuery() {
        List<PhoneNumber> phoneNumbers = this.phoneNumberRepository.findByPhoneNumberContainingOrderByIdDesc("151");
        Assertions.assertEquals(6, phoneNumbers.size());
    }

    @Test
    void testQuery_encrypted() {
        String fuzz = "151";
        Segment encrypted = this.segmentRepository.findBySegment(fuzz);

        List<PhoneNumber> phoneNumbers = this.phoneNumberRepository.findByEncryptedContainingOrderByIdDesc(encrypted.getEncrypted());
        Assertions.assertEquals(6, phoneNumbers.size());
    }
}