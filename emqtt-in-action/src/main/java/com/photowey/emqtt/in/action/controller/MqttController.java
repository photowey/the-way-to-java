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
package com.photowey.emqtt.in.action.controller;

import com.photowey.emqtt.in.action.gateway.MqttGateway;
import com.photowey.emqtt.in.action.message.EmqttMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code MqttController}
 *
 * @author photowey
 * @date 2021/12/20
 * @since 1.0.0
 */
@RestController
@RequestMapping("/emqtt")
public class MqttController {

    @Autowired
    private MqttGateway mqttGateway;

    @PostMapping("/message")
    @ApiOperation("send the message to emqtt")
    public ResponseEntity<String> message(@Validated @RequestBody @ApiParam EmqttMessage message) {
        mqttGateway.sendToMqtt(message.getTopic(), message.getQos(), message.getContent());

        return new ResponseEntity<>(String.format("send to emqtt,the topic: %s, message: %s", message.getTopic(), message.getContent()), HttpStatus.OK);
    }

}