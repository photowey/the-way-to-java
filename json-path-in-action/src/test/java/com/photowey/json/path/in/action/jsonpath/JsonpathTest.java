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
package com.photowey.json.path.in.action.jsonpath;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.jayway.jsonpath.*;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code JsonpathTest}
 *
 * @author photowey
 * @date 2022/10/23
 * @since 1.0.0
 */
@Slf4j
class JsonpathTest {

    @Test
    void testSimpleAPI() {
        List<String> authors = JsonPath.read(JSON_DOCUMENT, "$.store.book[*].author");

        log.info("authors: {}", authors);
    }

    @Test
    void testConfiguration() {
        Configuration conf = Configuration.builder()
                .jsonProvider(new JacksonJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .build();

        Book book = JsonPath.using(conf).parse(JSON_DOCUMENT).read("$.store.book[0]", Book.class);

        log.info("book info: \n{}", JSON.toJSONString(book, JSONWriter.Feature.PrettyFormat));
    }

    @Test
    void testJsonPathList() {
        List<String> authors = JsonPath.read(JSON_DOCUMENT, "$.store.book[*].author");
        System.out.println(authors);
    }

    @Test
    void testJsonProvider() {
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(JSON_DOCUMENT);

        String author0 = JsonPath.read(document, "$.store.book[0].author");
        String author1 = JsonPath.read(document, "$.store.book[1].author");

        System.out.println(author0);
        System.out.println(author1);
    }

    @Test
    void testJsonExpression() {
        ReadContext ctx = JsonPath.parse(JSON_DOCUMENT);

        List<String> authorsOfBooksWithISBN = ctx.read("$.store.book[?(@.isbn)].author");

        System.out.println(authorsOfBooksWithISBN);

        Configuration configuration = Configuration.defaultConfiguration();
        List<Map<String, Object>> expensiveBooks = JsonPath
                .using(configuration)
                .parse(JSON_DOCUMENT)
                .read("$.store.book[?(@.price > 10)]", List.class);

        System.out.println(expensiveBooks);
    }

    @Test
    void testCastException() {
        // Works fine
        String author = JsonPath.parse(JSON_DOCUMENT).read("$.store.book[0].author");
        System.out.println(author);

        // Will throw an java.lang.ClassCastException
        Assertions.assertThrows(ClassCastException.class, () -> {
            List<String> list = JsonPath.parse(JSON_DOCUMENT).read("$.store.book[0].author");
            System.out.println(list);
        });
    }

    @Test
    void testParseDate() {
        String json = "{\"date_as_long\" : 1411455611975}";
        Date date = JsonPath.parse(json).read("$['date_as_long']", Date.class);

        System.out.println(date);
    }

    /**
     * <pre>
     * {
     * 	"author":"Nigel Rees",
     * 	"category":"reference",
     * 	"price":8.95,
     * 	"title":"Sayings of the Century"
     * }
     * </pre>
     */
    @Test
    void testParsePojo() {
        Configuration configuration = this.jacksonJsonProvider();
        Book book = JsonPath.using(configuration).parse(JSON_DOCUMENT).read("$.store.book[0]", Book.class);

        System.out.println(JSON.toJSONString(book, JSONWriter.Feature.PrettyFormat));
    }

    @Test
    void testTypeRef() {
        Configuration configuration = this.jacksonJsonProvider();
        TypeRef<List<String>> typeRef = new TypeRef<List<String>>() {
        };
        List<String> titles = JsonPath.using(configuration).parse(JSON_DOCUMENT).read("$.store.book[*].title", typeRef);

        System.out.println(titles);
    }

    @Test
    void testInlinePredicate() {
        Configuration configuration = this.jacksonJsonProvider();
        List<Map<String, Object>> books = JsonPath.using(configuration).parse(JSON_DOCUMENT).read("$.store.book[?(@.price < 10)]");

        System.out.println(JSON.toJSONString(books, JSONWriter.Feature.PrettyFormat));
    }

    @Test
    void testFilterPredicate() {
        Filter cheapFictionFilter = Filter.filter(
                Criteria.where("category").is("fiction").and("price").lte(10D)
        );

        List<Map<String, Object>> books = JsonPath.parse(JSON_DOCUMENT).read("$.store.book[?]", cheapFictionFilter);

        System.out.println(JSON.toJSONString(books, JSONWriter.Feature.PrettyFormat));

        Filter fooOrBar = Filter.filter(
                Criteria.where("foo").exists(true)).or(Criteria.where("bar").exists(true)
        );
        Filter fooAndBar = Filter.filter(
                Criteria.where("foo").exists(true)).and(Criteria.where("bar").exists(true)
        );
    }

    @Test
    void testCustomPredicate() {

        Predicate booksWithISBN = new Predicate() {
            @Override
            public boolean apply(PredicateContext ctx) {
                return ctx.item(Map.class).containsKey("isbn");
            }
        };

        List<Map<String, Object>> books = JsonPath.parse(JSON_DOCUMENT).read("$.store.book[?]", (ctx) -> {
            return ctx.item(Map.class).containsKey("isbn");
        });

        System.out.println(JSON.toJSONString(books, JSONWriter.Feature.PrettyFormat));
    }

    @Test
    void testPathList() {
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> pathList = JsonPath.using(conf).parse(JSON_DOCUMENT).read("$..author");

        assertThat(pathList).containsExactly(
                "$['store']['book'][0]['author']",
                "$['store']['book'][1]['author']",
                "$['store']['book'][2]['author']",
                "$['store']['book'][3]['author']"
        );
    }

    @Test
    void testAddNode() {
        String newJson = JsonPath.parse(JSON_DOCUMENT)
                .put(JsonPath.compile("$"), "host", "192.168.19.250:9527")
                .put(JsonPath.compile("$"), "basePath", "uaa")
                .jsonString();

        System.out.println(newJson);
    }

    private Configuration jacksonJsonProvider() {
        Configuration configuration = Configuration.builder()
                .jsonProvider(new JacksonJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .build();

        return configuration;
    }

    @Data
    public static class Book implements Serializable {

        private static final long serialVersionUID = 2172990445632454919L;

        private String category;
        private String author;
        private String title;
        private BigDecimal price;
    }

    static final String JSON_DOCUMENT = "{\n" +
            "    \"store\": {\n" +
            "        \"book\": [\n" +
            "            {\n" +
            "                \"category\": \"reference\",\n" +
            "                \"author\": \"Nigel Rees\",\n" +
            "                \"title\": \"Sayings of the Century\",\n" +
            "                \"price\": 8.95\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"Evelyn Waugh\",\n" +
            "                \"title\": \"Sword of Honour\",\n" +
            "                \"price\": 12.99\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"Herman Melville\",\n" +
            "                \"title\": \"Moby Dick\",\n" +
            "                \"isbn\": \"0-553-21311-3\",\n" +
            "                \"price\": 8.99\n" +
            "            },\n" +
            "            {\n" +
            "                \"category\": \"fiction\",\n" +
            "                \"author\": \"J. R. R. Tolkien\",\n" +
            "                \"title\": \"The Lord of the Rings\",\n" +
            "                \"isbn\": \"0-395-19395-8\",\n" +
            "                \"price\": 22.99\n" +
            "            }\n" +
            "        ],\n" +
            "        \"bicycle\": {\n" +
            "            \"color\": \"red\",\n" +
            "            \"price\": 19.95\n" +
            "        }\n" +
            "    },\n" +
            "    \"expensive\": 10\n" +
            "}";

}
