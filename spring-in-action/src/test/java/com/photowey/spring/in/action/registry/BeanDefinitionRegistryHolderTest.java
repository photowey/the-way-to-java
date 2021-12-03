package com.photowey.spring.in.action.registry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code BeanDefinitionRegistryHolderTest}
 *
 * @author photowey
 * @date 2021/12/03
 * @since 1.0.0
 */
@SpringBootTest
class BeanDefinitionRegistryHolderTest {

    @Autowired
    private BeanDefinitionRegistryHolder registryHolder;

    @Test
    void testUnregisterBean() {
        // com.photowey.spring.in.action.registry.HelloBeanDefinitionRegistryPostProcessorTest.testBeanDefinitionRegistry
        HelloBeanDefinition notNullBean = this.registryHolder.getBean(HelloBeanDefinition.class);
        Assertions.assertNotNull(notNullBean, "instance can't be null!");

        String beanName = BeanFactoryUtils.transformedBeanName("helloBeanDefinition");
        this.registryHolder.unregisterBean(beanName);

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> {
            this.registryHolder.getBean(HelloBeanDefinition.class);
        });

        this.registryHolder.registerBean(beanName, notNullBean);

        HelloBeanDefinition notNullBeanV2 = this.registryHolder.getBean(HelloBeanDefinition.class);
        Assertions.assertNotNull(notNullBeanV2, "instance can't be null!");
    }

}