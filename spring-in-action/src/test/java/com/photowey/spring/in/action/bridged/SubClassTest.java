package com.photowey.spring.in.action.bridged;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * {@code SubClassTest}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class SubClassTest {

    @Test
    void testBridgeMethod() {

        Method[] declaredMethods = ReflectionUtils.getAllDeclaredMethods(SubClass.class);
        Assertions.assertEquals(14, declaredMethods.length);

        Method methodObject = ClassUtils.getMethod(SubClass.class, "sayHello", new Class[]{Object.class});
        // methodObject::true---2118645580
        log.info("methodObject::" + methodObject.isBridge() + "--" + methodObject.hashCode());

        Method methodString = ClassUtils.getMethod(SubClass.class, "sayHello", new Class[]{String.class});
        // methodString::false---2118645580
        log.info("methodString::" + methodString.isBridge() + "--" + methodString.hashCode());

        Method bridgedMethod = BridgeMethodResolver.findBridgedMethod(methodObject);
        // bridgedMethod::false---2118645580
        log.info("bridgedMethod::" + bridgedMethod.isBridge() + "--" + bridgedMethod.hashCode());

    }

}