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
package com.photowey.mybatis.plus.mate.in.action.domain.dto;

import com.photowey.mybatis.plus.mate.in.action.domain.User;
import com.photowey.mybatis.plus.mate.in.action.domain.UserInfo;
import lombok.Data;

import java.util.List;

/**
 * {@code UserDto}
 *
 * @author photowey
 * @date 2022/06/23
 * @since 1.0.0
 */
@Data
public class UserDto extends User {

    private List<UserInfo> userInfos;
}
