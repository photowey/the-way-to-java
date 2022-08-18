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
package com.photowey.mybatis.plus.join.in.action.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersAgesVo;
import com.photowey.mybatis.plus.join.in.action.domain.vo.UsersVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * {@code UsersServiceTest}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
@SpringBootTest
class UsersServiceTest {

    @Autowired
    private UsersService usersService;

    @Autowired
    private UsersAgeService usersAgeService;

    @Test
    void contextLoads() {

    }

    @Test
    void getByAgeName() {
        List<UsersVo> byAgeName = this.usersService.findByAgeName("90");
        System.out.println(JSON.toJSONString(byAgeName));
    }

    @Test
    void getByAgeName1() {
        List<UsersAgesVo> usersAgesVos = this.usersService.test1("90");
        System.out.println(JSON.toJSONString(usersAgesVos));
    }

    @Test
    void getCountByAgeName() {
        int countByAgeName = this.usersService.getCountByAgeName("90");
        System.out.println(countByAgeName);
    }

    @Test
    void testIds() {
        List<Integer> ids = this.usersService.getIds();
        System.out.println(ids);
    }

    @Test
    void testOneToOne() {
        List<UsersVo> usersVos = usersService.oneToOne();
        System.out.println(JSON.toJSONString(usersVos));
    }

    @Test
    void testManyToMany() {
        List<UsersAgesVo> usersAgesVos = usersAgeService.manyToMany();
        System.out.println(JSON.toJSONString(usersAgesVos));
    }

    @Test
    void testUserName() {
        String userName = usersService.getUserName();
        System.out.println(userName);
    }

    @Test
    void testPage() {
        Page<UsersVo> page = usersService.page();
        System.out.println(JSON.toJSONString(page));
    }

    @Test
    void testAliasAndReturnMap() {
        List<Map> maps = this.usersService.customizeAlias();
        System.out.println(JSON.toJSONString(maps));
    }

}