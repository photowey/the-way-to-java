/*
 * Copyright © 2021 the original author or authors.
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
package com.photowey.mybatis.plus.join.in.action.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * {@code Users}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@TableName(value = "users", autoResultMap = true)
public class Users {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;
    private String userName;
    private Date createTime;
    @TableLogic
    private Long ageId;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    private TestUserJson contentJson;

}
