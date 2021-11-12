package com.photowey.mybatis.in.action.repository;

import com.photowey.mybatis.in.action.domain.entity.Organization;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * {@code OrganizationRepositoryTest}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@SpringBootTest
class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    void testSave() {
        Organization organization = this.populateOrganization();

        // 1459040003836350466
        this.organizationRepository.insert(organization);

        Organization db = this.organizationRepository.selectById(organization.getId());
        Assertions.assertNotNull(db);
        Assertions.assertEquals(0L, db.getParentId());
        Assertions.assertEquals("0", db.getFamilyMap());
        Assertions.assertEquals("宇宙漫游者", db.getOrgName());
        Assertions.assertEquals("MD2021111295278848", db.getOrgCode());
        Assertions.assertEquals(1024, db.getOrderNo());
        Assertions.assertEquals(1, db.getStatus());
        Assertions.assertEquals("我是备注", db.getRemark());

    }

    public Organization populateOrganization() {
        Organization organization = new Organization();
        organization.setParentId(0L);
        organization.setFamilyMap("0");
        organization.setOrgName("宇宙漫游者");
        organization.setOrgCode("MD2021111295278848");
        organization.setOrderNo(1024);
        organization.setStatus(1);
        organization.setRemark("我是备注");
        organization.setCreateTime(LocalDateTime.now());
        organization.setCreateBy(202111295278848L);
        organization.setModifiedTime(LocalDateTime.now());
        organization.setUpdateBy(202111295278848L);
        organization.setDeleted(0);
        return organization;
    }

}