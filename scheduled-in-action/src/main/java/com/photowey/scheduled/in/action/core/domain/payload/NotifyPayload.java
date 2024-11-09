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
package com.photowey.scheduled.in.action.core.domain.payload;

import com.photowey.scheduled.in.action.core.constant.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * {@code NotifyPayload}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/11/09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyPayload implements AliyunPayload, Serializable {

    private static final long serialVersionUID = 2971256931912093837L;

    private String type;
    private String messageType;

    private String target;
    private String title;

    private String content;
    private String appId;

    private String actionId;
    private String actionName;

    public static NotifyPayload noops() {
        return NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_NO_OPS)
            .target("skip")
            .content("ok")
            .build();
    }

    public static NotifyPayload sms(String mobile, String content) {
        return NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_SMS)
            .target(mobile)
            .content(content)
            .build();
    }

    public static NotifyPayload dingtalk(String mobile, String content) {
        return NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_DINGTALK)
            .target(mobile)
            .content(content)
            .build();
    }

    public static NotifyPayload dingtalk(String mobile, String content, String messageType) {
        NotifyPayloadBuilder builder = NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_DINGTALK)
            .messageType(messageType)
            .content(content);

        if (isNotBlank(mobile)) {
            builder.target(mobile);
        }

        return builder.build();
    }

    public static NotifyPayload dingtalk(String mobile, String content, String messageType, String title) {
        NotifyPayloadBuilder builder = NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_DINGTALK)
            .messageType(messageType)
            .title(title)
            .content(content);

        if (isNotBlank(mobile)) {
            builder.target(mobile);
        }

        return builder.build();
    }

    public static NotifyPayload dingtalk(String mobile, String content, String messageType, String title, String actionId, String actionName) {
        NotifyPayloadBuilder builder = NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_DINGTALK)
            .messageType(messageType)
            .title(title)
            .actionId(actionId)
            .actionName(actionName)
            .content(content);

        if (StringUtils.hasLength(mobile)) {
            builder.target(mobile);
        }

        return builder.build();
    }

    public static NotifyPayload dingtalkMarkdown(String content, String title) {
        return dingtalk("", content, MessageConstants.DINGTALK_MARKDOWN_MESSAGE_TYPE, title);
    }

    public static NotifyPayload dingtalkMarkdown(String content, String title, String actionId, String actionName) {
        return dingtalk("", content, MessageConstants.DINGTALK_MARKDOWN_MESSAGE_TYPE, title, actionId, actionName);
    }

    public static NotifyPayload dingtalkMarkdown(String mobile, String content, String title) {
        return dingtalk(mobile, content, MessageConstants.DINGTALK_MARKDOWN_MESSAGE_TYPE, title);
    }

    public static NotifyPayload wechat(String appId, String openId, String content) {
        return wechat(appId, MessageConstants.MESSAGE_TYPE_WECHAT, openId, content);
    }

    public static NotifyPayload mini(String appId, String openId, String content) {
        return wechat(appId, MessageConstants.MESSAGE_TYPE_MINI, openId, content);
    }

    public static NotifyPayload wechat(String appId, String type, String openId, String content) {
        return NotifyPayload.builder()
            .type(type)
            .target(openId)
            .content(content)
            .appId(appId)
            .build();
    }

    public static NotifyPayload email(String email, String content) {
        return NotifyPayload.builder()
            .type(MessageConstants.MESSAGE_TYPE_EMAIL)
            .target(email)
            .content(content)
            .build();
    }

    private static boolean isNotBlank(String sequence) {
        return null != sequence && sequence.trim().length() > 0;
    }
}

