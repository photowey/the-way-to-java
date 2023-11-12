package io.github.photowey.proguard.in.action.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.photowey.proguard.in.action.domain.entity.Hello;
import io.github.photowey.proguard.in.action.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code HelloServiceImpl}
 *
 * @author photowey
 * @date 2023/11/12
 * @since 1.0.0
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Integer calculate() {
        Hello hello = populateHello();

        return hello.getVariableInt() * 10;
    }

    @Override
    public String serializeObjectToString() {
        Hello hello = populateHello();
        try {
            return this.objectMapper.writeValueAsString(hello);
        } catch (Exception e) {
            throw new RuntimeException("failed");
        }
    }

    private static Hello populateHello() {
        Map<String, Object> map = new HashMap<>(4);
        map.put("hello", "world");
        map.put("tom", "jerry");

        Hello hello = Hello.builder()
                .variableString("hello.proguard")
                .variableInt(10)
                .variableLong(System.currentTimeMillis())
                .variableObject(map)
                .build();

        return hello;
    }
}
