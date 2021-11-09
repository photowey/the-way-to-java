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
package com.photowey.data.filter.in.action.query;

import com.photowey.data.filter.in.action.core.enums.AggregationType;
import com.photowey.data.filter.in.action.core.operation.filter.Filter;
import com.photowey.data.filter.in.action.core.operation.groupby.GroupBy;
import com.photowey.data.filter.in.action.core.operation.limit.Limit;
import com.photowey.data.filter.in.action.core.operation.orderby.OrderBy;
import com.photowey.data.filter.in.action.core.operation.where.Where;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code JavaQueryTest}
 *
 * @author photowey
 * @date 2021/11/09
 * @since 1.0.0
 */
@Slf4j
class JavaQueryTest {

    @Test
    void testWhere() {
        List<Filter> filters = new ArrayList<>();
        filters.add(new FilterImpl().operation("and").field("name").targetValue("photowey"));
        filters.add(new FilterImpl().operation("or").field("age").targetValue("21"));

        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                data.add(new User(i, "photowey", 19));
            } else if (i % 33 == 0) {
                data.add(new User(i, "photoshark", 21));
            } else
                data.add(new User(i, "photowey" + i, 1 + i));
        }

        Where.Condition condition = new Where.Condition(filters);
        Where where = new Where(condition);

        JavaQuery javaQuery = new JavaQuery();
        List<Object> result = javaQuery.query(data, where, null, null, null);

        Assertions.assertEquals(13, result.size());

    }

    @Test
    void testOrderBy() {
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                data.add(new User(i, "photowey", 19));
            } else if (i % 33 == 0) {
                data.add(new User(i, "photoshark", 21));
            } else
                data.add(new User(i, "photowey" + i, 1 + i));
        }

        // id=98:name=photowey98:age=99
        // -> id=98:name=photowey99:age=21
        data.add(new User(98, "photowey99", 21));

        List<String> fields = new ArrayList<>();
        fields.add("name");
        fields.add("id");
        List<Boolean> types = new ArrayList<>();
        types.add(false);
        types.add(true);
        OrderBy.Condition condition = new OrderBy.Condition(fields, types);
        OrderBy orderBy = new OrderBy(condition);

        JavaQuery javaQuery = new JavaQuery();
        List<Object> query = javaQuery.query(data, null, orderBy, null, null);
        Object first = query.get(0);

        Assertions.assertEquals("id=98:name=photowey99:age=21", first.toString());

    }

    @Test
    void testGroupBy() {
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 10 == 0) {
                data.add(new User(i, "photowey", 19));
            } else if (i % 33 == 0) {
                data.add(new User(i, "photoshark", 21));
            } else
                data.add(new User(i, "photowey" + i, 1 + i));
        }

        data.add(new User(10, "photowey", 19));
        data.add(new User(10, "photowey", 20));
        data.add(new User(20, "photowey", 19));
        data.add(new User(33, "photoshark", 21));

        List<String> keys = new ArrayList<>();
        keys.add("name");
        keys.add("age");
        List<AggregationType> types = new ArrayList<>();
        AggregationType count = AggregationType.COUNT;
        count.field("id");
        count.type("COUNT");
        AggregationType sum = AggregationType.SUM;
        sum.field("age");
        sum.type("SUM");

        types.add(count);
        types.add(sum);

        GroupBy.Condition condition = new GroupBy.Condition(keys, types);
        GroupBy groupBy = new GroupBy(condition);

        JavaQuery javaQuery = new JavaQuery();
        List<Object> candidates = javaQuery.query(data, null, null, groupBy, null);

        int countTotal = 0;

        for (Object candidate : candidates) {
            if ("{COUNT(id)=3, name=photowey, id=0, age=19, SUM(age)=57}".equals(candidate.toString())) {
                countTotal++;
            }
        }

        Assertions.assertEquals(1, countTotal);

    }

    @Test
    public void testLimit() {
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                data.add(new User(i, "photowey", 19));
            } else if (i % 33 == 0) {
                data.add(new User(i, "photoshark", 21));
            } else
                data.add(new User(i, "photowey" + i, 1 + i));
        }

        Limit.Condition condition = new Limit.Condition(5, 11);
        Limit limit = new Limit(condition);
        JavaQuery javaQuery = new JavaQuery();

        List<Object> query = javaQuery.query(data, null, null, null, limit);
        User first = (User) query.get(0);
        User tail = (User) query.get(5);

        Assertions.assertEquals(5, first.id());
        Assertions.assertEquals(10, tail.id());

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true)
    public static class FilterImpl implements Filter {
        String operation;
        String field;
        String targetValue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(fluent = true)
    public static class User {
        private int id;
        private String name;
        private int age;

        @Override
        public String toString() {
            return "id=" + id + ":name=" + name + ":age=" + age;
        }
    }
}