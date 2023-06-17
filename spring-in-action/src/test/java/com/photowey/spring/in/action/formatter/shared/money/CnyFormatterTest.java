package com.photowey.spring.in.action.formatter.shared.money;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.photowey.spring.in.action.cny.annotation.CnyFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * {@code CnyFormatterTest}
 *
 * @author photowey
 * @date 2023/06/17
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class CnyFormatterTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCnyFormat() throws JsonProcessingException {
        Person person = Person.builder()
                .id(System.currentTimeMillis())
                .balance(new BigDecimal("1024.8848"))
                .build();

        System.out.println(objectMapper.writeValueAsString(person));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Person implements Serializable {

        private static final long serialVersionUID = -4477639101713352528L;

        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;

        @CnyFormat(toYuan = false, toFen = true)
        private BigDecimal balance;
    }
}
