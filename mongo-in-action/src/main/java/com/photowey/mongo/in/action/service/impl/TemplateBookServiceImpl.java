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
package com.photowey.mongo.in.action.service.impl;

import com.photowey.mongo.in.action.domain.entity.Book;
import com.photowey.mongo.in.action.engine.IMongoEngine;
import com.photowey.mongo.in.action.service.TemplateBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code TemplateBookServiceImpl}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class TemplateBookServiceImpl implements TemplateBookService {

    @Autowired
    private IMongoEngine mongoEngine;

    /**
     * 保存对象
     *
     * @param book {@link Book}
     * @return {@link Book}
     */
    public Book saveBook(Book book) {
        LocalDateTime now = LocalDateTime.now();
        book.setCreateTime(now);
        book.setUpdateTime(now);
        this.mongoEngine.mongoTemplate().save(book);

        return book;
    }

    /***
     * 删除对象
     *
     * @param book {@link Book}对象
     * @return {@code boolean}
     */
    public boolean deleteBook(Book book) {
        this.mongoEngine.mongoTemplate().remove(book);
        return true;
    }

    /**
     * 根据主键标识-删除对象
     *
     * @param id 主键标识
     * @return {@code boolean}
     */
    @Override
    public boolean deleteById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        this.mongoEngine.mongoTemplate().remove(query, Book.class);
        return true;
    }

    /**
     * 更新-书籍
     * <p>
     * updateFirst 更新查询返回结果集的第一条
     * updateMulti 更新查询返回结果集的全部
     * this.mongoEngine.mongoTemplate().updateMulti(query,update,Book.class);
     * upsert 更新对象不存在则去添加
     * this.mongoEngine.mongoTemplate().upsert(query,update,Book.class);
     *
     * @param book {@link Book}
     * @return {@link Book}
     */
    public Book updateBook(Book book) {
        Query query = new Query(Criteria.where("id").is(book.getId()));
        LocalDateTime now = LocalDateTime.now();
        Update update = new Update()
                .set("publish", book.getPublish())
                .set("info", book.getInfo())
                .set("updateTime", now);
        this.mongoEngine.mongoTemplate().updateFirst(query, update, Book.class);

        return book;
    }

    /**
     * 查询列表-不分页
     *
     * @return {@link Book}
     */
    public List<Book> findAll() {
        return this.mongoEngine.mongoTemplate().findAll(Book.class);
    }

    /**
     * 根据主键标识-查询对象
     *
     * @param id 主键标识
     * @return {@link Book}
     */
    public Book findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return this.mongoEngine.mongoTemplate().findOne(query, Book.class);
    }

    /**
     * 根据出版社-模糊匹配
     *
     * @param publishName 出版社名称
     * @return {@link Book}
     */
    @Override
    public List<Book> findBookByPublishLike(String publishName) {
        Query query = new Query(Criteria.where("publish").regex(".*?\\" + publishName + ".*"));
        return this.mongoEngine.mongoTemplate().find(query, Book.class);
    }
}
