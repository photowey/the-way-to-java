/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.validator.in.action.example.assembler;

import com.photowey.validator.in.action.example.domain.dto.HelloDTO;
import com.photowey.validator.in.action.example.domain.entity.Hello;
import org.springframework.stereotype.Component;

/**
 * {@code HelloAssembler}
 *
 * @author photowey
 * @date 2022/02/24
 * @since 1.0.0
 */
@Component
public class HelloAssembler {

    /**
     * 将实体对象转化为 {@code DTO} 对象
     *
     * @param entity {@link Hello} 实体对象
     * @return {@link HelloDTO} {@code DTO} 对象
     */
    public HelloDTO toDTO(final Hello entity) {
        return HelloDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .age(entity.getAge())
                .temperature(entity.getTemperature())
                .price(entity.getPrice())
                .orderState(entity.getOrderState())
                .hobbies(entity.getHobbies())
                .ratios(entity.getRatios())
                .build();
    }

    /**
     * 将{@code DTO} 对象转化为实体对象
     *
     * @param dto {@link HelloDTO} {@code DTO} 对象
     * @return {@link Hello} 实体对象
     */
    public Hello toEntity(final HelloDTO dto) {

        return Hello.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .temperature(dto.getTemperature())
                .price(dto.getPrice())
                .orderState(dto.getOrderState())
                .hobbies(dto.getHobbies())
                .ratios(dto.getRatios())
                .build();
    }

}
