package com.photowey.dubbo.spi.in.action.spi.activate;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * {@code ActiveApi}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
//@SPI
@SPI("dubbo") // The default extension
public interface ActivateApi {

    // @Adaptive
    String doActivateApi(String parameter);
}
