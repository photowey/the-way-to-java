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
package com.photowey.mybatis.plus.join.in.action.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import icu.mhb.mybatisplus.plugln.config.MybatisPlusJoinConfig;
import icu.mhb.mybatisplus.plugln.injector.JoinDefaultSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * {@code MyBatisPlusConfigure}
 *
 * @author photowey
 * @date 2022/08/18
 * @since 1.0.0
 */
@Configuration
public class MyBatisPlusConfigure extends JoinDefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        return super.getMethodList(mapperClass, tableInfo);
    }

    @Bean
    public MybatisPlusPropertiesCustomizer plusPropertiesCustomizer() {
        MybatisPlusJoinConfig.builder()
                // 查询字段别名关键字
                .columnAliasKeyword("as")
                // 表、left join、right join、inner join 表别名关键字
                .tableAliasKeyword("as")
                .build();
        return MybatisPlusProperties::getGlobalConfig;
    }

//    @Bean
//    public MybatisPlusJoinConfig mybatisPlusJoinConfig() {
//        return MybatisPlusJoinConfig.builder()
//                // 查询字段别名关键字
//                .columnAliasKeyword("as")
//                // 表、left join、right join、inner join 表别名关键字
//                .tableAliasKeyword("is")
//                .build();
//    }

}
