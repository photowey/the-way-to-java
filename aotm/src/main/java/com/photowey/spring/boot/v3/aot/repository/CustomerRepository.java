package com.photowey.spring.boot.v3.aot.repository;

import com.photowey.spring.boot.v3.aot.core.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * {@code CustomerRepository}
 *
 * @author photowey
 * @date 2022/11/27
 * @since 1.0.0
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
