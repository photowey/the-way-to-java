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
package com.photowey.shardingsphere.in.action.repository;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.photowey.shardingsphere.in.action.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Random;

/**
 * {@code UserRepositoryTest}
 *
 * @author photowey
 * @date 2022/06/07
 * @since 1.0.0
 */
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSave() {
        Random random = new SecureRandom();
        for (int i = 0; i < 1000; i++) {
            String name = NanoIdUtils.randomNanoId();
            int nextInt = random.nextInt(1000_000_000);
            int idx = nextInt & (DIST.length - 1);
            this.userRepository.insert(new User(name, nextInt % 2 == 0 ? "男" : "女", DIST[idx]));
        }
    }

    @Test
    void testSelectOne() {
        User user = this.userRepository.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, 1534172972074434561L));
        Assertions.assertNotNull(user);
        Assertions.assertEquals("女", user.getGender());
        Assertions.assertEquals("自由与爱之声", user.getData());
    }

    @Test
    void testSelectPage() {
        IPage<User> page = new Page<>(1, 100);
        IPage<User> selectPage = this.userRepository.selectPage(page, Wrappers.<User>lambdaQuery());
        Assertions.assertNotNull(selectPage);
        Assertions.assertEquals(100, selectPage.getRecords().size());
        Assertions.assertEquals(1000, selectPage.getTotal());
        Assertions.assertEquals(1, selectPage.getCurrent());
        Assertions.assertEquals(10, selectPage.getPages());
        Assertions.assertEquals(100, selectPage.getSize());
    }

    @Test
    void testReadWriteSplitting() {
        // 手动更新从库数据 -> 女->男
        // UpDATE `user_1` u SET u.gender = '男' WHERE u.id=1534172972074434561
        User user = this.userRepository.selectOne(Wrappers.<User>lambdaQuery().eq(User::getId, 1534172972074434561L));
        Assertions.assertNotNull(user);
        Assertions.assertEquals("男", user.getGender());
        Assertions.assertEquals("自由与爱之声", user.getData());
    }

    // 刺猬 《幻象波普星》《我们飞向太空》
    private static final String[] DIST = new String[]{
            "走出魂梦",
            "进入太空",
            "放大瞳孔",
            "拂如惊鸿",
            "爱 降临于我 在纯洁的季节",
            "恨 刺痛于我 在思考的昼夜",
            "我幻想的欢乐的伙伴",
            "疏离于时间长河",
            "追逐于内心盛宴",
            "惨死在欲望的街边",
            "世俗 轮转于干燥的物质世界",
            "艺术 自毁于湿润的精神原野",
            "凡客止步",
            "闪烁的城镇河边",
            "小人忙碌",
            "打磨漂亮的鸡毛令箭",
            "忽略那些流言与不解",
            "放眼世界并随心向前",
            "自由与爱之声",
            "穿透了干涸的屏幕",
            "之后 进入了一片 潮湿的坟墓",
            "带着时间永远无法挽回的伤痛",
            "流向永恒 浩瀚的太空",
    };
}