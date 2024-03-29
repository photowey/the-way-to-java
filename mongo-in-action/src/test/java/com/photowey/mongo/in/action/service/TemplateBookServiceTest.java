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
package com.photowey.mongo.in.action.service;

import com.photowey.mongo.in.action.domain.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * {@code TemplateBookServiceTest}
 *
 * @author photowey
 * @date 2021/11/24
 * @since 1.0.0
 */
@SpringBootTest
class TemplateBookServiceTest extends AbstractMongoTest {

    @Test
    void saveBook() {
        String nextId = this.mongoEngine.keyGenerator().nextStrId();
        Book book = this.populateBook(nextId, 9527);
        this.mongoEngine.serviceEngine().templateBookService().saveBook(book);
        Book bookDb = this.mongoEngine.serviceEngine().templateBookService().findById(nextId);
        Assertions.assertNotNull(bookDb, "TemplateBookService failure!");
    }

    @Test
    void testUpdateBook() {
        // {@link com.photowey.mongo.in.action.service.TemplateBookServiceTest#saveBook}
        String id = "64300273238122790";
        Book bookDb = this.mongoEngine.serviceEngine().templateBookService().findById(id);
        bookDb.setPublish("机械工业出版社");
        this.mongoEngine.serviceEngine().templateBookService().updateBook(bookDb);
        Book bookUpdate = this.mongoEngine.serviceEngine().templateBookService().findById(id);
        Assertions.assertEquals("机械工业出版社", bookUpdate.getPublish(), "TemplateBookService failure!");
    }

    @Test
    void testBookLikeQuery() {
        String publishName = "工业出版社";
        // { "publish" : { "$regularExpression" : { "pattern" : ".*?工业出版社.*", "options" : ""}}}
        List<Book> books = this.mongoEngine.serviceEngine().templateBookService().findBookByPublishLike(publishName);

        Assertions.assertEquals(4, books.size());
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }

    @Test
    void testBookLikeQuery_v2() {
        String publishName = "工业出版社";
        List<Book> books = this.mongoEngine.serviceEngine().templateBookService().findBookByPublishLike_v2(publishName);

        Assertions.assertEquals(4, books.size());
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }

    @Test
    void testBookLikeQuery_v3() {
        String publishName = "机械";
        List<Book> books = this.mongoEngine.serviceEngine().templateBookService().findBookByPublishLike_v2(publishName);

        Assertions.assertEquals(2, books.size());
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }

    @Test
    void testBookLikeQuery_v4() {
        String publishName = "工业出版社";
        // { "publish" : { "$regularExpression" : { "pattern" : "工业出版社", "options" : ""}}}
        List<Book> books = this.mongoEngine.repositoryEngine().bookRepository().findByPublishLike(publishName);

        Assertions.assertEquals(4, books.size());
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }

    @Test
    void testBookLikeQuery_v5() {
        String publishName = "机械";
        List<Book> books = this.mongoEngine.repositoryEngine().bookRepository().findByPublishLike(publishName);

        Assertions.assertEquals(2, books.size());
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }

    @Test
    void testBookEqQuery() {
        String publishName = "机械工业出版社";
        List<Book> books = this.mongoEngine.repositoryEngine().bookRepository().findByPublish(publishName);

        // { "publish" : "机械工业出版社"}
        Assertions.assertEquals(2, books.size());
        books.forEach(book -> {
            Assertions.assertEquals(publishName, book.getPublish(), "TemplateBookService failure!");
        });
    }

    @Test
    void testMongoAware() {
        String publishName = "机械工业出版社";
        List<Book> books = this.awareBookService.mongoEngine().repositoryEngine().bookRepository().findByPublish(publishName);

        // { "publish" : "机械工业出版社"}
        Assertions.assertEquals(2, books.size());
        books.forEach(book -> {
            Assertions.assertEquals(publishName, book.getPublish(), "TemplateBookService failure!");
        });
    }
}