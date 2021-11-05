package com.photowey.dubbo.spi.in.action.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * {@code RabbitMQActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
@Activate(value = {"value"}, group = {"mq"})
public class RabbitMQActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", " RabbitMQ", parameter);
    }
}
