/*
 * Copyright © 2021 the original author or authors (photowey@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.photowey.wechat.sdk.core.property;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photowey.wechat.sdk.core.checker.wechat.WechatPropertyChecker;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.PriorityOrdered;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * {@code WechatProperties}
 *
 * @author photowey
 * @date 2023/05/13
 * @since 1.0.0
 */
@Data
@Validated
public class WechatProperties implements BeanFactoryAware, InitializingBean, PriorityOrdered, Serializable {

    /**
     * TODO {@link WechatProperties} 必须交由 IOC 管理
     */

    @Valid
    private Global global = new Global();
    @Valid
    private Logger logger = new Logger();
    @Valid
    private Callback callback = new Callback();
    @Valid
    private Checker checker = new Checker();
    @Valid
    private Wechat wechat = new Wechat();
    @Valid
    @NotEmpty(message = "请配置:服务商微信参数")
    private Map<String/* tenantId */, Api> tenants = new HashMap<>();

    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    private BeanFactory beanFactory;

    public Api api(String tenantId) {
        return this.tenants.get(tenantId);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {
        WechatPropertyChecker propertyChecker = this.beanFactory.getBean(WechatPropertyChecker.class);
        propertyChecker.check(this);
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE + 100;
    }

    @Data
    public static class Global implements Serializable {
        @NotBlank(message = "请配置:全局服务商商户号")
        private String tenantId;
        @NotBlank(message = "请配置:全局服务商统一回调地址")
        private String domain;
    }

    @Data
    public static class Logger implements Serializable {
        @Valid
        private Notify notify = new Notify();
        @Valid
        private Decrypted decrypted = new Decrypted();
    }

    @Data
    public static class Notify implements Serializable {
        private boolean enabled = true;
    }

    @Data
    public static class Decrypted implements Serializable {
        private boolean enabled = true;
    }

    @Data
    public static class Callback implements Serializable {
        @Valid
        private Coupon coupon = new Coupon();
    }

    @Data
    public static class Coupon implements Serializable {
        /**
         * 通知地址: ${tenant.domain}/${url}
         * 还可以在通知地址上面做文章,让每个服务商的地址不一样,这样就可以区分
         * <pre>
         *     /v1/weixin/callbacks/coupon/notify/${tenantNum}
         * </pre>
         */
        private String url = "/v1/weixin/callbacks/coupon/notify";
    }

    @Data
    public static class Checker implements Serializable {
        private boolean checkTenantEmpty = true;
    }

    @Data
    public static class Wechat implements Serializable {
        private Cert cert = new Cert();
    }

    @Data
    public static class Cert implements Serializable {
        private Refresh refresh = new Refresh();
    }

    @Data
    public static class Refresh implements Serializable {
        private long delay = 0L;
        /**
         * <a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/wechatpay5_1.shtml">定期更新平台证书</a>
         */
        private long period = HOURS * 10L;
        private TimeUnit unit = TimeUnit.SECONDS;
    }

    private static final long SECONDS = 1;
    private static final long MINUTES = 60 * SECONDS;
    private static final long HOURS = 60 * MINUTES;
}