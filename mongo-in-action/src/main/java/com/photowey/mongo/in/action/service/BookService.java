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
package com.photowey.mongo.in.action.service;

import com.photowey.mongo.in.action.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@code BookService}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
public interface BookService {
    /**
     * 新增-书籍
     *
     * @param book {@link Book}对象
     * @return {@link Book}
     */
    Book saveBook(Book book);

    /**
     * 根据主键标识-删除对象
     *
     * @param id 主键标识
     * @return {@code boolean}
     */
    boolean deleteById(String id);

    /**
     * 更新-书籍
     *
     * @param book {@link Book}对象
     * @return {@link Book}
     */
    Book updateBook(Book book);

    /**
     * 根据主键标识-查询对象
     *
     * @param id 主键标识
     * @return {@link Book}
     */
    Book findById(String id);

    /**
     * 查询列表-不分页
     *
     * @return {@link Book}
     */
    List<Book> findAll();

    /**
     * 分页查询
     *
     * @param page {@link Pageable} 分页对象
     * @return {@link Page} 分页列表
     */
    Page<Book> findAll(Pageable page);
}
