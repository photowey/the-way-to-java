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
package com.photowey.spring.in.action.controller;

import com.photowey.spring.in.action.cny.annotation.CnyFormat;
import com.photowey.spring.in.action.domain.dto.PersonDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * {@code FormatterController}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/formatter")
public class FormatterController {

    @GetMapping("/format")
    @ApiOperation("序列化")
    public ResponseEntity<PersonDTO> format() {
        PersonDTO person = PersonDTO.builder()
                .id(System.currentTimeMillis())
                .balance(new BigDecimal("1024.8848"))
                .wallet("1024.8848")
                .build();

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/format/vb")
    @ApiOperation("序列化BigDecimal")
    public ResponseEntity<PersonDTO> formatBigDecimal(@CnyFormat(toYuan = false, toFen = true) BigDecimal balance) {
        PersonDTO person = PersonDTO.builder()
                .id(System.currentTimeMillis())
                .balance(balance)
                .wallet("1024.8848")
                .build();

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/format/vi")
    @ApiOperation("序列化Integer")
    public ResponseEntity<PersonDTO> formatInteger(@CnyFormat(toYuan = false, toFen = true) Integer balance) {
        PersonDTO person = PersonDTO.builder()
                .id(System.currentTimeMillis())
                .balance(new BigDecimal(balance))
                .wallet("1024.8848")
                .build();

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/format/vl")
    @ApiOperation("序列化Long")
    public ResponseEntity<PersonDTO> formatLong(@CnyFormat(toYuan = false, toFen = true) Long balance) {
        PersonDTO person = PersonDTO.builder()
                .id(System.currentTimeMillis())
                .balance(new BigDecimal(balance))
                .wallet("1024.8848")
                .build();

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/format/vs")
    @ApiOperation("序列化String")
    public ResponseEntity<PersonDTO> formatString(@CnyFormat(toYuan = false, toFen = true) String balance) {
        PersonDTO person = PersonDTO.builder()
                .id(System.currentTimeMillis())
                .balance(new BigDecimal(balance))
                .wallet("1024.8848")
                .build();

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping("/parse")
    @ApiOperation("反序列化:RequestBody")
    public ResponseEntity<PersonDTO> parse(@RequestBody PersonDTO person) {
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/parse/get")
    @ApiOperation("反序列化:RequestParam")
    public ResponseEntity<PersonDTO> parseGet(PersonDTO person) {
        return new ResponseEntity<>(person, HttpStatus.OK);
    }
}
