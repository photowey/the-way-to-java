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
package com.photowey.mongo.in.action.config;

import com.photowey.mongo.in.action.generator.IKeyGenerator;
import com.photowey.mongo.in.action.generator.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * {@code MongoConfigure}
 *
 * @author photowey
 * @date 2021/11/23
 * @since 1.0.0
 */
@Configuration
public class MongoConfigure {

    /**
     * {@link MongoTransactionManager}
     *
     * @param factory {@link MongoDatabaseFactory}
     * @return {@link MongoTransactionManager}
     */
    @Bean
    public MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);
    }

    /**
     * {@link IKeyGenerator}
     *
     * @return {@link IKeyGenerator}
     */
    @Bean("mongoKeyGenerator")
    public IKeyGenerator keyGenerator() {
        return new KeyGenerator();
    }
}
