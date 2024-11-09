/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.scheduled.in.action.notify.dingtalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.photowey.scheduled.in.action.context.NotifyContext;
import com.photowey.scheduled.in.action.core.constant.MessageConstants;
import com.photowey.scheduled.in.action.core.domain.response.NotifyResponse;
import com.photowey.scheduled.in.action.core.setter.MessageSetter;
import com.photowey.scheduled.in.action.dingtalk.sign.DingtalkSignAlgorithm;
import com.photowey.scheduled.in.action.dingtalk.sign.SignResult;
import com.photowey.scheduled.in.action.property.MessageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code DingtalkMessageNotifierImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Slf4j
public class DingtalkMessageNotifierImpl implements DingtalkMessageNotifier, BeanFactoryAware {

    @Resource
    private DingtalkSignAlgorithm<SignResult> dingtalkSignAlgorithm;

    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    private MessageProperties messageProperties() {
        return this.beanFactory.getBean(MessageProperties.class);
    }

    @Override
    public boolean supports(NotifyContext ctx) {
        return this.messageProperties().getDingtalk().isEnabled()
            && ctx.getType().equalsIgnoreCase(MessageConstants.MESSAGE_TYPE_DINGTALK);
    }

    @Override
    public NotifyResponse publish(NotifyContext ctx) throws Exception {
        SignResult signResult = this.dingtalkSignAlgorithm.sign(this.messageProperties().getDingtalk().getAppSecret());
        String api = this.messageProperties().getDingtalk().populateAPI(signResult.transferTo());

        DingTalkClient client = new DefaultDingTalkClient(api);
        OapiRobotSendRequest request = this.populateRequest(ctx);
        OapiRobotSendResponse response = client.execute(request);

        // {"errcode":0,"errmsg":"ok"}

        return NotifyResponse.walk(response.getErrcode(), response.getMessage());
    }

    private OapiRobotSendRequest populateRequest(NotifyContext ctx) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(ctx.getMessageType());
        ctx.setRequest(request);

        Map<String, MessageSetter> beans = this.beanFactory.getBeansOfType(MessageSetter.class);
        List<MessageSetter> messageSetters = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(messageSetters);

        for (MessageSetter messageSetter : messageSetters) {
            if (messageSetter.supports(ctx)) {
                messageSetter.advance(ctx);
            }
        }

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        if (ObjectUtils.isEmpty(ctx.getTargets())) {
            at.setIsAtAll(true);
        } else {
            at.setAtMobiles(new ArrayList<>(ctx.getTargets()));
            at.setIsAtAll(false);
        }
        request.setAt(at);

        return request;
    }

    @Override
    public int getOrder() {
        return 100;
    }
}

