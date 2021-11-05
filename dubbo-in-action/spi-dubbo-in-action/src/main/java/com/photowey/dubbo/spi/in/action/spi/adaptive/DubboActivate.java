package com.photowey.dubbo.spi.in.action.spi.adaptive;

import com.alibaba.dubbo.common.extension.Activate;
import com.photowey.dubbo.spi.in.action.spi.activate.ActivateApi;

/**
 * {@code DubboActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
// @Adaptive
// @SPI("dubbo") -> getDefaultExtension() -> No such extension com.photowey.dubbo.spi.in.action.spi.activate.ActivateApi by name dubbo
@Activate(order = 2, group = {"dubbo"})
public class DubboActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", "Dubbo", parameter);
    }
}
