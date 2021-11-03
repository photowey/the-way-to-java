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
