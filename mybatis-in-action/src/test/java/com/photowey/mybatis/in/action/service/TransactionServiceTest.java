package com.photowey.mybatis.in.action.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

/**
 * {@code TransactionServiceTest}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testRequiredTransaction() {
        Assertions.assertThrows(UnexpectedRollbackException.class, () -> {
            this.transactionService.transactionTestRequired();
        });

    }

    @Test
    void testNestedTransaction() {
        this.transactionService.transactionTestNested();
    }
}