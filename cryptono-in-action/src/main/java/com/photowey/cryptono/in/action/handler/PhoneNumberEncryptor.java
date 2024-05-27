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

import com.photowey.cryptono.in.action.core.model.MobilePhone;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.function.Function;

/**
 * {@code PhoneNumberEncryptor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/26
 */
public interface PhoneNumberEncryptor extends NumberEncryptor {

    default int phoneNumberLength() {
        return 11;
    }

    default int splitGroupLength() {
        return 3;
    }

    // ----------------------------------------------------------------

    default String encryptPhoneNumber(String phoneNumber) {
        return this.encryptPhoneNumber(phoneNumber, this::encrypt);
    }

    default String encryptPhoneNumber(String phoneNumber, Function<String, String> fx) {
        StringBuffer buffer = new StringBuffer();
        List<String> group = this.split(phoneNumber);
        group.forEach(member -> {
            String segment = fx.apply(member);
            buffer.append(segment);
        });

        return buffer.toString();
    }

    default String encryptFuzz(String input) {
        StringBuffer buffer = new StringBuffer();
        String padded = this.padding(input);

        List<String> group = this.splitRelax(padded);
        group.forEach(member -> {
            String segment = this.encrypt(member);
            buffer.append(segment);
        });

        return buffer.toString();
    }

    // ----------------------------------------------------------------

    default void check(String phoneNumber) {
        if (ObjectUtils.isEmpty(phoneNumber) || phoneNumber.length() != this.phoneNumberLength()) {
            throw new IllegalArgumentException(String.format("PhoneNumber[%s] is invalid", phoneNumber));
        }
    }

    default void checkRelax(String phoneNumber) {
        if (ObjectUtils.isEmpty(phoneNumber)) {
            throw new IllegalArgumentException(String.format("PhoneNumber[%s] is invalid", phoneNumber));
        }
    }

    default List<String> split(String phoneNumber) {
        this.check(phoneNumber);
        int groupLength = this.splitGroupLength();
        return MobilePhone.split(groupLength, phoneNumber);
    }

    default List<String> splitRelax(String phoneNumber) {
        this.checkRelax(phoneNumber);
        int groupLength = this.splitGroupLength();
        return MobilePhone.split(groupLength, phoneNumber);
    }

    default String padding(String input) {
        int length = input.length();
        int groupLength = this.splitGroupLength();
        int remainder = length % groupLength;

        if (remainder == 0) {
            return input;
        }

        int paddingLength = groupLength - remainder;

        return input + "0".repeat(Math.max(0, paddingLength));
    }
}
