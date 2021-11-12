package com.photowey.mybatis.in.action.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.photowey.mybatis.in.action.domain.entity.Organization;

/**
 * {@code OrganizationService} Service
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
public interface OrganizationService extends IService<Organization> {

    void handleRequiredSave();

    void handleNestedSave();

}
