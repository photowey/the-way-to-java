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
package com.photowey.mybatis.plus.join.in.action.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.mybatis.plus.join.in.action.domain.entity.Users;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersAgesVo;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersVo;
import icu.mhb.mybatisplus.plugln.base.service.JoinIService;

import java.util.List;
import java.util.Map;

/**
 * {@code UsersService}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
public interface UsersService extends JoinIService<Users> {

    /**
     * 基础多表示例
     */
    List<UsersVo> findByAgeName(String ageName);

    List<UsersAgesVo> test1(String ageName);

    /**
     * 一对一实例
     */
    List<UsersVo> oneToOne();

    /**
     * 获取单个实例
     */
    UsersVo getByAgeName(String ageName);

    /**
     * 获取count实例
     *
     * @return
     */
    int getCountByAgeName(String ageName);

    /**
     * 分页查询
     */
    Page<UsersVo> page();

    /**
     * 获取单个id列表示例
     *
     * @return id列表
     */
    List<Integer> getIds();

    /**
     * 获取单个姓名示例
     *
     * @return 姓名
     */
    String getUserName();

    /**
     * 自定义别名
     *
     * @return String
     */
    List<Map> customizeAlias();

}
