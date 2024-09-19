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

import com.baidu.bjf.remoting.protobuf.annotation.Ignore;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * {@code HelloPayload}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/09/19
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ProtobufClass
public class HelloPayload implements Serializable {

    @Ignore
    private static final long serialVersionUID = -7111701878291652655L;

    private Long id;
    private String name;
    private Integer age;
    private BigDecimal balance;
    private List<String> hobbies;
}