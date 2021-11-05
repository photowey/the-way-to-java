package com.photowey.dubbo.spi.in.action.spi.adaptive;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.photowey.dubbo.spi.in.action.spi.activate.ActivateApi;

/**
 * {@code SpringActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
@Adaptive // More than 1 adaptive class found, If @SPI("dubbo")
@Activate(group = {"spring"})
public class SpringActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", "Spring", parameter);
    }
}
