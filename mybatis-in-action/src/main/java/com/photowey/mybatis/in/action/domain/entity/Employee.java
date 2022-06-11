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
package com.photowey.mybatis.in.action.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.photowey.mybatis.in.action.mybatis.dynamic.enums.IdTypeEnum;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code Employee}
 *
 * @author photowey
 * @date 2021/11/02
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("Employee")
@TableName(value = "sys_employee")
@EqualsAndHashCode(callSuper = true)
@com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableName(value = "sys_employee")
public class Employee extends Model<Employee> {

    /**
     * 主键标识
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableId(value = "id", type = IdTypeEnum.INPUT)
    private Long id;
    /**
     * 工号
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("employee_no")
    private String employeeNo;
    /**
     * 隶属机构标识
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("org_id")
    private Long orgId;
    /**
     * 隶属机构名称
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("org_name")
    private String orgName;
    /**
     * 序号
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("order_no")
    private Integer orderNo;
    /**
     * 状态
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("status")
    private Integer status;
    /**
     * 备注
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("remark")
    private String remark;
    /**
     * 创建时间
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("gmt_create")
    @TableField("gmt_create")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("create_by")
    private Long createBy;
    /**
     * 更新时间
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("gmt_modified")
    @TableField("gmt_modified")
    private LocalDateTime modifiedTime;
    /**
     * 更新人
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("update_by")
    private Long updateBy;
    /**
     * 删除标记
     */
    @com.photowey.mybatis.in.action.mybatis.dynamic.annotation.TableField("deleted")
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
