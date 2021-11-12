package com.photowey.mybatis.in.action.service.impl;

import com.photowey.mybatis.in.action.engine.IMybatisEngine;
import com.photowey.mybatis.in.action.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@code TransactionServiceImpl}
 *
 * @author photowey
 * @date 2021/11/12
 * @since 1.0.0
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private IMybatisEngine mybatisEngine;

    /**
     * 测试事务-{@code Propagation.REQUIRED}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestRequired() {
        try {
            // 1.成功
            this.mybatisEngine.serviceEngine().employeeService().handleRequiredSave();
            // 2.抛出异常
            this.mybatisEngine.serviceEngine().organizationService().handleRequiredSave();

            // 测试是否回滚?
            // 结果是: 全归滚
            // org.springframework.jdbc.datasource.DataSourceTransactionManager.doSetRollbackOnly
            // org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only

            // 最外层事务已经被标记为: rollback-only 所以即使在--处理最外层业务时有try-catch 也会回滚
        } catch (Exception e) {
            // Ignore
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transactionTestNested() {

        try {
            // 1.成功
            this.mybatisEngine.serviceEngine().organizationService().handleNestedSave();
            // 2.抛出异常
            this.mybatisEngine.serviceEngine().employeeService().handleNestedSave();

            // 测试是否回滚?
            // 结果是: 部分归滚
            // organizationService().handleNestedSave() -> org.springframework.jdbc.datasource.DataSourceTransactionManager.doCommit
            // employeeService.handleNestedSave() -> 回滚
        } catch (Exception e) {
            // Ignore
        }

    }

}
