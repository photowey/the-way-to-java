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
package com.photowey.rocketmq.in.action.message;

/**
 * {@code AbstractMessageActor}
 *
 * @author photowey
 * @date 2021/10/31
 * @since 1.0.0
 */
public abstract class AbstractMessageActor {

    protected static final String[] TOPIC_PART_ORDER_MESSAGE_TAGS = new String[]{
            "rocketmq-topic-part-order-tagA",
            "rocketmq-topic-part-order-tagB",
            "rocketmq-topic-part-order-tagC"
    };

    protected static final String[] TOPIC_TAG_FILTER_MESSAGE_TAGS = new String[]{
            "rocketmq-topic-tag-filter-tagA",
            "rocketmq-topic-tag-filter-tagB",
            "rocketmq-topic-tag-filter-tagC"
    };

    protected static final String[] TOPIC_SQL_FILTER_MESSAGE_TAGS = new String[]{
            "rocketmq-topic-sql-filter-tagA",
            "rocketmq-topic-sql-filter-tagB",
            "rocketmq-topic-sql-filter-tagC"
    };
}
