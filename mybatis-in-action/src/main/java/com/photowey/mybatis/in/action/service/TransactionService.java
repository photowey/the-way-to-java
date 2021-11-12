package com.photowey.mybatis.in.action.service;

/**
 * {@code TransactionService}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
public interface TransactionService {

    /**
     * 测试事务
     */
    void transactionTestRequired();

    void transactionTestNested();
}
