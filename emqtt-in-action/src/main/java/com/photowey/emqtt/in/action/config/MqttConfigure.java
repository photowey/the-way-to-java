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
package com.photowey.emqtt.in.action.config;

import com.photowey.emqtt.in.action.property.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * {@code MqttConfigure}
 *
 * @author photowey
 * @date 2021/12/20
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = {MqttProperties.class})
public class MqttConfigure {

    @Autowired
    private MqttProperties mqttProperties;

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        // Multi
        options.setServerURIs(new String[]{this.mqttProperties.getAddress()});
        options.setUserName(this.mqttProperties.getUserName());
        options.setPassword(this.mqttProperties.getPassword().toCharArray());

        factory.setConnectionOptions(options);

        return factory;
    }

    @Bean("mqttInputChannel")
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer messageProducer() {
        MqttPahoMessageDrivenChannelAdapter channelAdapter = new MqttPahoMessageDrivenChannelAdapter(
                "mqtt-paho-client",
                this.mqttClientFactory(),
                "boat", "collector", "battery", "+/sensor"
        );
        channelAdapter.setCompletionTimeout(5_000);

        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        channelAdapter.setConverter(defaultPahoMessageConverter);
        channelAdapter.setQos(1);
        channelAdapter.setOutputChannel(this.mqttInputChannel());

        return channelAdapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler inboundHandler() {
        return message -> {
            String payload = message.getPayload().toString();
            String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

            // "boat", "collector", "battery", "+/sensor"
            if (topic.matches(".+/sensor")) {
                String sensorSn = topic.split("/")[0];
                log.info("the sensor:" + sensorSn + ", message is: " + payload);
            } else if (topic.equals("collector")) {
                log.info("the collector message is:" + payload);
            } else {
                log.info("discard message,topic:[" + topic + "], payload:" + payload);
            }
        };
    }

    @Bean("mqttOutboundChannel")
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler outboundHandler() {

        // publish-client
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("publish-client", this.mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("command");
        messageHandler.setDefaultQos(1);

        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        messageHandler.setConverter(defaultPahoMessageConverter);

        return messageHandler;
    }

}
