package com.photowey.dubbo.spi.in.action.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * {@code SpringActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
@Activate(order = 1, group = {"springboot"})
public class SpringBootActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", "SpringBoot", parameter);
    }
}
