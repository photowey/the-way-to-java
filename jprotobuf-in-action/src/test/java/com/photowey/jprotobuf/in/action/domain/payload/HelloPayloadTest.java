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
package com.photowey.jprotobuf.in.action.domain.payload;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@code HelloPayloadTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/09/19
 */
class HelloPayloadTest {

    @Test
    void testCodec() throws Exception {
        Codec<HelloPayload> codec = ProtobufProxy.create(HelloPayload.class);
        HelloPayload payload = HelloPayload.builder()
                .id(10086L)
                .name("photowey")
                .age(18)
                .balance(new BigDecimal("100.00"))
                .hobbies(List.of("football", "basketball"))
                .build();

        byte[] bytes = codec.encode(payload);
        HelloPayload image = codec.decode(bytes);

        Assertions.assertEquals(payload.getId(), image.getId());
        Assertions.assertEquals(payload.getName(), image.getName());
        Assertions.assertEquals(payload.getAge(), image.getAge());
        Assertions.assertEquals(payload.getBalance(), image.getBalance());
        Assertions.assertEquals(payload.getHobbies(), image.getHobbies());
    }
}