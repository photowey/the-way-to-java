/*
 * Copyright © 2021 photowey (photowey@gmail.com)
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
package com.photowey.solr.in.action.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.photowey.solr.in.action.domain.entity.Area;
import com.photowey.solr.in.action.pinyin.PinyinUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;

/**
 * {@code AreaRepositoryTest}
 *
 * @author photowey
 * @date 2022/03/20
 * @since 1.0.0
 */
@Slf4j
@SpringBootTest
class AreaRepositoryTest {

    @Autowired
    private AreaRepository areaRepository;

    @Test
    void testHanyupinyin() {
        String word = "勐腊县";
        String pinYin = PinyinUtils.toPinYin(word, "_");
        Assertions.assertEquals("meng_la_xian", pinYin);
    }

    @Test
    void testUpdateFirstField() {
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("first");
        List<Area> areaList = this.areaRepository.selectList(queryWrapper);
        areaList.stream().filter((area) -> !StringUtils.hasText(area.getFirst())).forEach((area) -> {
            String pinYin = PinyinUtils.toPinYin(area.getShortName(), "_");
            log.info("the area short name:[{}],pinyin is:[{}]", area.getShortName(), pinYin);
            if (StringUtils.hasText(pinYin)) {
                area.setFirst(pinYin.substring(0, 1).toUpperCase(Locale.ROOT));
            }
        });

        for (Area area : areaList) {
            this.areaRepository.updateById(area);
        }

    }

}