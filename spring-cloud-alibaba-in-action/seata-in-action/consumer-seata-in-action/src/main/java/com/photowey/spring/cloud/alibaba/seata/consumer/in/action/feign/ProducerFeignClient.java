package com.photowey.spring.cloud.alibaba.seata.consumer.in.action.feign;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * {@code ProducerFeignClient}
 *
 * @author photowey
 * @date 2022/02/28
 * @since 1.0.0
 */
@FeignClient(name = "producer-seata-in-action")
@LoadBalancerClient(name = "producer-seata-in-action")
public interface ProducerFeignClient {

    @GetMapping("/cloud/discovery/service/instances")
    ResponseEntity<String> serviceInstances(@RequestParam("serviceName") String serviceName);
}
