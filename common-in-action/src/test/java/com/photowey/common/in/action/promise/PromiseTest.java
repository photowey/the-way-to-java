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
package com.photowey.common.in.action.promise;

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * {@code PromiseTest}
 *
 * @author photowey
 * @date 2024/02/11
 * @since 1.0.0
 */
class PromiseTest {

    @Data
    public static class Message implements Serializable {

        private static final long serialVersionUID = -7142353781081408528L;

        private String value;

        public Message(String value) {
            this.value = value;
        }
    }

    @Test
    void testPromise_then_resolve() {
        Promise<Message, Throwable> promise = Promise.valueOf(null);
        promise.then(Assertions::assertNull);
    }

    @Test
    void testPromise_then__resolve_reject() {
        Promise<Message, Throwable> promise = Promise.valueOf(new Message("ok"));
        promise.then((value) -> {
            Assertions.assertNotNull(value);
            Assertions.assertEquals("ok", value.getValue());
        }, (x) -> {
            throw new UnsupportedOperationException("Unsupported now");
        });
    }

    @Test
    void testPromise_throwable() {
        Promise<Message, Throwable> promise = Promise.throwableOf(new RuntimeException("failed"));
        promise.throwable(Assertions::assertNotNull);
    }

    @Test
    void testPromise_then_throwable() {
        Promise<Message, Throwable> promise = Promise.throwableOf(new RuntimeException("failed"));
        promise.then((value) -> {
            throw new UnsupportedOperationException("Unsupported now");
        }).throwable(Assertions::assertNotNull);
    }

    @Test
    void testPromise_then__resolve_reject_throwable() {
        Promise<Message, Throwable> promise = Promise.throwableOf(new RuntimeException("failed"));
        promise.then((v1) -> {
            throw new UnsupportedOperationException("Unsupported now");
        }, (v2) -> {
            throw new UnsupportedOperationException("Unsupported now");
        }, Assertions::assertNotNull);
    }

    @Test
    void testPromise_then__value() {
        Promise<Message, Throwable> promise = Promise.valueOf(new Message("ok"));
        Message message = promise.then((value) -> {
            Assertions.assertNotNull(value);
            Assertions.assertEquals("ok", value.getValue());
        }).unwrap();

        Assertions.assertNotNull(message);
        Assertions.assertEquals("ok", message.getValue());
    }

    @Test
    void testPromise_throwable__value() {
        Promise<Message, Throwable> promise = Promise.throwableOf(new RuntimeException("failed"));
        Message message = promise.throwable(Assertions::assertNotNull).unwrap();

        Assertions.assertNull(message);
    }
}