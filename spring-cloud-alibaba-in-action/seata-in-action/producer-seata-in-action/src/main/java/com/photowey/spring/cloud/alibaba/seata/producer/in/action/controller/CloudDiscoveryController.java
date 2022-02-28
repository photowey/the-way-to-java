/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.spring.cloud.alibaba.seata.producer.in.action.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@code DiscoveryController}
 *
 * @author photowey
 * @date 2022/03/01
 * @since 1.0.0
 */
@RestController
@RequestMapping("/cloud/discovery")
public class CloudDiscoveryController {

    /**
     * GET :/service/instances
     * 获取服务列表
     *
     * @param serviceName 服务名称
     * @return {@link List<Instance>}
     * @throws NacosException
     * @see * http://localhost:9762/cloud/discovery/service/instances?serviceName=producer-seata-in-action
     */
    @GetMapping("/service/instances")
    public ResponseEntity<String> producerServiceInstances(@RequestParam("serviceName") String serviceName) throws NacosException {
        return new ResponseEntity<>(String.format("say Hello to %s from: producer-seata-in-action", serviceName), HttpStatus.OK);
    }
}