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
package com.photowey.mybatis.plus.join.in.action.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.mybatis.plus.join.in.action.domain.entity.Users;
import com.photowey.mybatis.plus.join.in.action.domain.entity.UsersAge;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersAgesVo;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersVo;
import com.photowey.mybatis.plus.join.in.action.mapper.UsersMapper;
import com.photowey.mybatis.plus.join.in.action.service.UsersService;
import icu.mhb.mybatisplus.plugln.base.service.impl.JoinServiceImpl;
import icu.mhb.mybatisplus.plugln.core.JoinLambdaWrapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * {@code UsersServiceImpl}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
@Service
public class UsersServiceImpl extends JoinServiceImpl<UsersMapper, Users> implements UsersService {

    @Override
    public List<UsersVo> findByAgeName(String ageName) {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(new Users().setUserId(1L));
        wrapper
                .orderByDesc(Users::getUserId)
                //.notDefaultSelectAll()
                .groupBy(Users::getAgeId)
                .selectAll(Arrays.asList(Users::getUserName, Users::getAgeId));

        wrapper.leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId)
                .select(UsersAge::getAgeDoc)
                .selectAs((cb -> {
                    cb.add(UsersAge::getAgeDoc, UsersAge::getAgeName)
                            .add(UsersAge::getId)
                            .add(UsersAge::getAgeName, "usersAgeName")
                            .add(UsersAge::getId, "age_table_id")
                            .add("", "mpnb");
                }))
                .eq(ageName != null, UsersAge::getAgeName, ageName)
                .orderByAsc(UsersAge::getId)
                .groupBy(UsersAge::getId)
                .end();


        return super.joinList(wrapper, UsersVo.class);
    }

    @Override
    public List<UsersAgesVo> test1(String ageName) {
        JoinLambdaWrapper<UsersAgesVo> wrapper = joinLambdaQueryWrapper(UsersAgesVo.class).eq(UsersAgesVo::getId, 1)
                .select(UsersAgesVo::getId);

        return super.joinList(wrapper, UsersAgesVo.class);
    }

    @Override
    public List<UsersVo> oneToOne() {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class)
                .selectAs((cb) -> {
                    cb.add(Users::getUserId, Users::getUserName, Users::getCreateTime)
                            .add("11", "ageTableId")
                            .add(Users::getUserName, "mpnb");
                });

        wrapper.leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId)
                .oneToOneSelect(UsersVo::getUsersAge, (cb) -> {
                    cb.add(UsersAge::getAgeDoc, UsersAge::getAgeName)
                            .add(UsersAge::getId, "ageId", UsersAge::getId);
                }).end();

        return super.joinList(wrapper, UsersVo.class);
    }

    @Override
    public UsersVo getByAgeName(String ageName) {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class);

        wrapper.select(Users::getUserId, Users::getUserName)
                .leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId)
                .oneToOneSelect(UsersVo::getUsersAge, (cb) -> {
                    cb.add(UsersAge::getId, "ageId", UsersAge::getId)
                            .add(UsersAge::getAgeDoc, UsersAge::getAgeName);
                })
                .eq(UsersAge::getAgeName, ageName)
                .end()
                .last("limit 1");

        return super.joinGetOne(wrapper, UsersVo.class);
    }

    @Override
    public int getCountByAgeName(String ageName) {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class);

        wrapper.select(Users::getUserId, Users::getUserName)
                .leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId)
                .eq(UsersAge::getAgeName, ageName).end();

        return super.joinCount(wrapper);
    }


    @SneakyThrows
    @Override
    public Page<UsersVo> page() {
        return super.joinPage(new Page<>(1, 100), new JoinLambdaWrapper<>(Users.class), UsersVo.class);
    }

    @Override
    public List<Integer> getIds() {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class)
                .select(Users::getUserId);

        return super.joinList(wrapper, Integer.class);
    }

    @Override
    public String getUserName() {
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class)
                .select(Users::getUserName)
                .eq(Users::getUserId, 1)
                .last("limit 1");

        return super.joinGetOne(wrapper, String.class);
    }

    @Override
    public List<Map> customizeAlias() {
        // 两个参数代表自定义别名
        JoinLambdaWrapper<Users> wrapper = joinLambdaQueryWrapper(Users.class, "userMaster");
        wrapper
                .select(Users::getUserId, Users::getUserName)
                .leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId, "u_age")
                .select(UsersAge::getAgeDoc).end()
                .leftJoin(UsersAge.class, UsersAge::getId, Users::getAgeId, "u_a")
                .select(UsersAge::getAgeName).end();

        return super.joinList(wrapper, Map.class);
    }

}
