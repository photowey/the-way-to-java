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
        List<Book> books = this.mongoEngine.serviceEngine().templateBookService().findBookByPublishLike(publishName);
        books.forEach(book -> {
            Assertions.assertTrue(book.getPublish().contains(publishName), "TemplateBookService failure!");
        });
    }
}