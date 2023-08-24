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
package com.photowey.mongo.in.action.service.impl;

import com.photowey.mongo.in.action.domain.entity.Book;
import com.photowey.mongo.in.action.engine.IMongoEngine;
import com.photowey.mongo.in.action.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * {@code BookServiceImpl}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private IMongoEngine mongoEngine;

    /**
     * 新增-书籍
     *
     * @param book {@link Book}对象
     * @return {@link Book}
     */
    @Override
    public Book saveBook(Book book) {
        LocalDateTime now = LocalDateTime.now();
        book.setCreateTime(now);
        book.setUpdateTime(now);
        this.mongoEngine.repositoryEngine().bookRepository().save(book);
        return book;
    }

    /**
     * 根据主键标识-删除对象
     *
     * @param id 主键标识
     * @return 布尔值
     */
    @Override
    public boolean deleteById(String id) {
        this.mongoEngine.repositoryEngine().bookRepository().deleteById(id);
        return true;
    }

    /**
     * 更新-书籍
     *
     * @param book {@link Book}对象
     * @return {@link Book}
     */
    @Override
    public Book updateBook(Book book) {
        LocalDateTime now = LocalDateTime.now();
        book.setCreateTime(now);
        book.setUpdateTime(now);

        // TODO 没有直接的更新接口

        return book;
    }

    /**
     * 根据主键标识-查询对象
     *
     * @param id 主键标识
     * @return {@link Book}
     */
    @Override
    public Book findById(String id) {
        return this.mongoEngine.repositoryEngine().bookRepository().findById(id).orElse(null);
    }

    /**
     * 查询列表-不分页
     *
     * @return {@link Book}
     */
    @Override
    public List<Book> findAll() {
        return this.mongoEngine.repositoryEngine().bookRepository().findAll();
    }

    /**
     * 分页查询
     *
     * @param page {@link Pageable} 分页对象
     * @return {@link Page} 分页列表
     */
    @Override
    public Page<Book> findAll(Pageable page) {
        // PageRequest pageable = new PageRequest(pageNo - 1, pageSize);
        return this.mongoEngine.repositoryEngine().bookRepository().findAll(page);
    }
}
