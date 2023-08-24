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
package com.photowey.mongo.in.action.ext.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

/**
 * {@code MongoRepositoryExt}
 *
 * @author photowey
 * @date 2022/12/23
 * @since 1.0.0
 */
public interface MongoRepositoryExt<T, ID> extends MongoRepository<T, ID> {

    /**
     * 根据
     * 业务主键查询
     *
     * @param pk   业务主键标识
     * @param <PK> 业务主键类型
     * @return {@link T} 类型
     */
    default <PK> T findByIdx(PK pk) {
        ID id = this.wrapId(pk);
        return this.findById(id).orElse(null);
    }

    /**
     * 根据
     * 业务主键列表-查询 {@code MongoDB} 文档
     *
     * @param ids 主键标识列表
     * @return List<T>
     */
    List<T> findByIdIn(Collection<ID> ids);

    /**
     * 根据
     * 业务主键删除
     *
     * @param pk   业务主键标识
     * @param <PK> 业务主键类型
     */
    default <PK> void deleteByIdx(PK pk) {
        ID id = this.wrapId(pk);
        this.deleteById(id);
    }

    /**
     * 根据
     * 业务主键-判断文档是否存在
     *
     * @param pk   业务主键标识
     * @param <PK> 业务主键类型
     */
    default <PK> boolean existsByIdx(PK pk) {
        ID id = this.wrapId(pk);
        return this.existsById(id);
    }

    default <PK> ID wrapId(PK pk) {
        ID id = (ID) String.valueOf(pk);

        return id;
    }

}
