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
package com.photowey.mybatis.plus.join.in.action.domain.vo;

import com.photowey.mybatis.plus.join.in.action.domain.entity.TestUserJson;
import com.photowey.mybatis.plus.join.in.action.domain.entity.UsersAge;
import lombok.Data;

import java.util.Date;

/**
 * {@code UsersVo}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
@Data
public class UsersVo {

    private Long userId;

    private String userName;

    private Date createTime;

    private Integer ageId;

    private Long ageTableId;

    private String mpnb;

    private String ageDoc;

    private String users_age_name;

    private UsersAge usersAge;

    private TestUserJson contentJson;

}
