package com.photowey.mybatis.in.action.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.photowey.mybatis.in.action.domain.entity.Organization;
import com.photowey.mybatis.in.action.engine.IMybatisEngine;
import com.photowey.mybatis.in.action.repository.OrganizationRepository;
import com.photowey.mybatis.in.action.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * {@code OrganizationServiceImpl} Service Impl
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationRepository, Organization> implements OrganizationService {

    @Autowired
    private IMybatisEngine mybatisEngine;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void handleRequiredSave() {
        Organization organization = this.populateOrganization();
        this.mybatisEngine.repositoryEngine().organizationRepository().insert(organization);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void handleNestedSave() {
        Organization organization = this.populateOrganization();
        this.mybatisEngine.repositoryEngine().organizationRepository().insert(organization);
    }

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

