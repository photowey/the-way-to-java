package com.photowey.spring.in.action.defer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * {@code HelloDeferredImportSelectorTest}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@SpringBootTest
class HelloDeferredImportSelectorTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testDeferredImportSelector() {
        HelloDeferred helloDeferred = this.applicationContext.getBean(HelloDeferred.class);
        Assertions.assertNotNull(helloDeferred);
        Assertions.assertEquals("Say hello from:HelloDeferred", helloDeferred.sayHello());
    }

}