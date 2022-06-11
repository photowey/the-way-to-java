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
package com.photowey.provider.sentinel.in.action.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * {@code Organization}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_organization")
@EqualsAndHashCode(callSuper = true)
public class Organization extends Model<Organization> {

    /**
     * 主键标识
     */
    private Long id;
    /**
     * 父节点标识
     */
    private Long parentId;
    /**
     * 族谱
     */
    private String familyMap;
    /**
     * 组织名称
     */
    private String orgName;
    /**
     * 组织编码
     */
    private String orgCode;
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
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 更新时间
     */
    @TableField("gmt_modified")
    private LocalDateTime modifiedTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 删除标记
     */
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
