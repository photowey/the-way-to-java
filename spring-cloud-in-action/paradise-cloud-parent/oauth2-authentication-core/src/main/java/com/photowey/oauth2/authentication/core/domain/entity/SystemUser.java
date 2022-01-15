package com.photowey.oauth2.authentication.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
public class SystemUser {

    private Long id;
    private String userId;
    private String userName;
    private String password;
    private String nickName;
    private Integer gender;
    private String avatar;
    private String mobile;
    private String email;
    private Integer status;

    @TableField("gmt_create")
    private LocalDateTime createTime;
    @TableField("gmt_modified")
    private LocalDateTime modifiedTime;
}
