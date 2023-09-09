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
package com.photowey.hicoomore.integrated.message.rabbitmq.core.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code MessageRabbitmq}
 *
 * @author photowey
 * @date 2023/09/08
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRabbitmq implements Serializable {

    private static final long serialVersionUID = -1429473322667862201L;

    /**
     * 主键标识
     */
    private Long id;
    /**
     * 分区标识
     */
    private Long shardingId;
    /**
     * 删除标记
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间 首次==更新时间
     */
    private LocalDateTime updateTime;
    /**
     * 主机地址
     */
    private String brokerHost;
    /**
     * 主机端口
     */
    private Integer brokerPort;
    /**
     * 虚拟主机
     */
    private String brokerVhost;
    /**
     * 业务编码
     */
    private String biz;
    /**
     * 消息标识
     */
    private String messageId;
    /**
     * 交换机
     */
    private String exchange;
    /**
     * 路由键
     */
    private String routingKey;
    /**
     * 消息类型
     */
    private String messageType;
    /**
     * 消息体
     */
    private String body;
    /**
     * 主机确认-confirm_callback
     */
    private Integer confirmed;
    /**
     * 确认Cause-confirm_callback
     */
    private String confirmCause;
    /**
     * 主机退回-returned_callback
     */
    private Integer returned;
    /**
     * 返回码-returned_callback
     */
    private Integer replyCode;
    /**
     * 返回描述-returned_callback
     */
    private String replyText;
    /**
     * 消费应答
     */
    private Integer acked;
}
