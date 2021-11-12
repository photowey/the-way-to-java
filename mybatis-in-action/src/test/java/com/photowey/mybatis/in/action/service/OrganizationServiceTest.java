package com.photowey.mybatis.in.action.service;

import com.photowey.mybatis.in.action.domain.entity.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * {@code OrganizationServiceTest}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@SpringBootTest
class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Test
    void testSelect() {

        // id = 1459040003836350466
        // @see com.photowey.mybatis.in.action.repository.OrganizationRepositoryTest.testSave
        Organization organization = this.organizationService.getById(1459040003836350466L);

        Assertions.assertNotNull(organization);
        Assertions.assertEquals(0L, organization.getParentId());
        Assertions.assertEquals("0", organization.getFamilyMap());
        Assertions.assertEquals("宇宙漫游者", organization.getOrgName());
        Assertions.assertEquals("MD2021111295278848", organization.getOrgCode());
        Assertions.assertEquals(1024, organization.getOrderNo());
        Assertions.assertEquals(1, organization.getStatus());
        Assertions.assertEquals("我是备注", organization.getRemark());

    }

}