package com.photowey.dubbo.spi.in.action.spi.activate;

import com.alibaba.dubbo.common.extension.Activate;

/**
 * {@code MybatisActivate}
 *
 * @author photowey
 * @date 2021/11/05
 * @since 1.0.0
 */
@Activate(group = {"mybatis", "mybatis2"})
public class MybatisActivate implements ActivateApi {

    @Override
    public String doActivateApi(String parameter) {
        return String.format("%s-%s", "Mybatis", parameter);
    }
}
