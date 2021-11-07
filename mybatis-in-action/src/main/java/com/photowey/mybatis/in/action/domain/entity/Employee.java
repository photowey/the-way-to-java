package com.photowey.mybatis.in.action.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class Employee extends Model<Employee> {

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
