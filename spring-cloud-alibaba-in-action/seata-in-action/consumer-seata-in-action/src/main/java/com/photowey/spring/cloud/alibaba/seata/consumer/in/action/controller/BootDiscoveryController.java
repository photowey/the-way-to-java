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
package com.photowey.spring.cloud.alibaba.seata.consumer.in.action.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.photowey.spring.cloud.alibaba.seata.consumer.in.action.feign.ProducerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * {@code DiscoveryController}
 *
 * @author photowey
 * @date 2021/11/13
 * @since 1.0.0
 */
//@RestController
@RequestMapping("/discovery")
public class BootDiscoveryController {

    @NacosInjected
    private NamingService namingService;

    @Autowired
    private ProducerFeignClient producerFeignClient;

    /**
     * GET :/service/instances
     * 获取服务列表
     *
     * @param serviceName 服务名称
     * @return {@link List<Instance>}
     * @throws NacosException
     * @see * http://localhost:7923/discovery/service/instances?serviceName=consumer-seata-in-action
     */
    @GetMapping("/service/instances")
    public ResponseEntity<List<Instance>> serviceInstances(@RequestParam("serviceName") String serviceName) throws NacosException {
        List<Instance> instances = this.namingService.getAllInstances(serviceName);
        return new ResponseEntity<>(instances, HttpStatus.OK);
    }
}