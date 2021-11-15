package com.photowey.spring.in.action.registry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * {@code HelloBeanDefinitionRegistryPostProcessorTest}
 *
 * @author photowey
 * @date 2021/11/15
 * @since 1.0.0
 */
@SpringBootTest
class HelloBeanDefinitionRegistryPostProcessorTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testBeanDefinitionRegistry() {
        HelloBeanDefinition helloBeanDefinition = this.applicationContext.getBean(HelloBeanDefinition.class);
        Assertions.assertNotNull(helloBeanDefinition);
        Assertions.assertEquals("Say hello from:HelloBeanDefinition", helloBeanDefinition.sayHello());
    }

}