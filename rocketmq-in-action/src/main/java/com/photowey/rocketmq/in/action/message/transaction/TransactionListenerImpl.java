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
package com.photowey.rocketmq.in.action.message.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@code TransactionListenerImpl}
 *
 * @author photowey
 * @date 2021/11/01
 * @since 1.0.0
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    private final AtomicInteger transactionIndex = new AtomicInteger(0);
    private final ConcurrentHashMap<String, Integer> localTransactionStatus = new ConcurrentHashMap<>();

    /**
     * 执行本地事务
     *
     * @param msg {@link MessageExt}
     * @param arg {@link Object}
     * @return {@link LocalTransactionState}
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // TODO 执行本地事务 update A...
        log.info("update A System ... where transactionId:{}", msg.getTransactionId());

        // TODO 情况1: 本地事务成功
        // 执行本地事务成功,确认消息
        // return LocalTransactionState.COMMIT_MESSAGE;*/

        // TODO 情况2: 本地事务失败
        // 执行本地事务失败,删除消息
        // return LocalTransactionState.ROLLBACK_MESSAGE;*/

        // TODO 情况3: 业务复杂,还处于中间过程或者依赖其他操作的返回结果,就是 UNKNOW
        int index = transactionIndex.getAndIncrement();
        int status = index % 3;
        localTransactionStatus.put(msg.getTransactionId(), status);

        return LocalTransactionState.UNKNOW;
    }

    /**
     * 本地事务回查
     *
     * @param msg {@link MessageExt}
     * @return {@link LocalTransactionState}
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String now = now();
        Integer status = localTransactionStatus.get(msg.getTransactionId());
        log.info("rocketmq execute check local transaction,now:[{}]", now);
        if (null != status) {
            switch (status) {
                case 0:
                    log.info("rocketmq execute check local transaction,the status == 0 and so result state is: UNKNOW");
                    return LocalTransactionState.UNKNOW;
                case 1:
                    log.info("rocketmq execute check local transaction,the status == 1 and so result state is: COMMIT_MESSAGE");
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    log.info("rocketmq execute check local transaction,the status == 2 and so result state is: ROLLBACK_MESSAGE");
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    log.info("rocketmq execute check local transaction,the status == unknown and so default result state is: COMMIT_MESSAGE");
                    return LocalTransactionState.COMMIT_MESSAGE;
            }
        }

        log.info("rocketmq execute check local transaction,the result state is: COMMIT_MESSAGE");

        return LocalTransactionState.COMMIT_MESSAGE;
    }

    public static String now() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return formatter.format(now);
    }
}
