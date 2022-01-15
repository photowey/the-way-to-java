package com.photowey.oauth2.authentication.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_permission")
public class SystemPermission {

    @TableId
    private Long id;
    private String name;
    private String url;

    @TableField("gmt_create")
    private LocalDateTime createTime;
    @TableField("gmt_modified")
    private LocalDateTime modifiedTime;
}
