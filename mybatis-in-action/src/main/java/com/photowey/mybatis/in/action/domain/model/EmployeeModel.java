package com.photowey.mybatis.in.action.domain.model;

import lombok.Data;

import java.io.Serializable;

/**
 * {@code EmployeeModel}
 *
 * @author photowey
 * @date 2021/11/07
 * @since 1.0.0
 */
@Data
public class EmployeeModel implements Serializable {

    private static final long serialVersionUID = 9026337029048321211L;

    /**
     * 主键标识
     */
    private Long id;
    /**
     * 工号
     */
    private String employeeNo;
    /**
     * 隶属机构标识
     */
    private Long orgId;
    /**
     * 隶属机构名称
     */
    private String orgName;
    /**
     * 序号
     */
    private Integer orderNo;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
}
