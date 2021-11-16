package com.photowey.spring.in.action.target;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * {@code CustomTargetSourceBeanTest}
 *
 * @author photowey
 * @date 2021/11/16
 * @since 1.0.0
 */
@SpringBootTest
class CustomTargetSourceBeanTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testCustomTargetSource() {
        CustomTargetSourceBean targetSourceBean = this.applicationContext.getBean(CustomTargetSourceBean.class);
        Assertions.assertNotNull(targetSourceBean);
        Assertions.assertTrue(AopUtils.isCglibProxy(targetSourceBean));
        Assertions.assertEquals("Say hello from:CustomTargetSourceBean", targetSourceBean.sayHello());
    }

}