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
package com.photowey.scheduled.in.action.context;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.photowey.scheduled.in.action.core.constant.MessageConstants;

import java.util.Collection;

/**
 * {@code NotifyContext}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
public interface NotifyContext {

    /**
     * 通知类型
     *
     * @return 通知类型
     */
    String getType();

    void setType(String type);

    /**
     * 通知消息体
     *
     * @return 通知消息体
     */
    String getBody();

    void setBody(String body);

    Collection<String> getTargets();

    void setTargets(Collection<String> targets);

    void setTargetz(String... targets);

    default String getTitle() {
        return "运维通知";
    }

    default void setTitle(String title) {
    }

    default String getMessageType() {
        return MessageConstants.DINGTALK_TXT_MESSAGE_TYPE;
    }

    default void setMessageType(String messageType) {
    }

    default OapiRobotSendRequest getRequest() {
        return null;
    }

    default void setRequest(OapiRobotSendRequest request) {

    }
}

