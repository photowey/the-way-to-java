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
package com.photowey.spring.cloud.alibaba.seata.consumer.in.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.spring.cloud.alibaba.seata.consumer.in.action.domain.entity.Organization;
import com.photowey.spring.cloud.alibaba.seata.consumer.in.action.engine.ISeataEngine;
import com.photowey.spring.cloud.alibaba.seata.consumer.in.action.repository.OrganizationRepository;
import com.photowey.spring.cloud.alibaba.seata.consumer.in.action.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * {@code OrganizationServiceImpl} Service Impl
 *
 * @author photowey
 * @date 2021/11/13
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationRepository, Organization> implements OrganizationService {

    @Autowired
    private ISeataEngine mybatisEngine;

    public Organization populateOrganization() {
        Organization organization = new Organization();
        organization.setParentId(0L);
        organization.setFamilyMap("0");
        organization.setOrgName("宇宙漫游者-事务测试");
        organization.setOrgCode("MD2021111288489527");
        organization.setOrderNo(1024);
        organization.setStatus(1);
        organization.setRemark("我是备注-事务测试");
        organization.setCreateTime(LocalDateTime.now());
        organization.setCreateBy(202111288489527L);
        organization.setModifiedTime(LocalDateTime.now());
        organization.setUpdateBy(202111288489527L);
        organization.setDeleted(0);
        return organization;
    }

}

