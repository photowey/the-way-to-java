package com.photowey.common.in.action.option;/*
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

import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

/**
 * {@code OptionTest}
 *
 * @author photowey
 * @date 2024/02/10
 * @since 1.0.0
 */
class OptionTest {

    @Data
    public static class Message implements Serializable {

        private static final long serialVersionUID = -7142353781081408528L;

        private String value;

        public Message(String value) {
            this.value = value;
        }
    }

    @Test
    void testOption_null() {
        Option<Message> option = Option.valueOf(null);

        Assertions.assertThrows(RuntimeException.class, () -> {
            Message x = option.match(() -> {
                return "failed";
            });
        });

    }

    @Test
    void testOption_not_null() {
        Option<Message> option = Option.valueOf(new Message("ok"));
        Message message = option.matches(() -> {
            throw new UnsupportedOperationException("Unsupported now");
        });

        Assertions.assertNotNull(message);
        Assertions.assertEquals("ok", message.getValue());
    }
}